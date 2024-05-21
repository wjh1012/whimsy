package cn.wangjiahang.service6000.api;

/**
 * @author jh.wang
 * @since 2024/5/14
 */
public class Loadbalancer6000ApiFallback implements Loadbalancer6000Api {
    @Override
    public String testPort(Integer error) {
        return "操作失败";
    }

    @Override
    public String save(Integer error) {
        return "操作失败";
    }
}
