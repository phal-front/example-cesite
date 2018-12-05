package phal.front.example.ecsite.site.facade;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.xml.bind.annotation.XmlRootElement;

import phal.front.example.ecsite.util.ResultCodeMessage;
import phal.front.example.ecsite.util.db.DBConnector;

@Dependent
public class FCOrderHistoryItem {

	@XmlRootElement
	public static class FindResult {
		public long orderHistoryId;
		public String itemId;
		public BigDecimal itemPrice;
	};

	public ResultCodeMessage findAll() {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {
			List<FindResult> resultList = new ArrayList<>();
			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"SELECT order_history_id, item_id, item_price FROM order_history_item ORDER BY order_history_id, item_id");
			) {
				try (ResultSet rs = ps.executeQuery()) {
					rcm.code = 100;
					while (rs.next()) {
						FindResult fr = new FindResult();
						fr.orderHistoryId = rs.getLong(1);
						fr.itemId = rs.getString(2);
						fr.itemPrice = rs.getBigDecimal(3);
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
							"SELECT order_history_id, item_id, item_price "
							+ "FROM order_history_item "
							+ "WHERE order_history_id=?"
							+ "ORDER BY order_history_id, item_id");
			) {
				ps.setLong(1, orderHistoryId);
				try (ResultSet rs = ps.executeQuery()) {
					rcm.code = 100;
					while (rs.next()) {
						FindResult fr = new FindResult();
						fr.orderHistoryId = rs.getLong(1);
						fr.itemId = rs.getString(2);
						fr.itemPrice = rs.getBigDecimal(3);
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

	public ResultCodeMessage find(long orderHistoryId, String itemId) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {
			FindResult fr = new FindResult();
			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"SELECT order_history_id, item_id, item_price FROM order_history_item WHERE order_history_id=? AND item_id=?");
			) {
				ps.setLong(1, orderHistoryId);
				ps.setString(2, itemId);
				try (ResultSet rs = ps.executeQuery()) {
					rcm.code = 100;
					while (rs.next()) {
						fr.orderHistoryId = rs.getLong(1);
						fr.itemId = rs.getString(2);
						fr.itemPrice = rs.getBigDecimal(3);
						rcm.code = 0;
						break;
					}
				}
			} catch (SQLException e) {
				throw e;
			}

			rcm.result = fr;
		} catch (Exception ex) {
			ex.printStackTrace();
			rcm.code = 900;
			rcm.messages.add("DBエラー");
		}

		return rcm;
	}

	public ResultCodeMessage insert(long orderHistoryId, String itemId, BigDecimal itemPrice) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {

			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"INSERT INTO order_history_item(order_history_id, item_id, item_price) VALUES(?,?,?)");
			) {
				ps.setLong(1, orderHistoryId);
				ps.setString(2, itemId);
				ps.setBigDecimal(3, itemPrice);
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


	public ResultCodeMessage delete(long orderHistoryId,  String itemId) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {

			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"DELETE FROM order_history_item WHERE order_history_id=? AND item_id=?");
			) {
				ps.setLong(1, orderHistoryId);
				ps.setString(2, itemId);
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
