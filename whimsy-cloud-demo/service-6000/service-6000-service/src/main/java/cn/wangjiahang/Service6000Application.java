package cn.wangjiahang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jh.wang
 * @since 2024/5/13
 */
@EnableDiscoveryClient
// @EnableFeignClients(basePackages = {"cn.wangjiahang.service6010.api"})
@SpringBootApplication
public class Service6000Application {
    public static void main(String[] args) {
        SpringApplication.run(Service6000Application.class, args);
    }
}
