package cn.wangjiahang.config;

import com.alibaba.cloud.sentinel.SentinelProperties;
import com.alibaba.cloud.sentinel.custom.SentinelDataSourceHandler;
import com.alibaba.cloud.sentinel.datasource.config.AbstractDataSourceProperties;
import com.alibaba.cloud.sentinel.datasource.factorybean.NacosDataSourceFactoryBean;
import com.alibaba.csp.sentinel.datasource.AbstractDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.property.PropertyListener;
import com.alibaba.csp.sentinel.property.SentinelProperty;
import com.alibaba.csp.sentinel.slots.block.AbstractRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.alibaba.nacos.shaded.com.google.gson.GsonBuilder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author jh.wang
 * @since 2024/5/16
 */
@Component
public class CustomSentinelDataSourceHandler extends SentinelDataSourceHandler {
    private static final Logger log = LoggerFactory.getLogger(SentinelDataSourceHandler.class);
    private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final DefaultListableBeanFactory beanFactory;
    private final SentinelProperties sentinelProperties;
    private final Environment env;

    public CustomSentinelDataSourceHandler(DefaultListableBeanFactory beanFactory, SentinelProperties sentinelProperties, Environment env) {
        super(beanFactory, sentinelProperties, env);
        this.beanFactory = beanFactory;
        this.sentinelProperties = sentinelProperties;
        this.env = env;
    }

    @Override
    public void afterSingletonsInstantiated() {
        sentinelProperties.getDatasource()
                .forEach((dataSourceName, dataSourceProperties) -> {
                    try {
                        List<String> validFields = dataSourceProperties.getValidField();
                        if (validFields.size() != 1) {
                            log.error("[Sentinel Starter] DataSource " + dataSourceName
                                    + " multi datasource active and won't loaded: "
                                    + dataSourceProperties.getValidField());
                            return;
                        }
                        AbstractDataSourceProperties abstractDataSourceProperties = dataSourceProperties
                                .getValidDataSourceProperties();
                        abstractDataSourceProperties.setEnv(env);
                        abstractDataSourceProperties.preCheck(dataSourceName);
                        registerBean(abstractDataSourceProperties, dataSourceName
                                + "-sentinel-" + validFields.get(0) + "-datasource");
                    } catch (Exception e) {
                        log.error("[Sentinel Starter] DataSource " + dataSourceName
                                + " build error: " + e.getMessage(), e);
                    }
                });
    }

    private void registerBean(final AbstractDataSourceProperties dataSourceProperties,
                              String dataSourceName) {
        BeanDefinitionBuilder builder = parseBeanDefinition(dataSourceProperties, dataSourceName);

        this.beanFactory.registerBeanDefinition(dataSourceName, builder.getBeanDefinition());
        // init in Spring
        AbstractDataSource newDataSource = (AbstractDataSource) this.beanFactory.getBean(dataSourceName);

        switch (dataSourceProperties.getRuleType()) {
            case FLOW -> FlowRuleManager.register2Property(expandSentinelProperty(dataSourceProperties, dataSourceName, newDataSource));
            case DEGRADE -> DegradeRuleManager.register2Property(expandSentinelProperty(dataSourceProperties, dataSourceName, newDataSource));
            case PARAM_FLOW -> ParamFlowRuleManager.register2Property(expandSentinelProperty(dataSourceProperties, dataSourceName, newDataSource));
            case SYSTEM -> SystemRuleManager.register2Property(expandSentinelProperty(dataSourceProperties, dataSourceName, newDataSource));
            case AUTHORITY -> AuthorityRuleManager.register2Property(expandSentinelProperty(dataSourceProperties, dataSourceName, newDataSource));
            case GW_FLOW -> dataSourceProperties.postRegister(newDataSource);
            case GW_API_GROUP -> dataSourceProperties.postRegister(newDataSource);
        }

        // 使用自定义的 postRegister
        // register property in RuleManager
        // dataSourceProperties.postRegister(newDataSource);
    }

    private SentinelProperty expandSentinelProperty(AbstractDataSourceProperties dataSourceProperties, String dataSourceName, AbstractDataSource newDataSource) {
        // 从 spring 容器中 拿到对应的nacos 配置bean  规则为前面加一个 &
        final NacosDataSourceFactoryBean bean = this.beanFactory.getBean("&" + dataSourceName, NacosDataSourceFactoryBean.class);
        final SentinelProperty property = newDataSource.getProperty();

        // 注册动态监听
        newDataSource.getProperty().addListener(new NcoasPropertyListener(bean, dataSourceProperties.getDataType()));

        return property;
    }


    public static class NcoasPropertyListener implements PropertyListener<List<AbstractRule>> {
        private ConfigService configService;
        private final NacosDataSourceFactoryBean nacosDataSourceFactoryBean;
        private final String dataType;

        public NcoasPropertyListener(NacosDataSourceFactoryBean nacosDataSourceFactoryBean, String dataType) {
            this.nacosDataSourceFactoryBean = nacosDataSourceFactoryBean;
            this.dataType = dataType;

            try {
                // 暴力反射拿到 nacos service: configService 用于 publishConfig
                final NacosDataSource object = nacosDataSourceFactoryBean.getObject();
                final Field field = ReflectionUtils.findField(NacosDataSource.class, "configService", ConfigService.class);
                field.setAccessible(true);
                configService = (ConfigService) field.get(object);
            } catch (Exception e) {
                log.error("[NcoasPropertyListener] NacosDataSourceFactoryBean error: {}", e.getMessage(), e);
            }
        }

        @Override
        public void configUpdate(List<AbstractRule> value) {
            if (configService == null) {
                return;
            }

            try {
                configService.publishConfig(nacosDataSourceFactoryBean.getDataId(), nacosDataSourceFactoryBean.getGroupId(), gson.toJson(value), dataType);
            } catch (Exception e) {
                log.error("推送 sentinel config 失败: {}", e.getMessage(), e);
            }
        }

        @Override
        public void configLoad(List<AbstractRule> value) {

        }
    }
}
