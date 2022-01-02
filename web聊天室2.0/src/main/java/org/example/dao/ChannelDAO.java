package org.example.dao;

import org.example.exception.AppException;
import org.example.model.Channel;
import org.example.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文档注释 一般用于 方法的注释和类的注释
 * User: hong yaO
 * Date: 2022-01-2022/1/1
 * Time: 16:48
 */
public class ChannelDAO {

    public static List<Channel> query() {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet res = null;

        // 定义返回数据
        List<Channel> list = new ArrayList<>();
        try {
            // 1.获取数据库连接
            c = DBUtils.getConnection();

            // 2.通过 Connection + sql 创建操作命令对象 preparedStatement
            String sql = "select * from channel";
            ps = c.prepareStatement(sql);

            // 3.执行 sql: 执行前替换占位符
            res = ps.executeQuery();

            // 4.如果是查询操作，处理结果集
            while (res.next()) {
                Channel channel = new Channel();
                channel.setChannelId(res.getInt("channelId"));
                channel.setChannelName(res.getString("channelName"));
                list.add(channel);
            }
            return list;
        } catch (SQLException e) {
            throw new AppException("查询频道列表出错", e);
        } finally {
            DBUtils.close(c,ps,res);
        }
    }
}
