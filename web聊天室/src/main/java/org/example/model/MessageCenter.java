package org.example.model;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
     保存 websocket 信息
 * User: hong yaO
 * Date: 2021-12-2021/12/6
 * Time: 12:19
 */
public class MessageCenter {
    // ConcurrentHashMap 线程安全，读读并发，写写互斥
    private static final ConcurrentHashMap <Integer, Session> clients = new ConcurrentHashMap<>();

    /**
     * websocket 建立连接，田间用户id 和客户端session,保存起来
     */
    public static void addOnlineUser(Integer userId, Session session) {
        clients.put(userId, session);
    }

    /**
     * 关闭 websocket连接，和出错是，删除客户端session
     */
    public static void delOnlineUser(Integer userId) {

    }

    /**
     * 接收到某用户的消息时，转发到客户端
     * 存在性能问题：
     * 如果接受到的消息数量 m 很庞大，同时在线的用户数量 n 也很多，要转发的次数 m*n 很大
     * 每隔消息接受都是一个线程，都要等待websocket 中的 onMessage 回调方法执行，性能差
     * todo: 优化（使用阻塞队列的方式解决，并行并发的执行 任务提交和任务执行）
     */
    public static void sendMessage(String message) {
        try {
            Enumeration<Session> e =  clients.elements();
            while (e.hasMoreElements()) {
                Session session = e.nextElement();
                session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
           e.printStackTrace();
        }
    }
}
