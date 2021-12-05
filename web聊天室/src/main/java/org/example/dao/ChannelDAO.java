package org.example.dao;

import org.example.exception.AppException;
import org.example.model.Channel;
import org.example.model.User;
import org.example.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文档注释 一般用于 方法的注释和类的注释
 * User: hong yaO
 * Date: 2021-12-2021/12/5
 * Time: 12:48
 */
public class ChannelDAO {
    public static List<Channel> query() {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        // 定义返回数据
        List<Channel> list = new ArrayList<>();
        try {
            // 1.获取数据库连接 Connection
            connection = Util.getConnection();

            // 2.通过 Connection + sql 创建操作命令对象 preparedStatement
            String sql = "select * from channel";
            ps = connection.prepareStatement(sql);

            // 3.执行 sql: 执行前替换占位符
            res = ps.executeQuery();

            // 4.如果是查询操作，处理结果集
            while (res.next()) {
                // 移动到下一行，如果有数据返回 true
                Channel channel = new Channel();
                // 设置属性
                channel.setChannelId(res.getInt("channelId"));
                channel.setChannelName(res.getString("channelName"));
                list.add(channel);
            }
            return list;
        } catch (Exception e) {
            throw new AppException("查询频道列表出错", e);
        } finally {
            // 5.释放资源
            Util.close(connection, ps, res);
        }
    }
}
