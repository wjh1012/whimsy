package simple;

import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author jh.wang
 * @since 2024/5/3
 */

public class SimpleTest {
    @Test
    public void spelTest(){
        ExpressionParser parser = new SpelExpressionParser();

        final Expression expression = parser.parseExpression("1+4*2");

        System.out.println(expression.getValue());
    }

    @Test
    public void spelTest2(){

    }

}
