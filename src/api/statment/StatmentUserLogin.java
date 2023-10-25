package api.statment;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.Scanner;

//模拟用户登录
public class StatmentUserLogin
{
    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
//        1.获取用户名和密码
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = scanner.nextLine();
        System.out.println("请输入密码：");
        String password = scanner.nextLine();
//        2.JDBC一套
//        DriverManager.registerDriver(new Driver());
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/atguigu",
                "root",
                "zyx721130"
        );
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM t_user WHERE account = '" + username + "' AND PASSWORD = '" + password + "'";
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            System.out.println("登录成功");
        } else {
            System.out.println("登录失败");
        }
        resultSet.close();
        statement.close();
        connection.close();


    }
}
