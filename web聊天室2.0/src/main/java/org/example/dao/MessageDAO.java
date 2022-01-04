package org.example.dao;

import org.example.exception.AppException;
import org.example.model.Message;
import org.example.util.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文档注释 一般用于 方法的注释和类的注释
 * User: hong yaO
 * Date: 2022-01-2022/1/2
 * Time: 16:45
 */
public class MessageDAO {

    /**
     * 服务器接收到消息插入数据库
     */
    public static int insert(Message message) {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            // 1.获取数据库连接
            c = DBUtils.getConnection();
            // 2.sql
            String sql = "insert into message values (null,?,?,?,?)";
            ps = c.prepareStatement(sql);
            ps.setInt(1, message.getUserId());
            ps.setInt(2, message.getChannelId());
            ps.setString(3, message.getContent());
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new AppException("保存消息出错", e);
        } finally {
            DBUtils.close(c,ps);
        }
    }

    /**
     * 根据最后退出时间查询：未看到的消息
     *     查询消息发送时间 大于 该用户最后退出时间的所有消息
     */
    public static List<Message> queryByLastLogout(Integer userId) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        // 定义返回数据
        List<Message> list = new ArrayList<>();
        try {
            // 1.获取数据库的连接
            c = DBUtils.getConnection();
            // 2.通过 Connection + sql 创建操作命令对象 preparedStatement
            String sql = "select m.*,u.nickName from message m join user u on u.userId=m.userId " +
                    "where m.sendTime > (select lastLogout from user where userId = ?)";
            ps = c.prepareStatement(sql);
            // 3.执行 sql: 执行前替换占位符
            ps.setInt(1, userId);
            res = ps.executeQuery();
            // 4.如果是查询操作，处理结果集
            while (res.next()) {
                Message m = new Message();
                m.setUserId(userId);
                m.setChannelId(res.getInt("channelId"));
                m.setContent(res.getString("content"));
                m.setNickName(res.getString("nickName"));
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AppException("查询用户 [" + userId + "] 的消息出错", e);
        } finally {
            DBUtils.close(c,ps,res);
        }
        return list;
    }
}

