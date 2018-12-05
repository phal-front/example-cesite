package phal.front.example.ecsite.site.facade;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.xml.bind.annotation.XmlRootElement;

import phal.front.example.ecsite.util.ResultCodeMessage;
import phal.front.example.ecsite.util.db.DBConnector;

@Dependent
public class FCOrderHistory {

	/*
    order_history_id           BIGSERIAL,
    order_history_timestamptz  TIMESTAMPTZ,
    order_history_send_name    VARCHAR(300),
    order_history_send_addr    VARCHAR(300),
    order_history_send_email   VARCHAR(100),
 */
	@XmlRootElement
	public static class FindResult {
		public long orderHistoryId;
		public String orderHstoryTimestampTZ;
		public String orderHistorySendName;
		public String orderHistorySendAddr;
		public String orderHistorySendEmail;
	};

	public ResultCodeMessage findAll() {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {
			List<FindResult> resultList = new ArrayList<>();
			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
						"SELECT "
						+ "order_history_id,"
						+ "TO_CHAR(order_history_timestamptz, 'YYYYMMDD HH24MISS'),"
						+ "order_history_send_name,"
						+ "order_history_send_addr,"
						+ "order_history_send_email "
						+ "FROM order_history "
						+ "ORDER BY order_history_id"
						);
			) {
				try (ResultSet rs = ps.executeQuery()) {
					rcm.code = 100;
					while (rs.next()) {
						FindResult fr = new FindResult();
						fr.orderHistoryId = rs.getLong(1);
						fr.orderHstoryTimestampTZ = rs.getString(2);
						fr.orderHistorySendName = rs.getString(3);
						fr.orderHistorySendAddr = rs.getString(4);
						fr.orderHistorySendEmail = rs.getString(5);
						resultList.add(fr);
					}
					if(resultList.size() > 0) {
						rcm.code = 0;
					}
				}
			} catch (SQLException e) {
				throw e;
			}

			rcm.result = resultList;
		} catch (Exception ex) {
			ex.printStackTrace();
			rcm.code = 900;
			rcm.messages.add("DBエラー");
		}

		return rcm;
	}

	public ResultCodeMessage find(long orderHistoryId) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {
			List<FindResult> resultList = new ArrayList<>();
			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"SELECT "
							+ "order_history_id,"
							+ "TO_CHAR(order_history_timestamptz, 'YYYYMMDD HH24MISS'),"
							+ "order_history_send_name,"
							+ "order_history_send_addr,"
							+ "order_history_send_email "
							+ "FROM order_history "
							+ "WHERE order_history_id=?"
							+ "ORDER BY order_history_id"
							);
			) {
				ps.setLong(1, orderHistoryId);
				try (ResultSet rs = ps.executeQuery()) {
					rcm.code = 100;
					while (rs.next()) {
						FindResult fr = new FindResult();
						fr.orderHistoryId = rs.getLong(1);
						fr.orderHstoryTimestampTZ = rs.getString(2);
						fr.orderHistorySendName = rs.getString(3);
						fr.orderHistorySendAddr = rs.getString(4);
						fr.orderHistorySendEmail = rs.getString(5);
						resultList.add(fr);
					}
					if(resultList.size() > 0) {
						rcm.code = 0;
					}
				}
			} catch (SQLException e) {
				throw e;
			}

			rcm.result = resultList;
		} catch (Exception ex) {
			ex.printStackTrace();
			rcm.code = 900;
			rcm.messages.add("DBエラー");
		}

		return rcm;
	}

	public ResultCodeMessage insert(
			String orderHistorySendName,
			String orderHistorySendAddr,
			String orderHistorySendEmail
	) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {

			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"INSERT INTO order_history("
							+ "order_history_timestamptz,"
							+ "order_history_send_name,"
							+ "order_history_send_addr,"
							+ "order_history_send_email"
							+ ")VALUES("
							+ "CURRENT_TIMESTAMP,"
							+ "?,"
							+ "?,"
							+ "?"
							+ ")"
					);
			) {
				ps.setString(1, orderHistorySendName);
				ps.setString(2, orderHistorySendAddr);
				ps.setString(3, orderHistorySendEmail);
				rcm.result = ps.executeUpdate();
				cn.commit();
				rcm.code = 0;
			} catch (SQLException e) {
				throw e;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			rcm.code = 900;
			rcm.messages.add("DBエラー");
		}

		return rcm;
	}

	public ResultCodeMessage insertBill(
			String orderHistorySendName,
			String orderHistorySendAddr,
			String orderHistorySendEmail,
			List<String> itemIds,
			List<BigDecimal> itemPrices
	) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {
			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();

			) {
				long returnOrderHistoryId = 0;
				try(PreparedStatement ps = cn.prepareStatement(
						"INSERT INTO order_history("
						+ "order_history_timestamptz,"
						+ "order_history_send_name,"
						+ "order_history_send_addr,"
						+ "order_history_send_email"
						+ ")VALUES("
						+ "CURRENT_TIMESTAMP,"
						+ "?,"
						+ "?,"
						+ "?"
						+ ")"
				, Statement.RETURN_GENERATED_KEYS);){
					ps.setString(1, orderHistorySendName);
					ps.setString(2, orderHistorySendAddr);
					ps.setString(3, orderHistorySendEmail);
					int orderHistoryInsertCount = ps.executeUpdate();

					try(ResultSet rs = ps.getGeneratedKeys();){
						if (rs != null && rs.next()) {
							returnOrderHistoryId = rs.getLong(1);
						}
					}
				}

				int orderHistoryItemInsertCount = 0;
				try(PreparedStatement ps = cn.prepareStatement(
						"INSERT INTO order_history_item(order_history_id, item_id, item_price) VALUES(?,?,?)");
				) {
					for(int i=0; i<itemIds.size(); i++) {
						ps.setLong(1, returnOrderHistoryId);
						ps.setString(2, itemIds.get(i));
						ps.setBigDecimal(3, itemPrices.get(i));
						orderHistoryItemInsertCount += ps.executeUpdate();
					}
				}

				rcm.result = orderHistoryItemInsertCount;
				cn.commit();
				rcm.code = 0;
			} catch (SQLException e) {
				throw e;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			rcm.code = 900;
			rcm.messages.add("DBエラー");
		}

		return rcm;
	}


	public ResultCodeMessage delete(long orderHistoryId) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {

			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"DELETE FROM order_history WHERE order_history_id=?");
			) {
				ps.setLong(1, orderHistoryId);
				rcm.result = ps.executeUpdate();
				cn.commit();
				rcm.code = 0;

			} catch (SQLException e) {
				throw e;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			rcm.code = 900;
			rcm.messages.add("DBエラー");
		}

		return rcm;
	}
}
