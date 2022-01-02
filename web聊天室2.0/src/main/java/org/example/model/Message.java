package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文档注释 一般用于 方法的注释和类的注释
 * User: hong yaO
 * Date: 2021-12-2021/12/30
 * Time: 20:24
 */

// 自动生成 get 等方法
@Getter
@Setter
@ToString
public class Message {

    private Integer messageId;
    private Integer userId;
    private Integer channelId;
    private String content;
    private java.util.Date sendTime;

    private String nickName;

}
