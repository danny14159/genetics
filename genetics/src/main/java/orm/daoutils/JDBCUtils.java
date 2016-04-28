package orm.daoutils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;


public class JDBCUtils {
	private static DataSource dataSource = new ComboPooledDataSource();

	public static DataSource getDataSource() {
		return dataSource;
	}

	
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	
	public static String generateOrderId() {
		String uuid = UUID.randomUUID().toString(); // xxxx-xxx-xxx-xxxx-xxx
		int hashCode = Math.abs(uuid.hashCode());
		return "order-" + hashCode;
	}

	public static String generateProductId() {
		String uuid = UUID.randomUUID().toString(); // xxxx-xxx-xxx-xxxx-xxx
		int hashCode = Math.abs(uuid.hashCode());
		return "ep-" + hashCode;
	}
	public static void close(Connection conn) {
		if (conn !=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Statement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(ResultSet rs) {
		if (rs !=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
