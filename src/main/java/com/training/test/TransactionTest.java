package com.training.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/11/22.
 */
public class TransactionTest {

    public static void main(String[] args) {
        // 加载驱动类
        try {
            Class.forName("com.training.ArteryBase.Driver");
            Connection conn = DriverManager.getConnection("jdbc:ArteryBase://172.16.32.39:6543/db_19fb_dr?&language=us_english&charset=utf8&ApplicationName=transactionTest",
                    "sa", "6789@jkl");
            conn.setAutoCommit(false);

            List<String> stms = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                // 随机生成实体码，主表插入数据
                String c_stm = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
                stms.add(c_stm);
            }
            try {
                deleteData(conn, stms);
                for (String c_stm : stms) {
                    String c_stm_lsws = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
                    saveData(conn, c_stm, c_stm_lsws);
                    conn.commit();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("保存数据失败");
                conn.rollback();
            }
            conn.close();
        } catch (Exception e) {
            System.out.println(new Date(System.currentTimeMillis()) + "数据库连接失败：" + e);
        }
    }

    private static void deleteData(Connection conn, List<String> stms) throws SQLException {
        String delSql = "DELETE from db_lsws.t_ajxx where c_stm = ? ;DELETE from db_lsws.t_lsws where c_stm_lsws = ? ;";
        try {
            for (String c_stm : stms) {
                PreparedStatement ps = conn.prepareStatement(delSql);
                try {
                    ps.setString(1, c_stm);
                    ps.setString(2, c_stm);
                    ps.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (ps != null) {
                        ps.close();
                    }
                }
                conn.commit();
            }
            System.out.println("删除成功");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("删除失败");
            throw e;
        }
    }

    private static void saveData(Connection conn, String c_stm, String c_stm_lsws) throws SQLException {
        String ajSql = "INSERT INTO db_lsws.t_ajxx (\"c_stm\", \"d_xgsj\", \"n_ajlx\", \"c_baah\", \"n_jbfy\") VALUES ('"+ c_stm +"', '2019-11-20 14:25:14.59', '8', '（2018）苏05测325号', '2401');";
        String lswsSql = "INSERT INTO \"db_lsws\".\"t_lsws\" (\"c_stm\", \"c_stm_lsws\", \"n_xh\", \"n_lb\", \"c_mc\", \"dt_zzsj\", \"c_nr\", \"n_yxgk\") VALUES ('"+ c_stm_lsws +"', '" + c_stm + "', '1', '4', '(2011)二中民四终字第0293号-民事判决书（二审维持原判或者改判用）-1.doc', '2011-07-05 16:38:52', '/51/51/0305/693/9909/003300512013030500004400000003110001-1.doc', '1');";
        PreparedStatement ps = conn.prepareStatement(ajSql);
        PreparedStatement ps2 = conn.prepareStatement(lswsSql);
        PreparedStatement ps3 = conn.prepareStatement(lswsSql);
        try {
            // 制造子表主键冲突
            ps.execute();
            ps2.execute();
            ps3.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("插入数据失败");
            conn.rollback();
        }

    }

}
