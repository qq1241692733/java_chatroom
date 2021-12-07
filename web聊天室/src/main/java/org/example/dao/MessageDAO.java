package org.example.dao;

import org.example.exception.AppException;
import org.example.model.Message;
import org.example.model.User;
import org.example.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文档注释 一般用于 方法的注释和类的注释
 * User: hong yaO
 * Date: 2021-12-2021/12/5
 * Time: 12:49
 */
public class MessageDAO {

    // 插入消息
    public static int insert(Message msg) {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = Util.getConnection();
            String sql = "insert into message values(null,?,?,?,?)";
            ps = c.prepareStatement(sql);
            ps.setInt(1, msg.getUserId());
            ps.setInt(2, msg.getChannelId());
            ps.setString(3, msg.getContent());
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            return ps.executeUpdate();
        }catch (Exception e) {
            throw new AppException("保存消息出错", e);
        }finally {
            Util.close(c, ps);
        }
    }

    public static List<Message> queryByLestLogout(Integer userId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        // 定义返回数据
        List<Message> list = new ArrayList<>();
        try {
            // 1.获取数据库连接 Connection
            connection = Util.getConnection();

            // 2.通过 Connection + sql 创建操作命令对象 preparedStatement
            String sql = "select m.*,u.nickName from message m join user u on u.userId=m.userId where m.sendTime > (select lastLogout from user where userId = ?)";
            ps = connection.prepareStatement(sql);

            // 3.执行 sql: 执行前替换占位符
            ps.setInt(1, userId);
            res = ps.executeQuery();

            // 4.如果是查询操作，处理结果集
            while (res.next()) {
                Message m = new Message();
                // 获取结果集字段，处理结果集
                m.setUserId(userId);
                m.setNickName(res.getString("nickName"));
                m.setContent(res.getString("content"));
                m.setChannelId(res.getInt("channelId"));
                list.add(m);
            }
            return list;
        } catch (Exception e) {
            throw new AppException("查询用户[" + userId + "]的消息出错", e);
        } finally {
            // 5.释放资源
            Util.close(connection, ps, res);
        }
    }
}
