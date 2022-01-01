package org.example.servlet;

import org.example.dao.UserDAO;
import org.example.exception.AppException;
import org.example.model.User;
import org.example.util.DBUtils;
import org.example.util.JSONUtils;
import sun.java2d.pipe.AlphaPaintPipe;

import javax.rmi.CORBA.Util;
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

    // 检测登录状态接口：页面初始化行执行
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 请求以及响应的内容类型以及编码方式
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
                resp.getWriter().println(JSONUtils.serialize(user));
                return;
            }
        }
        // 未登录
        user.setOk(false);
        user.setReason("用户未登录");
        // 返回响应数据
        resp.getWriter().println(JSONUtils.serialize(user));
        // 3.返回响应数据: 从响应对象获取输出流，打印输出到响应 body

    }

    // 登录接口
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        // 响应数据类型：根据接口文档，User类包含了约定的字段
        User user = new User();
        try {
            // 1. 解析请求数据
//        String name = req.getParameter("name");
//        String password = req.getParameter("password");

            User input = JSONUtils.deserialize(req.getInputStream(), User.class);
            // 2. 业务处理：数据库验证账号密码，如果验证通过，创建 Session 会话保存用户信息
            // 根据账号查询
            User query = UserDAO.queryByName(input.getName());
            if (query == null) {
                throw new AppException("用户不存在");
            }
            if (!query.getPassword().equals(input.getPassword())) {
                throw new AppException("账号或密码错误");
            }
            // 能查到改用户，并且密码正确，成功登录,保存会话
            HttpSession session = req.getSession();
            session.setAttribute("user", query);
            user = query;
            // 构造操作成功正常返回数据： ok:true
            user.setOk(true);
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
            // 登录操作失败
            user.setOk(false);
            // 自定义异常，自己抛，为中文信息可以给用户看
            if (e instanceof AppException) {
                user.setReason(e.getMessage());
            } else {
                // 非自定义异常，英文信息，自己转一下
                user.setReason("未知的错误，请联系系统管理员");
            }
        }
        // 3.返回响应数据：从响应对象获取输出流，打印输出到响应 body
        resp.getWriter().println(JSONUtils.serialize(user));
    }
}
