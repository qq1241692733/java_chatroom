package org.example.servlet;

import org.example.dao.MessageDAO;
import org.example.dao.UserDAO;
import org.example.model.Message;
import org.example.model.MessageCenter;
import org.example.util.JSONUtils;
import sun.security.provider.MD2;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文档注释 一般用于 方法的注释和类的注释
 * User: hong yaO
 * Date: 2022-01-2022/1/1
 * Time: 16:25
 */
@ServerEndpoint("/message/{userId}")
public class MessageWebsocket {

    @OnOpen
    public void onOpen(@PathParam("userId") Integer userId, Session session) throws IOException {
        System.out.println("建立连接" + userId);
        // 1.把每隔客户端 session 都保存起来，之后转发消息到所有客户端要用
        MessageCenter.addOnlineUser(userId, session);
        // 2.查询本客户端（用户）上次登录前的消息
        List<Message> list = MessageDAO.queryByLastLogout(userId);
        // 3.发送当前用户上次登录后的消息
        for (Message m : list) {
            // 此处的 message是对象的引用，需要反序列化
            session.getBasicRemote().sendText(JSONUtils.serialize(m));
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("接收到消息：" + message);
        // 1.遍历保存的所有 Session，给每个都发消息，此处的 message 是 JSON字符串
//        MessageCenter.sendMessage(message);
        MessageCenter.getInstance().addMessage(message);
        // 2.消息保存到数据库
        //    反序列化 json 字符串为 message 对象
        Message msg = JSONUtils.deserialize(message, Message.class);
        //    插入数据库
        int n = MessageDAO.insert(msg);
    }

    @OnClose
    public void onClose(@PathParam("userId") Integer userId) {
        System.out.println("关闭连接: " + userId);
        // 1.本客户端关闭连接，要在之前保存的 session 集合中删除
        MessageCenter.delOnlineUser(userId);
        // 2.建立连接时要获取上次用户登录以后的消息，所以关闭长连接就是代表用户退出
        // 更新用户上次退出时间
        //UserDAO.updateLastLogout(userId);
    }

    @OnError
    public void onError(@PathParam("userId") Integer userId,  Throwable error) {
        System.out.println("连接出现错误! " + userId);
        MessageCenter.delOnlineUser(userId);
        error.printStackTrace();
        // 和关闭连接一样
    }
}
