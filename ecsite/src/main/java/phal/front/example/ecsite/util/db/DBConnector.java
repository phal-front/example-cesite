package phal.front.example.ecsite.util.db;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import phal.front.example.ecsite.AppConfig;

public class DBConnector implements AutoCloseable {
	private Connection connection = null;

	public Connection getConnection() throws RuntimeException {
		InitialContext context = null;
		try {
			context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup(AppConfig.DB_JNDI);
			connection = dataSource.getConnection();
			//connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			try {
				if (context != null) {
					context.close();
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		return connection;
	}

	@Override
	public void close() throws RuntimeException {
		if (connection != null) {
			try {
				if(! connection.isClosed()) {
					connection.close();
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}
}
