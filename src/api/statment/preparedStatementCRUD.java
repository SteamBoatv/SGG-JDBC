package api.statment;

import Bank.BankDAO;
import Bank.BaseDAO;
import Utils.JDBCUtilsPro;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

public class preparedStatementCRUD extends BaseDAO
{
    @Test
    public void testInsert()  throws Exception
    {
        String sql = "INSERT INTO t_user(account,PASSWORD,nickname) VALUES (?,?,?)";
        update(sql,"baseDAO","123","无");
    }

    @Test
    public void testUpdate() throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "zyx721130");
        String sql = "UPDATE t_user SET nickname = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,"违规昵称");
        preparedStatement.setObject(2,3);
        int rows = preparedStatement.executeUpdate();
        if(rows == 1) System.out.println("修改成功");
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testDelete() throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "zyx721130");
        String sql = "DELETE FROM t_user WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,4);
        int i = preparedStatement.executeUpdate();
        if(i > 0){
            System.out.println("执行成功");
        }
        preparedStatement.close();
        connection.close();

    }

    @Test
    public void testQuery() throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu?user=root&password=zyx721130");
        String sql = "SELECT * FROM t_user";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            System.out.println(resultSet.getString(1));
            System.out.println(resultSet.getString(2));
            System.out.println(resultSet.getString(3));
            System.out.println(resultSet.getString(4));
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testQueryPro() throws SQLException, NoSuchFieldException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException
    {
        String sql = "SELECT id,account AS 'name',password,nickname FROM t_user WHERE account = ?";
        List<User> list = new ArrayList<>();
        list = query(User.class,sql,"member1");
        for (int i = 0; i < list.size(); i++) {
            User users = list.get(i);
            System.out.println(users.name + "-" + users.password);
        }
    }

    @Test
    public void testSelectPro()throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu","root","zyx721130");
        String sql = "SELECT * FROM t_user";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<Map> list = new ArrayList<>();
        while(resultSet.next()){
            Map<Object, Object> map = new HashMap<>();
            for (int col = 1; col <= columnCount; col++) {
                Object value = resultSet.getObject(col);
                map.put(metaData.getColumnLabel(col),value);
            }
            list.add(map);
        }

        System.out.println(list);
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

}
