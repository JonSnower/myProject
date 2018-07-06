package com.hey.java.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class JDBCUtils {
	 
    /**
     * url格式 -> jdbc:子协议:子名称//主机名:端口号/数据库的名字?属性名=属性值&属性名=属性值
     * configString变量中有多个参数，需要深入地去研究它们的具体含义
     */
    private static String configString = "?useUnicode=true&characterEncoding=utf8&useSSL=true";
    private static String url = "jdbc:mysql://127.0.0.1:3306/mydb" + configString;
    // 本地的mysql数据库(无子名称) 端口号3306 数据库jdbcforjava
 
    private static String user = "root";
    private static String password = "heyujiao";
 
    // 工具类，直接使用，不可生对象
    private JDBCUtils() {
    }
 
    // 把注册驱动放到静态代码块中，因为 静态代码块只执行一次
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            // 异常要抛出去
            throw new ExceptionInInitializerError(e);
        }
    }
 
    /**
     * 获取与指定数据库的链接
     *
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
 
    /**
     * 释放三种资源ResultSet Statement Connection
     *
     */
    public static void free(ResultSet rs, PreparedStatement ps, Connection con) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new DaoException(e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                throw new DaoException(e);
            } finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    throw new DaoException(e);
                }
            }
        }
    }
}