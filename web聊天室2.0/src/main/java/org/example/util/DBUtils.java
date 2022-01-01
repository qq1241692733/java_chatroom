package org.example.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.example.exception.AppException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文档注释 一般用于 方法的注释和类的注释
 * User: hong yaO
 * Date: 2021-12-2021/12/31
 * Time: 16:47
 */
public class DBUtils {
    private static MysqlDataSource dataSource = null;

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
        if (dataSource == null) {
            // 第一次调用
            dataSource = new MysqlDataSource();
            dataSource.setURL("jdbc:mysql://localhost:3306/java_chatroom");
            dataSource.setUser("root");
            dataSource.setPassword("jhy1241692733");
            dataSource.setUseSSL(false);
            dataSource.setCharacterEncoding("UTF-8");
        }
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new AppException("获取数据库连接失败", e);
        }
    }

    /**
     * 释放 JDBC资源
     */

    public static void close(Connection c, PreparedStatement ps, ResultSet res) {
        try {
            if (c != null) c.close();
            if (ps != null) ps.close();
            if (res != null) res.close();
        } catch (SQLException e) {
            throw new AppException("释放数据库资源出错", e);
        }
    }

    public static void close(Connection c, PreparedStatement ps) {
        close(c,ps,null);
    }
}
