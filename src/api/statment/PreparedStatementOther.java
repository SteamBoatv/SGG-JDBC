package api.statment;

import org.junit.Test;

import java.sql.*;

public class PreparedStatementOther
{
    @Test //测试主键回显
    public void getPrimaryKey() throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu",
                "root",
                "zyx721130"
        );
        String sql = "INSERT INTO t_user(account,password,nickname) VALUES(?,?,?)";
//        在创建preparedStatement时候，传入 需要拿取自增主键 的参数
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setObject(1, "other1");
        preparedStatement.setObject(2, "666");
        preparedStatement.setObject(3, "无");
        int i = preparedStatement.executeUpdate();
        if (i > 0) {
            System.out.println("插入成功");
//            获取 主键集 对象
//            只有 一行一列
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//            先移动到下一行
            generatedKeys.next();
            int anInt = generatedKeys.getInt(1);
            System.out.println("key is:" + anInt);
        } else {
            System.out.println("插入失败");
        }
        preparedStatement.close();
        connection.close();
    }

    @Test //普通插入
    public void tonsOfInsert() throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu", "root", "zyx721130");
        String sql = "INSERT INTO t_user(account,password,nickname) VALUES(?,?,?)";
//        在创建preparedStatement时候，传入 需要拿取自增主键 的参数
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        long l = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            preparedStatement.setObject(1, "tonsOfInsert" + i);
            preparedStatement.setObject(2, "666" + i);
            preparedStatement.setObject(3, "无");
            preparedStatement.executeUpdate();
        }
        long l1 = System.currentTimeMillis();
//        1710毫秒
        System.out.println("time is:" + (l1 - l));
        preparedStatement.close();
        connection.close();
    }

    @Test //批量插入
    public void tonsOfInsertPro() throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///atguigu?rewriteBatchedStatements=true",
                "root",
                "zyx721130"
        );
        String sql = "INSERT INTO t_user(account,password,nickname) VALUES(?,?,?)";
//        在创建preparedStatement时候，传入 需要拿取自增主键 的参数
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        long l = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            preparedStatement.setObject(1, "tonsOfInsertPro" + i);
            preparedStatement.setObject(2, "666Pro" + i);
            preparedStatement.setObject(3, "无");
//           不执行，追加到VALUES后面
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        long l1 = System.currentTimeMillis();
//        62毫秒
        System.out.println("time is:" + (l1 - l));
        preparedStatement.close();
        connection.close();
    }
}
