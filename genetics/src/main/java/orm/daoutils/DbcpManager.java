package orm.daoutils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

/**DBCP连接池管理类
 * @author Danny
 *
 */
public class DbcpManager {
	public static BasicDataSource dataSource;
	
	 private final static String username = "root";
     private final static String password = "KTW00YazYWSU";
     private final static String url = "jdbc:mysql://120.26.110.181:3306/gh";
     public static Connection connection = null;
     public static int connectionCount = 0;

	public static Connection getConnection() throws SQLException {
        try {
            if (dataSource == null) {
                dataSource = new BasicDataSource();
                String driver = "com.mysql.jdbc.Driver";
                try {
                    dataSource.setDriverClassName(driver);
                    dataSource.setUrl(url);
                    dataSource.setUsername(username);
                    dataSource.setPassword(password);
                    dataSource.setMaxIdle(10);
                    if (connection == null || connection.isClosed()) {
                        System.out.println(" requeition CONNECTION WITH FIRST SERVER.");
                        connection = dataSource.getConnection();
                        connectionCount++;
                    }
                } catch (SQLException e) {
                    System.out.println("***Connection Requisition*** Could not connect to the database msg :" + e.getMessage());
                }
            } else {
                connection = dataSource.getConnection();
                connectionCount++;
            }
        } catch (Exception e) {
            System.out.println("open connection exception" + e);
        }
        return connection;
    }

}
