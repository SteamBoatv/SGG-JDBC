package Bank;

import Utils.JDBCUtilsPro;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BankService
{
    public void transfer(String accountIn,String accountOut,float money) throws Exception
    {
        BankDAO dao = new BankDAO();
        Connection connection = JDBCUtilsPro.getConnection();
        try {
            connection.setAutoCommit(false);
            dao.add(accountIn,money);
            dao.sub(accountOut,money);
            connection.commit();
            System.out.println("交易成功");
        }catch (Exception e){
            connection.rollback();
            System.out.println("交易失败");
            throw e;
        }finally {
            JDBCUtilsPro.freeConnection();
        }
    }

    @Test
    public void test() throws Exception
    {
        transfer("ergouzi","lvdandan",10);
    }
}
