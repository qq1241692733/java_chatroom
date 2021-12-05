package org.example.servlet;

import org.example.dao.ChannelDAO;
import org.example.exception.AppException;
import org.example.model.Channel;
import org.example.model.Response;
import org.example.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文档注释 一般用于 方法的注释和类的注释
 * User: hong yaO
 * Date: 2021-12-2021/12/5
 * Time: 18:08
 */

@WebServlet("/channel")
public class ChannelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        Response response = new Response();

        try {
            // 查询所有的频道列表返回
            List<Channel> list = ChannelDAO.query();
            response.setOk(true);
            response.setData(list);
            // ok:true, data[{}, {}]
            // ok:false, reason:""
        }catch (Exception e) {
            e.printStackTrace();
            // 目前,前端的实现，再后端报错，要返回空的 List
            // 改造前端为解析 ok, reason

            if (e instanceof AppException) {
                response.setReason(e.getMessage());
            } else {

            }
        }
        resp.getWriter().println(Util.serialize(response));
    }
}
