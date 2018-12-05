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
public class FCItem {

	@XmlRootElement
	public static class FindResult {
		public String itemId;
		public String itemName;
		public BigDecimal itemPrice;
		public int itemStock;
	};

	public ResultCodeMessage findAll() {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {
			List<FindResult> resultList = new ArrayList<>();
			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"SELECT item_id, item_name, item_price, item_stock FROM item ORDER BY item_id");
			) {
				try (ResultSet rs = ps.executeQuery()) {
					rcm.code = 100;
					while (rs.next()) {
						FindResult fr = new FindResult();
						fr.itemId = rs.getString(1);
						fr.itemName = rs.getString(2);
						fr.itemPrice = rs.getBigDecimal(3);
						fr.itemStock = rs.getInt(4);
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

	public ResultCodeMessage findSearchName(String searchName) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {
			List<FindResult> resultList = new ArrayList<>();
			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"SELECT item_id, item_name, item_price, item_stock FROM item WHERE item_name LIKE ? ORDER BY item_id");
			) {
				ps.setString(1, "%" + searchName + "%");
				try (ResultSet rs = ps.executeQuery()) {
					rcm.code = 100;
					while (rs.next()) {
						FindResult fr = new FindResult();
						fr.itemId = rs.getString(1);
						fr.itemName = rs.getString(2);
						fr.itemPrice = rs.getBigDecimal(3);
						fr.itemStock = rs.getInt(4);
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

	public ResultCodeMessage findKindId(String kindId) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {
			List<FindResult> resultList = new ArrayList<>();
			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"SELECT item.item_id, item.item_name, item.item_price, item.item_stock "
							+ "FROM kind_item JOIN item ON(kind_item.item_id = item.item_id) "
							+ "WHERE kind_id=? "
							+ "ORDER BY item_id");
			) {
				ps.setString(1, kindId);
				try (ResultSet rs = ps.executeQuery()) {
					rcm.code = 100;
					while (rs.next()) {
						FindResult fr = new FindResult();
						fr.itemId = rs.getString(1);
						fr.itemName = rs.getString(2);
						fr.itemPrice = rs.getBigDecimal(3);
						fr.itemStock = rs.getInt(4);
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

	public ResultCodeMessage find(String itemId) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {
			FindResult fr = new FindResult();
			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"SELECT item_id, item_name, item_price, item_stock FROM item WHERE item_id = ?");
			) {
				ps.setString(1, itemId);
				try (ResultSet rs = ps.executeQuery()) {
					rcm.code = 100;
					while (rs.next()) {
						fr.itemId = rs.getString(1);
						fr.itemName = rs.getString(2);
						fr.itemPrice = rs.getBigDecimal(3);
						fr.itemStock = rs.getInt(4);
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

	public ResultCodeMessage insert(
			String itemId,
			String itemName,
			BigDecimal itemPrice,
			int itemStock
	) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {

			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"INSERT INTO item(item_id, item_name, item_price, item_stock) VALUES(?,?,?,?)");
			) {
				ps.setString(1, itemId);
				ps.setString(2, itemName);
				ps.setBigDecimal(3, itemPrice);
				ps.setInt(4, itemStock);
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

	public ResultCodeMessage update(
			String itemId,
			String itemName,
			BigDecimal itemPrice,
			int itemStock
	) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {

			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"UPDATE item SET item_name=?, item_price=?, item_stock=? WHERE item_id=?");
			) {
				ps.setString(1, itemName);
				ps.setBigDecimal(2, itemPrice);
				ps.setInt(3, itemStock);
				ps.setString(4, itemId);
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

	public ResultCodeMessage delete(String itemId) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {

			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"DELETE FROM item WHERE item_id=?");
			) {
				ps.setString(1, itemId);
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
