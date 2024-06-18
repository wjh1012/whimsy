package cn.wangjiahang.service;

import cn.wangjiahang.entity.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jh.wang
 * @since 2024/6/17
 */
@Service
public class TestService1 {

    @Transactional
    public void test1() {
        final Entity entity = new Entity();
        entity.setId(1);
        entity.setF1("1");
        entity.setF2("2");

        final boolean insert = entity.insert();
        System.out.println("test1 insert result: " + insert);

        test1_1();
    }
    private void test1_1() {
        final Entity entity = new Entity();
        entity.setId(1);
        entity.setF1("1");
        entity.setF2("2");

        final boolean insert = entity.insertOrUpdate();
        System.out.println("test1_1 insertOrUpdate result: " + insert);

        if (true) {
            throw new RuntimeException();
        }
    }

    public void test2() {
        test2_1();
    }

    @Transactional
    private void test2_1() {
        final Entity entity = new Entity();
        entity.setId(1);
        entity.setF1("1");
        entity.setF2("2");

        final boolean insert = entity.insertOrUpdate();
        System.out.println("test2_1 insertOrUpdate result: " + insert);

        if (true) {
            throw new RuntimeException();
        }
    }

    public void test3() {
        test3_1();
    }

    @Transactional
    public void test3_1() {
        final Entity entity = new Entity();
        entity.setId(1);
        entity.setF1("1");
        entity.setF2("2");

        final boolean insert = entity.insertOrUpdate();
        System.out.println("test3_1 insertOrUpdate result: " + insert);

        if (true) {
            throw new RuntimeException();
        }
    }

    @Autowired
    @Lazy
    public TestService1 testService1;

    @Transactional
    public void test4() {
        testService1.test4_1();

        if (true) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void test4_1() {
        final Entity entity = new Entity();
        entity.setId(1);
        entity.setF1("1");
        entity.setF2("2");

        final boolean insert = entity.insertOrUpdate();
        System.out.println("test4_1 insertOrUpdate result: " + insert);
    }
}
