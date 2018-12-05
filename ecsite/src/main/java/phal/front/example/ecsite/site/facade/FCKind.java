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
public class FCKind {

	@XmlRootElement
	public static class FindResult {
		public String kindId;
		public String kindName;
	};

	public ResultCodeMessage findAll() {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {
			List<FindResult> resultList = new ArrayList<>();
			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"SELECT kind_id, kind_name FROM kind ORDER BY kind_id");
			) {
				try (ResultSet rs = ps.executeQuery()) {
					rcm.code = 100;
					while (rs.next()) {
						FindResult fr = new FindResult();
						fr.kindId = rs.getString(1);
						fr.kindName = rs.getString(2);
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

	public ResultCodeMessage find(String kindId) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {
			FindResult fr = new FindResult();
			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"SELECT kind_id, kind_name FROM kind WHERE kind_id = ?");
			) {
				ps.setString(1, kindId);
				try (ResultSet rs = ps.executeQuery()) {
					rcm.code = 100;
					while (rs.next()) {
						fr.kindId = rs.getString(1);
						fr.kindName = rs.getString(2);
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

	public ResultCodeMessage insert(String kindId, String kindName) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {

			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"INSERT INTO kind(kind_id, kind_name) VALUES(?,?)");
			) {
				ps.setString(1, kindId);
				ps.setString(2, kindName);
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

	public ResultCodeMessage update(String kindId, String kindName) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {

			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"UPDATE kind SET kind_name=? WHERE kind_id=?");
			) {
				ps.setString(1, kindName);
				ps.setString(2, kindId);
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

	public ResultCodeMessage delete(String kindId) {
		ResultCodeMessage rcm = new ResultCodeMessage();
		try {

			try (
					DBConnector acdbc = new DBConnector();
					Connection cn = acdbc.getConnection();
					PreparedStatement ps = cn.prepareStatement(
							"DELETE FROM kind WHERE kind_id=?");
			) {
				ps.setString(1, kindId);
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
