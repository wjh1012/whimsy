package cn.wangjiahang.api;

import cn.wangjiahang.service6000.api.Loadbalancer6000ApiFallback;

/**
 * @author jh.wang
 * @since 2024/5/14
 */
// @Component("loadbalancer6000ApiFallback")
public class LoadbalancerApiFallback extends Loadbalancer6000ApiFallback {
    @Override
    public String testPort(Integer error) {
        return "操作失败";
    }
}