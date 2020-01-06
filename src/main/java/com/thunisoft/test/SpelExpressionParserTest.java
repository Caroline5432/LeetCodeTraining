package com.thunisoft.test;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/12/4.
 */
public class SpelExpressionParserTest {

    public static void main(String[] args) {
        String condition = "!false";
        // String condition = "#props('invasion.process.enable')";
        Map<String, Object> params = new HashMap<>();
        params.put("resourceId", "ajxx,wsxx");
        params.put("dataSourceId", "15fb");
        params.put("dtZlsj", null);
        params.put("sjfw", "2");
        params.put("cBhFapz", "5176886e66c94144a41fa32afc5d7bf8");
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(condition);
        StandardEvaluationContext SPEL_CONTEXT = new StandardEvaluationContext();
        SPEL_CONTEXT.setVariables(params);
        Boolean result = expression.getValue(SPEL_CONTEXT, expression, Boolean.class);
        System.out.println(result);
    }

}
