package cn.wangjiahang.config;

import com.alibaba.csp.sentinel.datasource.nacos.CustomSentinelDataSourceHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author jh.wang
 * @since 2024/5/16
 */
/**
 * @author jh.wang
 * @since 2024/5/16
 */
@Configuration
@Import({CustomSentinelDataSourceHandler.class})
public class SentinelNacosSync { }
