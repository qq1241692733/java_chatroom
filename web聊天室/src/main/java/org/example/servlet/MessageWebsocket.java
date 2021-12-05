package org.example.servlet;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/message/{userId}")
public class MessageWebsocket {

    @OnOpen
    public void onOpen(@PathParam("userId") Integer userId,
                       Session session){
        System.out.println("建立连接："+userId);
    }

    @OnMessage
    public void onMessage(Session session,
                          String message){
        System.out.printf("接收到消息：%s", message);
    }

    @OnClose
    public void onClose(){
        System.out.println("关闭连接");
    }

    @OnError
    public void onError(Throwable t){
        System.out.println("出错了");
        t.printStackTrace();
    }
}
