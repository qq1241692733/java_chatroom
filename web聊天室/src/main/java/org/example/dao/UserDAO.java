package org.example.dao;

import org.example.exception.AppException;
import org.example.model.User;
import org.example.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文档注释 一般用于 方法的注释和类的注释
 * User: hong yaO
 * Date: 2021-12-2021/12/5
 * Time: 12:48
 */
public class UserDAO {

    public static User queryByName(String name) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        // 定义返回数据
        User user = null;
        try {
            // 1.获取数据库连接 Connection
            connection = Util.getConnection();

            // 2.通过 Connection + sql 创建操作命令对象 preparedStatement
            String sql = "select * from user where name=?";
            ps = connection.prepareStatement(sql);

            // 3.执行 sql: 执行前替换占位符
            ps.setString(1, name);
            res = ps.executeQuery();

            // 4.如果是查询操作，处理结果集
            while (res.next()) {
                // 移动到下一行，如果有数据返回 true
                user = new User();
                // 设计结果集字段到用户对象属性中
                user.setUserId(res.getInt("userId"));
                user.setName(name);
                user.setPassword(res.getString("password"));
                user.setNickName(res.getString("nickName"));
                user.setIconPath(res.getString("iconPath"));
                user.setSignature(res.getString("signature"));

                java.sql.Timestamp lastLogout =  res.getTimestamp("LastLogout");
                // java.sql.Date 不带时分秒 util.Date 带时分秒， sql.Timestamp带时分秒
                user.setLastLogout(new Date(lastLogout.getTime()));
            }
            return user;
        } catch (Exception e) {
            throw new AppException("查询用户账号出错", e);
        } finally {
            // 5.释放资源
            Util.close(connection, ps, res);
        }
    }

    /**
     * 更新用户退出时间
     * @param userId
     */
    public static int updateLastLogout(Integer userId) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = Util.getConnection();
            String sql = "update user set lastLogout=? where userId=?";
            ps = c.prepareStatement(sql);
            ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            ps.setInt(2, userId);
            return ps.executeUpdate();
        }catch (Exception e) {
            throw new AppException("更新用户退出时间出错", e);
        }finally {
            Util.close(c, ps);
        }
    }
}
