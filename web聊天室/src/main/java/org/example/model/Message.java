package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文档注释 一般用于 方法的注释和类的注释
 * User: hong yaO
 * Date: 2021-12-2021/12/4
 * Time: 21:52
 */

@Getter
@Setter
@ToString
public class Message {
    private Integer messageId;
    private Integer userId;
    private Integer channelId;
    private String content;
    private java.util.Date sendTime;

    // 客户端发送的消息，转发到所有客户端的消息，需要昵称. 客户端显示(昵称，消息)
    private String nickName;
}
