/**
 * 这段 Java 代码是一个工具类 DruidUtils，
 * 它的作用是简化数据库连接的获取，使用了阿里巴巴的数据库连接池组件 Druid。
 */
package util;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * package util;：表明这个类属于 util 包。
 *
 * DruidDataSourceFactory：这是 Druid 提供的工厂类，用于创建数据源（连接池）。
 *
 * javax.sql.* 和 java.sql.*：用于 JDBC 操作数据库连接。
 *
 * java.util.*：用于使用 Properties 类，读取配置文件
 */
public class DruidUtils {
    private static DataSource dataSource;//静态变量 dataSource，表示全局共享的连接池对象。

    // 静态代码块（类加载时执行一次）
    //用来初始化数据库连接池
    static {
        try {
            Properties properties = new Properties();
            properties.load(DruidUtils.class.getClassLoader().getResourceAsStream("jdbc.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
