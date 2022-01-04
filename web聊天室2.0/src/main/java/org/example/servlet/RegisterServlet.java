package org.example.servlet;

import org.example.dao.UserDAO;
import org.example.model.User;
import org.example.util.JSONUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA
 * Description:
 * User:gz
 * DATA:2021-05-30
 * Time:19:43
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        // 响应数据类型：根据接口文档，User类包含了约定的字段
        User user = new User();

        // 1.解析请求数据
        User input = JSONUtils.deserialize(req.getInputStream(), User.class);

        // 2.业务处理
        //  根据 name 查询数据库
        User query = UserDAO.queryByName(input.getName());
        if (query == null) {
            // 如果查询结果为空，说明无该用户，可注册。 进行数据库插入
            int flag = UserDAO.addUser(input.getName(),input.getPassword(),input.getNickName(),input.getSignature());
            if (flag == 1) {
                // 构造操作成功正常返回数据
                user = input;
                user.setOk(true);
                user.setReason("注册成功");
                System.out.println("注册成功:" + input.getNickName());
            } else {
                // 构造操作失败返回数据
                user.setOk(false);
                user.setReason("注册失败");
            }
        } else {
            // 已有该用户，构造操作失败返回数据
            user.setOk(false);
            user.setReason("用户已经存在，请重新输入");
        }

        // 3.返回响应数据：从响应对象获取输出流，打印输出到响应 body
        resp.getWriter().println(JSONUtils.serialize(user));
    }
}
