package api.statment;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;

//实现基本的JDBC流程
public class StatmentQueryPart
{
    public static void main(String[] args) throws SQLException
    {
        // 注册驱动

        // 注册驱动，并且抛出了异常
        DriverManager.registerDriver(new Driver());
        
        //获取连接
        // java接口 = 实现类
        Connection connection = DriverManager.getConnection(
                // "jdbc:mysql://localhost:3306/test?serverTimezone=UTC",
                "jdbc:mysql://127.0.0.1:3306/atguigu",
                "root",
                "zyx721130"
        );
        // 创建statment对象
        Statement statement = connection.createStatement();
        // 发送sql，并获取返回结果
        String sql = "SELECT * FROM t_user";
        // 执行查询类Query的语句
        //获取结果集解析
        ResultSet resultSet = statement.executeQuery(sql);
        // 查看有没有下一行数据，如果有就获取
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("account");
            String password = resultSet.getString("PASSWORD");
            String nickname = resultSet.getString("nickname");
            System.out.println("id: " + id + " name: " + name + " password: " + password + " nickname: " + nickname);
        }
        //关闭资源(由内往外关闭)
        resultSet.close();
        statement.close();
        connection.close();

    }
}
