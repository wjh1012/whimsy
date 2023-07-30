package cn.wangjiahang.whimsy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author jh.wang
 * @since 2023/7/16
 */
@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackages ="cn.wangjiahang.whimsy.**.domain.**.po")
@EnableJpaAuditing
public class Start {
    public static void main(String[] args){
        SpringApplication.run(Start.class, args);
    }
}
