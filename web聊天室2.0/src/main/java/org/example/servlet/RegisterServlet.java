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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        User input= JSONUtils.deserialize(req.getInputStream(), User.class);
        User findUser= UserDAO.queryByName(input.getName());
        User output=new User();
        if(findUser==null){
            int flag= UserDAO.addUser(input.getName(),input.getPassword(),input.getNickName(),input.getSignature());
            if(flag==1){
                output=input;
                output.setOk(true);
                output.setReason("注册成功");
            }else{
                output.setOk(false);
                output.setReason("注册失败");
            }
        }else{
            output.setOk(false);
            output.setReason("用户已经存在，请重新输入");
        }
        resp.getWriter().println(JSONUtils.serialize(output));
    }
}
