package org.example.servlet;

import org.example.model.Response;
import org.example.model.User;
import org.example.util.Util;
import sun.misc.Unsafe;

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
 * 注销
 * User: hong yaO
 * Date: 2021-12-2021/12/6
 * Time: 21:57
 */

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        HttpSession session = req.getSession(false);
        if (session != null) {
            User user = (User)session.getAttribute("user");
            if (user != null) {
                // 用户已登录: 删除 session 中保存的用户信息
                session.removeAttribute("user");
                // 注销成功，返回 ok:true
                Response r = new Response();
                r.setOk(true);
                resp.getWriter().println(Util.serialize(r));
                return;
            }
        }
        // 用户未登录
        Response r = new Response();
        r.setOk(false);
        r.setReason("用户未登录");
        resp.getWriter().println(Util.serialize(r));
    }
}
