package cn.wangjiahang.controller;

import cn.wangjiahang.entity.TestSeata;
import cn.wangjiahang.service6000.api.Loadbalancer6000Api;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.GlobalTransactionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author jh.wang
 * @since 2024/5/19
 */
@RestController
@RequestMapping("seata")
public class SeataController {

    private static final Logger log = LoggerFactory.getLogger(SeataController.class);
    @Autowired
    private Loadbalancer6000Api loadbalancer6000Api;

    @RequestMapping("test1")
    @GlobalTransactional(rollbackFor = Exception.class)
    public String save(@RequestParam(value = "error", required = false, defaultValue = "1") Integer error) throws TransactionException {
        // 自己业务操作
        final TestSeata testSeata = new TestSeata();
        testSeata.setMoney(BigDecimal.valueOf(new Random().nextDouble()));
        final boolean insert = testSeata.insert();

        // 远程业务操作
        final String save = loadbalancer6000Api.save(error);
        log.info("remote dispatch status：{}", save);

        if (error != null && error.equals(2)) {
            throw new IllegalArgumentException("手动异常");
        }

        testSeata.setId(null);
        log.info(testSeata.insert() ? "保存成功1" : "保存失败1");


        if (!"保存成功".equals(save)) {
            GlobalTransactionContext.reload(RootContext.getXID()).rollback();
        }

        return insert ? "保存成功" : "保存失败";
    }
}
