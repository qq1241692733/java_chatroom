package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 工具类
 * User: hong yaO
 * Date: 2021-12-2021/12/3
 * Time: 22:38
 */
public class Util {

    private static final ObjectMapper M = new ObjectMapper();

    private static final MysqlDataSource DS = new MysqlDataSource();

    static {
        // 设置 json序列化、反序列化的日期格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        M.setDateFormat(dateFormat);
        DS.setURL("jdbc:mysql://localhost:3306/java_chatroom");
        DS.setUser("root");
        DS.setPassword("jhy1241692733");
        DS.setUseSSL(false);
        DS.setCharacterEncoding("UTF-8");
    }

    /**
     * json序列化: java 对象转换为 json 字符串
     */
    public static String serialize(Object o) {
        try {
            return M.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("json序列化失败：" + o, e);
        }
    }

    /**
     * 反序列化 json：把 json字符串转换为 java对象
     */
    public static <T> T deserialize(String s, Class<T> c) {
        try {
            return M.readValue(s, c);  // json 格式反序列化
        } catch (JsonProcessingException e) {
            // 如果出现这个异常，一般是 Json字符串中的键，在 class中没有找到对应的属性
            throw new RuntimeException("json反序列化失败" , e);
        }
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection(){
        try {
            return DS.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("获取数据库连接失败", e);
        }
    }

    /**
     * 释放 JDBC资源
     */
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("释放数据库资出错", e);
        }
    }

    public static void close(Connection connection, Statement statement) {
        close(connection, statement, null);
    }

    public static void main(String[] args) {
        // 测试 json序列化
        Map<String, Object> map = new HashMap<>();
        map.put("ok", true);
        map.put("d", new Date());
        System.out.println(serialize(map));

        // 测试数据库连接：需要把init.sql在cmd执行，初始化数据库，表数据
        System.out.println(getConnection());
    }
}
