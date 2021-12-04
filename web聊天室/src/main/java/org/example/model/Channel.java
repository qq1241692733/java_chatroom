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
 * Time: 21:51
 */

@Getter
@Setter
@ToString
public class Channel {
    private Integer channelId;

    private String channelName;
}
