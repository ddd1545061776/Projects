package com.ddd.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCutil {
    private static DataSource ds;

    /*
     * 获取连接池对象
     * */
    public static DataSource getDataSource() {
        return ds;
    }

    static {
        try {
//加载配置文件
            Properties properties = new Properties();
            //使用classloader加载配置文件，获取字节输入流
            InputStream resourceAsStream = JDBCutil.class.getClassLoader().getResourceAsStream("druid.properties");
            properties.load(resourceAsStream);
            //初始化连接对象
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
         *获取连接Connection对象
         */
    }
        public static Connection getConnection () throws SQLException {
            return ds.getConnection();
        }

}
