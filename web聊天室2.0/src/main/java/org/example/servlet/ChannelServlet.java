package org.example.servlet;

import org.example.dao.ChannelDAO;
import org.example.exception.AppException;
import org.example.model.Channel;
import org.example.model.Response;
import org.example.util.JSONUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文档注释 一般用于 方法的注释和类的注释
 * User: hong yaO
 * Date: 2022-01-2022/1/1
 * Time: 16:49
 */
@WebServlet("/channel")
public class ChannelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        // 响应数据类型
        Response response = new Response();

        try {
            // 查询所有的频道列表返回
            List<Channel> list = ChannelDAO.query();
            response.setOk(true);
            response.setData(list);
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
            // 登录操作失败
            response.setOk(false);
            if (e instanceof AppException) {
                // 自定义异常，自己抛，为中文信息可以给用户看
                response.setReason(e.getMessage());
            } else {
                // 非自定义异常，英文信息，自己转一下
                response.setReason("未知的错误，请联系系统管理员");
            }
        }
        // 3.返回响应数据：从响应对象获取输出流，打印输出到响应 body
        resp.getWriter().println(JSONUtils.serialize(response));
    }
}
