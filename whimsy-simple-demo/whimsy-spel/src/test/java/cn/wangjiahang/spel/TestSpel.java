package cn.wangjiahang.spel;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author jh.wang
 * @since 2024/5/3
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TestSpel {
    @Test
    public void test简单表达式() {
        ExpressionParser parser = new SpelExpressionParser();

        final Expression expression = parser.parseExpression("1+4*2");

        System.out.println(expression.getValue());
    }



}
