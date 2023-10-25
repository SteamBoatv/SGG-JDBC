package Utils;

import Druid.DruidUse;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils
{
    private static DataSource dataSource;
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
        return dataSource.getConnection();
    }

    public static void freeConnection(Connection connection) throws SQLException
    {
        connection.setAutoCommit(true);
        connection.close();
    }

}
