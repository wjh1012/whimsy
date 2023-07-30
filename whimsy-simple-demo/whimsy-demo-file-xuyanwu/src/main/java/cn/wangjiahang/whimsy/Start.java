package cn.wangjiahang.whimsy;

import cn.xuyanwu.spring.file.storage.EnableFileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jh.wang
 * @since 2023/7/23
 */
@SpringBootApplication
@EnableFileStorage
public class Start {
    public static void main(String[] args){
        SpringApplication.run(Start.class, args);
    }
}
