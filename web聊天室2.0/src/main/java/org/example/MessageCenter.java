package org.example;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 保存websocket需要的信息：
 * 所有客户端session
 */
public class MessageCenter {

    /**
     * 支持线程安全的map结构，并且满足高并发（读写，读读并发，写写互斥的，加锁粒度）
     */
    private static final ConcurrentHashMap<Integer, Session> clients = new ConcurrentHashMap<>();

    /**
     * websocket建立连接时，添加用户id和客户端session，保存起来
     */
    public static void addOnlineUser(Integer userId, Session session){
        clients.put(userId, session);
    }
    /**
     * 关闭websocket连接，和出错时，删除客户端session
     */
    public static void delOnlineUser(Integer userId){
        clients.remove(userId);
    }
    /**
     * 接收到某用户的消息时，转发到所有客户端
     */
    public static void sendMessage(String message){
        try {
            Enumeration<Session> e = clients.elements();
            while(e.hasMoreElements()){
                Session session = e.nextElement();
                session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
