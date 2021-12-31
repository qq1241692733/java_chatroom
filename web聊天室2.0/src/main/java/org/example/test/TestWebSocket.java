package org.example.test;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文档注释 一般用于 方法的注释和类的注释
 * User: hong yaO
 * Date: 2021-12-2021/12/26
 * Time: 16:55
 */

@ServerEndpoint(value="/test/{userId}")
public class TestWebSocket {
    private String userId = null;

    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        this.userId = userId;
        System.out.println("打开链接" + userId);
    }

    @OnClose
    public void onClose() {
        System.out.println("关闭连接: " + userId);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("收到消息! " + userId + ": " + message);
        session.getBasicRemote().sendText(message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("连接出现错误! " + this.userId);
        error.printStackTrace();
    }
}
