package cn.wangjiahang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jh.wang
 * @since 2024/5/13
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"cn.wangjiahang", "cn.wangjiahang.service6000"})
@EnableFeignClients(basePackages = {"cn.wangjiahang.service6000"})
public class Service6010Application {
    public static void main(String[] args) {
        SpringApplication.run(Service6010Application.class, args);
    }
}
