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
 * Time: 22:19
 */

@Getter
@Setter
@ToString
public class Response {
    // 当前接口响应的是否操作成功
    private boolean ok;
    // 操作时报，前端展示错误信息
    private String reason;
    // 保存业务数据
    private Object data;
}
