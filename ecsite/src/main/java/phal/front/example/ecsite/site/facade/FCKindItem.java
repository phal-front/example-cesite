package phal.front.example.ecsite.site.facade;

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
public class FCKindItem {

	@XmlRootElement
	public static class FindResult {
		public String kindId;
		public String itemId;
	};

	public ResultCodeMessage findAll() {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {
			List<FindResult> resultList = new ArrayList<>();
			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"SELECT kind_id, item_id FROM kind_item ORDER BY kind_id, item_id");
			) {
				try (ResultSet rs = ps.executeQuery()) {
					rcm.code = 100;
					while (rs.next()) {
						FindResult fr = new FindResult();
						fr.kindId = rs.getString(1);
						fr.itemId = rs.getString(2);
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

	public ResultCodeMessage find(String kindId, String itemId) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {
			FindResult fr = new FindResult();
			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"SELECT kind_id, item_id FROM kind_item WHERE kind_id=? AND item_id=?");
			) {
				ps.setString(1, kindId);
				ps.setString(2, itemId);
				try (ResultSet rs = ps.executeQuery()) {
					rcm.code = 100;
					while (rs.next()) {
						fr.kindId = rs.getString(1);
						fr.itemId = rs.getString(2);
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

	public ResultCodeMessage insert(String kindId, String itemId) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {

			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"INSERT INTO kind_item(kind_id, item_id) VALUES(?,?)");
			) {
				ps.setString(1, kindId);
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


	public ResultCodeMessage delete(String kindId,  String itemId) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {

			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"DELETE FROM kind_item WHERE kind_id=? AND item_id=?");
			) {
				ps.setString(1, kindId);
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
