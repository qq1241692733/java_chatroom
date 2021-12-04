package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文档注释 一般用于 方法的注释和类的注释
 * User: hong yaO
 * Date: 2021-12-2021/12/4
 * Time: 21:30
 */

// 数据库使用，前后端ajax，session保持是基于对象和二进制抓换（这里要串行化化接口）
 @Getter
 @Setter
 @ToString
public class User extends Response implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Integer userId;
    private String name;
    private String password;
    private String nickName;
    private String iconPath;
    private String signature;
    private java.util.Date lastLogout;

}
