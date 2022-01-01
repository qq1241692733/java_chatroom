package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.AppException;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * 文档注释 一般用于 方法的注释和类的注释
 * User: hong yaO
 * Date: 2022-01-2022/1/1
 * Time: 9:37
 */
public class JSONUtils {
    private static final ObjectMapper M = new ObjectMapper();

    static {
        // 设置 json序列化、反序列化的日期格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        M.setDateFormat(dateFormat);
    }

    /**
     * json序列化: java 对象转换为 json 字符串
     */
    public static String serialize(Object o) {
        try {
            return M.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new AppException("json序列化失败：" + o, e);
        }
    }

    /**
     * 反序列化 json: 把 json字符串转为 java对象
     */
    public static <T> T deserialize(String s, Class<T> c) {
        try {
            return M.readValue(s, c);
        } catch (JsonProcessingException e) {
            throw new AppException("反序列化失败", e);
        }
    }

    public static  <T> T deserialize(InputStream inputStream, Class<T> c) {
        try {
            return M.readValue(inputStream, c);
        } catch (IOException e) {
            throw new AppException("反序列化失败", e);
        }
    }

    public static void main(String[] args) {
        // 测试 json序列化
        Map<String, Object> map = new HashMap<>();
        map.put("ok", true);
        map.put("d", new Date());
        System.out.println(serialize(map));

        // 测试数据库连接：需要把init.sql在cmd执行，初始化数据库，表数据
        System.out.println(DBUtils.getConnection());
    }
}
