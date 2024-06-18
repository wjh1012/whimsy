package cn.wangjiahang.service;

import cn.wangjiahang.entity.Entity;
import cn.wangjiahang.mapper.EntityMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jh.wang
 * @since 2024/6/17
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TestService1Test {
    @Autowired
    TestService1 testService1;

    @Autowired
    EntityMapper mapper;

    @BeforeEach
    public void setUp() {
        final int count = mapper.delete(new LambdaQueryWrapper<>());
        System.out.println("--------delete count : " + count + "--------");
    }

    @AfterEach
    public void tearDown() {
        final Long count = mapper.selectCount(new LambdaQueryWrapper<>());
        System.out.println("--------end count : " + count + "--------");
    }

    @Test
    public void test1() {
        testService1.test1();
    }

    @Test
    public void test2() {
        testService1.test2();
    }

    @Test
    public void test3() {
        testService1.test3();
    }

    @Test
    public void test4() {
        testService1.test4();
    }
}
