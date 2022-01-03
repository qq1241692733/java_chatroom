package org.example.dao;

import org.example.exception.AppException;
import org.example.model.User;
import org.example.util.DBUtils;

import java.sql.*;
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
                user.setNickName(res.getString("nickName"));
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

    public static int updateLastLogout(Integer userId) {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = DBUtils.getConnection();
            String sql = "update user set lastLogout=? where userId=?";
            ps = c.prepareStatement(sql);
            ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            ps.setInt(2, userId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new AppException("更新用户最后退出时间出错", e);
        } finally {
            DBUtils.close(c,ps);
        }

    }

    public static int addUser(String name, String password, String nickName, String signature) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = DBUtils.getConnection();
            String sql = "insert into user values (null,?,?,?,null,?,?)";
            ps = c.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,password);
            ps.setString(3,nickName);
            ps.setString(4,signature);
            ps.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
            return ps.executeUpdate();
        }catch (Exception e){
            throw new AppException("注册失败",e);
        }finally {
            DBUtils.close(c,ps);
        }
    }
}
