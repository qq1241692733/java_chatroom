package org.example.servlet;

import org.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文档注释 一般用于 方法的注释和类的注释
 * User: hong yaO
 * Date: 2021-12-2021/12/30
 * Time: 21:23
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // 检测登录状态接口：页面初始换行执行
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        // 响应数据类型：根据接口文档，User类包含了约定的字段
        User user = new User();
        // 获取当前请求的session,并再获取用户信息，如果获取不到返回 ok:false
        HttpSession session = req.getSession();

        if (session != null) {
            User get = (User)session.getAttribute("user");
            if (get != null) {
                // 已登录，并获取到用户信息
                user = get;
                user.setOk(true);
            }
        }
        // 3.返回响应数据: 从响应对象获取输出流，打印输出到响应 body

    }

    // 登录接口
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
