package Druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DruidUse
{
    @Test // 测试硬编码
    public void testHard() throws Exception
    {
//        创建连接池对象
        DruidDataSource dataSource = new DruidDataSource();
//        初始化
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/atguigu");
        dataSource.setUsername("root");
        dataSource.setPassword("zyx721130");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        初始化连接数量
        dataSource.setInitialSize(5);
//        设置后天生成的连接池，一共的最大数量
        dataSource.setMaxActive(10);

        Connection connection = dataSource.getConnection();
//        这里是回收链接 而不是释放 只是写法上看着一样
        connection.close();

    }

    @Test // 测试软编码
    public void testSoft() throws Exception
    {
        Properties properties = new Properties();
        InputStream ips = DruidUse.class.getClassLoader().getResourceAsStream("Druid/druid.properties");
        properties.load(ips);

        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();

//        xxx
        connection.close();
    }

}
