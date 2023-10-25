package api.statment;

import java.sql.*;
import java.util.Scanner;

public class PreparedStatementLogin
{
    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String account = scanner.nextLine();
        System.out.println("请输入密码：");
        String password = scanner.nextLine();

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu?user=root&password=zyx721130");

        String sql = "SELECT * FROM t_user WHERE account = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,account);
        preparedStatement.setObject(2,password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            System.out.println("yes");
        }else {
            System.out.println("no");
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();

    }
}
