package Utils;

import Druid.DruidUse;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtilsPro
{
    private static DataSource dataSource;
    private static ThreadLocal<Connection> tl = new ThreadLocal<>();
    static{
        try{
            Properties properties = new Properties();
            InputStream ips = DruidUse.class.getClassLoader().getResourceAsStream("Druid/druid.properties");
            properties.load(ips);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException
    {
        Connection connection = tl.get();
//        如果当前线程里没存储connection，则表明 第一次使用，那就来一个
        if(connection == null){
            connection = dataSource.getConnection();
//            来完之后，记得存进去
            tl.set(connection);
        }
        return connection;
    }

    public static void freeConnection() throws SQLException
    {
        Connection connection = tl.get();
        if(connection != null){
            connection.setAutoCommit(true);
            connection.close();
            tl.remove();
        }
    }

}
