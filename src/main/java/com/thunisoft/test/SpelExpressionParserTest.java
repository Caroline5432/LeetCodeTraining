package com.thunisoft.test;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/12/4.
 */
public class SpelExpressionParserTest {

    public static void main(String[] args) {
        /*String condition = "!false";
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
        System.out.println(result);*/

        // System.out.println(convertPathToRowId("/239/239/22/768/23900000014080/2.html"));
        String zipName = "AJ_2400_20200225094531_01_2020.zip";
        String xmlName = "0201_000a08929a6644349935b7daf9b70ffe.xml";
        System.out.println(checkXmlName(zipName, xmlName));
    }

    public static String convertPathToRowId(String path) {
        DecimalFormat df = new DecimalFormat("000");
        DecimalFormat df_ajbs = new DecimalFormat("000000000000000");
        Pattern pattern = Pattern.compile("(\\d+)/(\\d+)/(\\d+)/(\\d+)/(\\d+)/(\\d+)[.]htm[l]?");
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            StringBuilder sbuilder = new StringBuilder();
            sbuilder.append(df.format(Long.parseLong(matcher.group(5)) % 256L));
            sbuilder.append("_");
            sbuilder.append(matcher.group(3));
            sbuilder.append("_");
            sbuilder.append(df_ajbs.format(Long.parseLong(matcher.group(5))));
            sbuilder.append("_");
            sbuilder.append(df.format((long)Integer.parseInt(matcher.group(6))));
            return sbuilder.toString();
        } else {
            return path;
        }
    }

    private static boolean checkXmlName(String zipName, String xmlName) {
        String msg = String.format("[%s]-[%s], xml文件解析失败, 错误信息: %s",
                zipName,
                xmlName,
                "xml文件名不合法");
        if(!xmlName.contains("_")){
            System.out.println(msg);
            return false;
        }
        String[] strs = xmlName.split("\\.")[0].split("_");
        if (zipName.startsWith("LSWS")){
            if(strs.length != 2 || strs[1].length() != 36) {
                System.out.println(msg);
                return false;
            }
        } else {
            Pattern p = Pattern.compile("[0-9]*");
            //xml名解析的数组，小于2个或者strs[1]的长度小于4或者strs[1]不是数字，都返回false
            if (strs.length < 2 || strs[1].length() < 4 || !p.matcher(strs[1]).matches()) {
                System.out.println(msg);
                return false;
            }
        }
        return true;
    }

}
