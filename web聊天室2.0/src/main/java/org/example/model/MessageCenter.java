package org.example.model;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文档注释 一般用于 方法的注释和类的注释
 * User: hong yaO
 * Date: 2022-01-2022/1/2
 * Time: 15:21
 */

public class MessageCenter {
    // ConcurrentHashMap 线程安全，读读并发，写写互斥
    private static final ConcurrentHashMap<Integer, Session> clients = new ConcurrentHashMap<>();

    /**
     * 优化：
     *    1.阻塞队列:用来存放消息，接收到客户端消息就放到里边（放进去很快）
     *    2.再启动一个线程，不停的拉取队列中的消息，发送(发送和接受并发执行，异步消息处理)
     *          在大量消息接收时，可以降低服务器压力，达到削峰的目的
     *    3.懒汉模式
     */
    private static BlockingDeque<String> messageBlockingDeque = new LinkedBlockingDeque<>();

    private MessageCenter() {}

    private static volatile MessageCenter center = null;

    public static MessageCenter getInstance() {
        if (center == null) {
            synchronized (MessageCenter.class) {
                if (center == null) {
                    center = new MessageCenter();
                    new Thread(new Runnable() { // 启动一个线程，不停的拉取队列中的消息
                        @Override
                        public void run() {
                            while (true) {
                                // 阻塞式获取数据，如果队列为空，阻塞等待
                                try {
                                    String message = messageBlockingDeque.take();
                                    sendMessage(message);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                }
            }
        }
        return center;
    }

    // 不直接法消息，先把消息存放在队列里，由另一个线程去发
    public void addMessage(String message) {
        messageBlockingDeque.add(message);
    }

    /**
     * websocket 建立连接，添加用户 id 和客户端 session，保存起来
     */
    public static void addOnlineUser(Integer userId, Session session) {
        clients.put(userId, session);
    }

    /**
     * 关闭 websocket连接和出错时，删除客户端 session
     */
    public static void delOnlineUser(Integer userId) {
        clients.remove(userId);
    }

    /**
     * 接受到某用户的消息时，转发到客户端。
     *      此处的 message 是 JSON字符串，所以直接转发即可不用反序列化
     *   存在性能问题：
     *   如果接受到的消息数量 m 很庞大，同时在线的用户数量 n 也很多，要转发的次数 m*n 很大
     *   每隔消息接受都是一个线程，都要等待websocket 中的 onMessage 回调方法执行，性能差
     *   todo: 优化（使用阻塞队列的方式解决，并行并发的执行 任务提交和任务执行）
     */
    public static void sendMessage(String message) {
        try {
            for (Session session : clients.values()) {
                session.getBasicRemote().sendText(message);
                System.out.println("发送消息："+ message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
