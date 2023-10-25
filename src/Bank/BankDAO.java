package Bank;

import Utils.JDBCUtilsPro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// dao 包 存储了直接连接银行数据库的最基础的方法,来供高级方法调用
public class BankDAO
{
//    加钱
    public void add(String account, float money) throws Exception
    {
        Connection connection = JDBCUtilsPro.getConnection();
        String sql = "UPDATE t_bank SET money = money + ? WHERE account = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,money);
        preparedStatement.setObject(2,account);
        int i = preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    //    减钱
    public void sub(String account,float money) throws Exception
    {
        Connection connection = JDBCUtilsPro.getConnection();
        String sql = "UPDATE t_bank SET money = money - ? WHERE account = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,money);
        preparedStatement.setObject(2,account);
        int i = preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
