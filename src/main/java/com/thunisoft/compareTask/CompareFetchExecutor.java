package com.thunisoft.compareTask;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Description:
 * Datetime:    2021/1/13   10:00
 * Author:   zhangliujie
 */
@Slf4j
public class CompareFetchExecutor implements Runnable {

    private String sqlLeft;

    private String sqlRight;

    private List<String> columns;

    private CountDownLatch countDownLatch;

    CompareFetchExecutor(String sqlLeft, String sqlRight, List<String> columns, CountDownLatch countDownLatch) {
        this.sqlLeft = sqlLeft;
        this.sqlRight = sqlRight;
        this.columns = columns;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        log.info("比对开始");
        long start = System.currentTimeMillis();
        log.info(sqlLeft);
        int fetchSize = 10000;
        try {
            Class.forName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");

            String url = "jdbc:log4jdbc:postgresql://172.16.32.39:6543/db_15cs" ;
            String url2 = "jdbc:log4jdbc:postgresql://172.16.32.39:6543/db_15fb_test" ;
            String username = "sa" ;
            String password = "123456" ;

            //将结果数据落到结果表
            String urlZj = "jdbc:log4jdbc:postgresql://172.16.32.221:5432/bigdata_sjzj_dev" ;
            String usernameZj = "sjzjv" ;
            String passwordZj = "123456" ;

            List<Map<String, Object>> leftDatas = new ArrayList<>();
            List<Map<String, Object>> rightDatas = new ArrayList<>();
            List<Map<String, Object>> more = new ArrayList<>();
            List<Map<String, Object>> less = new ArrayList<>();
            List<Map<String, Object>> different = new ArrayList<>();
            String pkKey = columns.get(0);

            Connection conn1 = DriverManager.getConnection(url , username , password );
            conn1.setAutoCommit(false);
            Statement stmt1 = conn1.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            stmt1.setFetchSize(fetchSize);
            ResultSet rs1 = stmt1.executeQuery(sqlLeft);

            Connection conn2 = DriverManager.getConnection(url2 , username , password );
            conn2.setAutoCommit(false);
            Statement stmt2 = conn2.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt2.setFetchSize(fetchSize);
            ResultSet rs2 = stmt2.executeQuery(sqlRight);

            Connection conn3= DriverManager.getConnection(urlZj , usernameZj , passwordZj );
            PreparedStatement ps = null;
            try{
                while (rs1.next()) {
                    while (leftDatas.size() < (fetchSize - 1)) {
                        Map<String, Object> row = new HashMap<>();
                        String pkValue = rs1.getString(1);
                        row.put(pkKey, pkValue);
                        // todo
                        row.put(pkKey + "2", pkValue);
                        for (int i = 1; i < columns.size(); i++) {
                            row.put(columns.get(i), rs1.getString(i + 1));
                            // todo
                            row.put(columns.get(i) + "2", rs1.getString(i + 1));
                        }
                        row.put("md5", rs1.getString(columns.size() + 1));
                        leftDatas.add(row);
                        if (!rs1.next()) {
                            break;
                        } else if (leftDatas.size() == (fetchSize - 1)) {
                            Map<String, Object> row2 = new HashMap<>();
                            String pkValue2 = rs1.getString(1);
                            row2.put(pkKey, pkValue2);
                            // todo
                            row2.put(pkKey + "2", pkValue2);
                            for (int i = 1; i < columns.size(); i++) {
                                row2.put(columns.get(i), rs1.getString(i + 1));
                                // todo
                                row2.put(columns.get(i) + "2", rs1.getString(i + 1));
                            }
                            row2.put("md5", rs1.getString(columns.size() + 1));
                            leftDatas.add(row2);
                        }
                    }
                    while (rightDatas.size() < fetchSize) {
                        if (!rs2.next()) {
                            break;
                        }
                        Map<String, Object> row = new HashMap<>();
                        String pkValue = rs2.getString(1);
                        row.put(pkKey, pkValue);
                        // todo
                        row.put(pkKey + "2", pkValue);
                        for (int i = 1; i < columns.size(); i++) {
                            row.put(columns.get(i), rs2.getString(i + 1));
                            // todo
                            row.put(columns.get(i) + "2", rs2.getString(i + 1));
                        }
                        row.put("md5", rs2.getString(columns.size() + 1));
                        rightDatas.add(row);
                    }
                    for (int i = 0; i < leftDatas.size();) {
                        for (int j = 0; j < rightDatas.size();) {
                            Map<String, Object> leftData = leftDatas.get(i);
                            Map<String, Object> rightData = rightDatas.get(j);
                            String pkValueLeft = (String) leftData.get(pkKey);
                            String pkValueRight = (String) rightData.get(pkKey);
                            if (pkValueLeft.equals(pkValueRight)) {
                                leftDatas.remove(leftData);
                                rightDatas.remove(rightData);
                                if (!leftData.get("md5").equals(rightData.get("md5"))) {
                                    leftData.put("n_type", 3);
                                    rightData.put("n_type", 3);
                                    different.add(leftData);
                                    different.add(rightData);
                                }
                            } else if (pkValueLeft.compareTo(pkValueRight) < 0) {
                                leftDatas.remove(leftData);
                                leftData.put("n_type", 1);
                                more.add(leftData);
                                if (i == leftDatas.size()) {
                                    //如果左表已经走到最后一位，右表其余的数不用查了
                                    break;
                                }
                            } else {
                                rightDatas.remove(rightData);
                                rightData.put("n_type", 2);
                                less.add(rightData);
                            }
                            if (leftDatas.size() == 0) {
                                break;
                            }
                        }
                        if (rightDatas.size() == 0) {
                            break;
                        }
                    }
                    if (more.size() > 0) {
                        String resultSqlMore = getResultSql(more, columns);
                        ps = conn3.prepareStatement(resultSqlMore);
                        ps.execute();
                    }
                    more.clear();
                    if (less.size() > 0) {
                        String resultSqlLess = getResultSql(less, columns);
                        ps = conn3.prepareStatement(resultSqlLess);
                        ps.execute();
                    }
                    less.clear();
                    if (different.size() > 0) {
                        String resultSqlDif = getResultSql(different, columns);
                        ps = conn3.prepareStatement(resultSqlDif);
                        ps.execute();
                    }
                    different.clear();
                }
                while (rs2.next()) {
                    Map<String, Object> row = new HashMap<>();
                    String pkValue = rs2.getString(1);
                    row.put(pkKey, pkValue);
                    for (int i = 1; i < columns.size(); i++) {
                        row.put(columns.get(i), rs2.getString(i + 1));
                    }
                    row.put("md5", rs2.getString(columns.size() + 1));
                    row.put("n_type", 2);
                    rightDatas.add(row);
                }
                if (rightDatas.size() > 0) {
                    String resultSqlLess = getResultSql(rightDatas, columns);
                    ps = conn3.prepareStatement(resultSqlLess);
                    ps.execute();
                }
            }catch(SQLException se){
                log.error("数据库连接失败！{}", se);
            } finally {
                rs1.close();
                rs2.close();
                stmt1.close();
                stmt2.close();
                if (ps != null) {
                    ps.close();
                }
                conn1.close();
                conn2.close();
                conn3.close();
            }
            leftDatas.clear();
            rightDatas.clear();
            long end = System.currentTimeMillis();
            log.info("比对结束,耗时:{}", (end - start));
            countDownLatch.countDown();
        } catch (Exception e) {
            log.error("线程内异常:{}", e);
        }
    }

    private static String getResultSql(List<Map<String, Object>> datas, List<String> columns) {
        String ruleId = "111111111111";
        StringBuilder sql = new StringBuilder("insert into db_lkbd.t_lkbd_result_test2 (c_rule_id_sjzjv,");
        String insertSql = "";
        for (String column : columns) {
            sql.append("" + column +",");
        }
        sql.append("n_type) values ");
        for (Map<String, Object> data : datas) {
            sql.append("('" + ruleId + "',");
            for (String column : columns) {
                if (data.get(column) != null) {
                    sql.append("'" + data.get(column) + "',");
                } else {
                    sql.append(data.get(column) + ",");
                }
            }
            sql.append(data.get("n_type") + "");
            sql.append("),");
        }
        insertSql = sql.substring(0, sql.length() - 1);
        return insertSql;
    }
}
