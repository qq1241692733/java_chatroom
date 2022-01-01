package org.example.dao;

import org.example.exception.AppException;
import org.example.model.User;
import org.example.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文档注释 一般用于 方法的注释和类的注释
 * User: hong yaO
 * Date: 2022-01-2022/1/1
 * Time: 13:58
 */
public class UserDAO {
    /**
     *
     */
    public static User queryByName(String name) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        // 定义返回数据
        User user = null;
        try {
            // 1.获取数据库连接
            c = DBUtils.getConnection();

            // 2.通过
            String sql = "select * from user where name=?";
            ps = c.prepareStatement(sql);
            ps.setString(1, name);
            res = ps.executeQuery();

            // 3.如果是查询操作，处理结果集
            while (res.next()) {
                user = new User();
                user.setUserId(res.getInt("userId"));
                user.setName(name);
                user.setPassword(res.getString("password"));
                user.setNickname(res.getString("nickName"));
                user.setIconPath(res.getString("iconPath"));
                user.setSignature(res.getString("signature"));

                java.sql.Timestamp lastLogout = res.getTimestamp("LastLogout");
//                System.out.println(lastLogout);
//                System.out.println(lastLogout.getTime());
//                System.out.println(new Date(lastLogout.getTime()));
                // java.sql.Date 不带时分秒 util.Date 带时分秒， sql.Timestamp带时分秒
                user.setLastLogout(new Date(lastLogout.getTime()));
            }
            return user;
        } catch (SQLException e) {
            throw new AppException("查询用户账号出错", e);
        } finally {
            // 5. 释放资源
            DBUtils.close(c, ps, res);
        }
    }
}
