package cn.wangjiahang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jh.wang
 * @since 2024/5/17
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Authorization6200Application {
    public static void main(String[] args) {
        SpringApplication.run(Authorization6200Application.class, args);
    }
}
