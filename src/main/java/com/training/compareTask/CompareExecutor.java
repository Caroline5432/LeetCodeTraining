package com.training.compareTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

/**
 * Description:
 * Datetime:    2021/1/6   15:24
 * Author:   zhangliujie
 */
@Slf4j
public class CompareExecutor implements Runnable{

    private String sqlLeft;

    private String sqlRight;

    private List<String> columns;

    private CountDownLatch countDownLatch;

    CompareExecutor(String sqlLeft, String sqlRight, List<String> columns, CountDownLatch countDownLatch) {
        this.sqlLeft = sqlLeft;
        this.sqlRight = sqlRight;
        this.columns = columns;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        log.info("比对开始");
        log.info(sqlLeft);
        try {
            try {
                Class.forName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
            } catch (Exception e) {
                e.printStackTrace();
            }
            String url = "jdbc:log4jdbc:postgresql://172.16.32.39:6543/db_fb15_gz6" ;
            String url2 = "jdbc:log4jdbc:postgresql://172.16.32.39:6543/db_fb15_gz8" ;
            String username = "sa" ;
            String password = "123456" ;

            Map<String, Map<String, Object>> leftDataMap = new HashMap<>();
            Set<String> leftIds = new HashSet<>();
            log.info("左表开始写入数据");
            Long start = System.currentTimeMillis();
            try{
                Connection conn = DriverManager.getConnection(url , username , password ) ;
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.setFetchSize(10000);
                ResultSet rs = stmt.executeQuery(sqlLeft);
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    String pkKey = rs.getString(1);
                    row.put(columns.get(0), pkKey);
                    row.put("md5", rs.getString(columns.size() + 1));
                    leftDataMap.put(pkKey, row);
                    leftIds.add(pkKey);
                }
                stmt.close();
                conn.close();
            }catch(SQLException se){
                log.error("数据库连接失败！{}", se);
            }
            Long end = System.currentTimeMillis();
            log.info("左表写入数据结束，耗时:{}", (end - start));
            //计算左表少于右表数据
            List<Map<String, Object>> less = new ArrayList<>();
            //计算内容差异数据
            List<Map<String, Object>> different = new ArrayList<>();
            List<String> difIds = new ArrayList<>();
            Set<String> rightIds = new HashSet<>();
            try{
                Connection conn = DriverManager.getConnection(url2 , username , password ) ;
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.setFetchSize(10000);
                ResultSet rs2 = stmt.executeQuery(sqlRight);
                while (rs2.next()) {
                    String pkKey = rs2.getString(1);
                    if (leftIds.contains(pkKey)) {
                        //内容差异
                        String md5Left = (String)leftDataMap.get(pkKey).get("md5");
                        String md5Right = rs2.getString(columns.size() + 1);
                        if (!md5Right.equals(md5Left)) {
                            Map<String, Object> row = new HashMap<>();
                            row.put(columns.get(0), pkKey);
                            for (int i = 1; i < columns.size(); i++) {
                                row.put(columns.get(i), rs2.getString(i + 1));
                            }
                            row.put("md5", md5Right);
                            row.put("n_type", 3);
                            different.add(row);
                            difIds.add(pkKey);
                        }
                    } else {
                        //右表多于左表
                        Map<String, Object> row = new HashMap<>();
                        row.put(columns.get(0), pkKey);
                        for (int i = 1; i < columns.size(); i++) {
                            row.put(columns.get(i), rs2.getString(i + 1));
                        }
                        row.put("md5", rs2.getString(columns.size() + 1));
                        row.put("n_type", 2);
                        less.add(row);
                    }
                    rightIds.add(pkKey);
                }
                stmt.close();
                conn.close();
            }catch(SQLException se){
                log.error("数据库连接失败！{}", se);
            }
            //补充左表比右表多的数据及左表与右表内容差异的数据
            Set<String> moreIds = Sets.difference(leftIds, rightIds);
            List<Map<String, Object>> more = new ArrayList<>();
            try{
                Connection conn = DriverManager.getConnection(url , username , password ) ;
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.setFetchSize(10000);
                ResultSet rs = stmt.executeQuery(sqlLeft);
                while (rs.next()) {
                    String pkKey = rs.getString(1);
                    Map<String, Object> row = new HashMap<>();
                    if (moreIds.contains(pkKey))  {
                        row.put(columns.get(0), pkKey);
                        for (int i = 1; i < columns.size(); i++) {
                            row.put(columns.get(i), rs.getString(i + 1));
                        }
                        row.put("md5", rs.getString(columns.size() + 1));
                        row.put("n_type", 1);
                        more.add(row);
                    }
                    if (difIds.contains(pkKey)) {
                        row.put(columns.get(0), pkKey);
                        for (int i = 1; i < columns.size(); i++) {
                            row.put(columns.get(i), rs.getString(i + 1));
                        }
                        row.put("md5", rs.getString(columns.size() + 1));
                        row.put("n_type", 3);
                        different.add(row);
                    }
                }
                stmt.close();
                conn.close();
            }catch(SQLException se){
                log.error("数据库连接失败！{}", se);
            }

            //将结果数据落到结果表
            String urlZj = "jdbc:log4jdbc:postgresql://172.16.32.221:5432/bigdata_sjzj_dev" ;
            String usernameZj = "sjzjv" ;
            String passwordZj = "123456" ;
            try (Connection conn = DriverManager.getConnection(urlZj , usernameZj , passwordZj)) {
                conn.setAutoCommit(true);
                log.info("开始将结果数据落库");
                PreparedStatement ps = null;
                if (more.size() > 0) {
                    String resultSqlMore = getResultSql(more, columns);
                    ps = conn.prepareStatement(resultSqlMore);
                    ps.execute();
                }
                if (less.size() > 0) {
                    String resultSqlLess = getResultSql(less, columns);
                    ps = conn.prepareStatement(resultSqlLess);
                    ps.execute();

                }
                if (different.size() > 0) {
                    String resultSqlDif = getResultSql(different, columns);
                    ps = conn.prepareStatement(resultSqlDif);
                    ps.execute();
                }
                ps.close();
                conn.close();
                log.info("结果数据落库结束");
            } catch (SQLException e)  {
                log.error("数据落库异常，url:{}，异常:{}", urlZj, e);
            }
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
