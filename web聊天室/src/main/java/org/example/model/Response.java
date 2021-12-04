package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 前后接口需要统一字段
 * User: hong yaO
 * Date: 2021-12-2021/12/4
 * Time: 22:11
 */

@Getter
@Setter
@ToString
public class Response {
    // 当前接口响应的是否操作成功
    private boolean ok;
    // 操作时报，前端展示错误信息
    private String reason;
}
