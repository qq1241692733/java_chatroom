package org.example.servlet;

import org.example.dao.UserDAO;
import org.example.model.Response;
import org.example.model.User;
import org.example.util.JSONUtils;

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
 * Date: 2022-01-2022/1/3
 * Time: 11:59
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        // 响应数据
        Response r = new Response();

        // 获取到 session
        HttpSession session = req.getSession(false);
        if (session != null) {
            // 获取到 session 中的 user 对象
            User user = (User) session.getAttribute("user");
            if (user != null) {
                // 删除 session 中保存的用户信息
                session.removeAttribute("user");
                // 更新数据库该用户最后退出时间
                int n = UserDAO.updateLastLogout(user.getUserId());
                // 响应
                r.setOk(true);
                resp.getWriter().println(JSONUtils.serialize(r));
                return;
            }
        }
        // 用户未登录
        r.setOk(false);
        r.setReason("用户未登录");
        resp.getWriter().println(JSONUtils.serialize(r));
    }
}
