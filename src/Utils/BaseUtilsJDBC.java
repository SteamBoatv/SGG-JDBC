package Utils;

import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseUtilsJDBC
{
    @Test
    public void testJDBC() throws SQLException
    {
        Connection connection = JDBCUtils.getConnection();
        String sql = "SELECT * FROM t_user";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        List<Map> list = new ArrayList<>();
        int columnCount = metaData.getColumnCount();
        while(resultSet.next()){
            Map<Object,Object> map = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                map.put(metaData.getColumnLabel(i),resultSet.getObject(i));
            }
            list.add(map);
        }
        System.out.println(list);
        preparedStatement.close();
        JDBCUtils.freeConnection(connection);

    }
}
