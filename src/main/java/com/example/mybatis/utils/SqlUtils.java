package com.example.mybatis.utils;

import java.util.ArrayList;
import java.util.List;

public class SqlUtils {


    /**
     * 获取Insert语句后面values 参数信息
     *
     * @param sql string
     * @return string[]
     */
    public static String[] sqlInsertParameter(String sql) {
        int startIndex = sql.indexOf("values");
        int endIndex = sql.length();
        String substring = sql.substring(startIndex + 6, endIndex).replace("(", "").replace(")", "").replace("#{", "")
                .replace("}", "");
        String[] split = substring.split(",");
        return split;
    }

    /**
     * 获取select 后面where语句
     *
     * @param sql string
     * @return list
     */
    public static List<String> sqlSelectParameter(String sql) {
        int startIndex = sql.indexOf("where");
        int endIndex = sql.length();
        String substring = sql.substring(startIndex + 5, endIndex);
        String[] split = substring.split("and");
        List<String> listArr = new ArrayList<>();
        for (String string : split) {
            String[] sp2 = string.split("=");
            listArr.add(sp2[0].trim());
        }
        return listArr;
    }

    /**
     * 将SQL语句的参数替换变为?
     *
     * @param sql           string
     * @param parameterName string
     * @return string
     */
    public static String paramQuestion(String sql, String[] parameterName) {
        for (int i = 0; i < parameterName.length; i++) {
            String string = parameterName[i];
            sql = sql.replace("#{" + string + "}", "?");
        }
        return sql;
    }

    public static String paramQuestion(String sql, List<String> parameterName) {
        for (int i = 0; i < parameterName.size(); i++) {
            String string = parameterName.get(i);
            sql = sql.replace("#{" + string + "}", "?");
        }
        return sql;
    }

}