package cn.wangjiahang.api;

import cn.wangjiahang.service6000.api.Loadbalancer6000ApiFallback;
import org.springframework.stereotype.Component;

/**
 * @author jh.wang
 * @since 2024/5/14
 */
@Component
public class LoadbalancerApiFallback extends Loadbalancer6000ApiFallback {
    @Override
    public String testPort(Integer error) {
        return "自定义操作失败";
    }

    @Override
    public String save(Integer error) {
        return "自定义操作失败";
    }
}
