package Bank;

import Utils.JDBCUtilsPro;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDAO
{
    public int update(String sql, Object... params) throws SQLException
    {
        Connection connection = JDBCUtilsPro.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        int i = preparedStatement.executeUpdate();
        if (i > 0) {
            System.out.println("baseDAO中操作成功");
        }
        preparedStatement.close();
//        因为事务是一定会关闭自动提交的，所哟如果 getAutoCommit开着，说明不是事务，帮他关了
//        事务的话，见BankService中的代码，会在那边关闭的，不用在这关。
//        而且事务需要这个语句，他只是其中一句话，不能事务处理到中间，就关闭把
        if (connection.getAutoCommit() == true) {
            JDBCUtilsPro.freeConnection();
        }
        return i;
    }

    public <T> List<T> query(Class<T> clazz, String sql, Object ...params) throws SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException
    {
        Connection connection = JDBCUtilsPro.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if(params != null &&params.length!=0){
            for (int i = 1; i <= params.length; i++) {
                preparedStatement.setObject(i,params[i-1]);
            }
        }
        List<T> list = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        
        while(resultSet.next()){
//            一行一个对象
            T t = clazz.getDeclaredConstructor().newInstance();
            for (int i = 1; i <= columnCount; i++) {
                Object value = resultSet.getObject(i);
//                此处的别名 或者是真名 一定要对应那个类的名字 比如 User类来接收对象，
//                对象的姓名参数叫 nameaa，那别名也要取nameaa
                String columnLabel = metaData.getColumnLabel(i);
//              此处是获得这个类对象的 属性
                Field field = clazz.getDeclaredField(columnLabel);
//                如果属性是私有private的，我们将其调整为 可以设置
                field.setAccessible(true);
//                设置属性的赋值方法
//                参数一：要复制的对象 (如果是static，则可以不要第一个参数)
//                参数二：要复制的值value
                field.set(t,value);
            }
            list.add(t);
        }
        return list;
    }
}
