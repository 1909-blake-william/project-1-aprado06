package com.revature.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.util.ConnectionUtil;

public class ReimbursementDaoSQL implements ReimbursementDao {

	Reimbursement extractReim(ResultSet rs) throws SQLException {
		int id = rs.getInt("reimb_id");
		float amount = rs.getFloat("reimb_amount");
		Timestamp submitted = rs.getTimestamp("reimb_submitted");
		Timestamp resolved = rs.getTimestamp("reimb_resolved");
		String desc = rs.getString("reimb_description");
		Blob receipt = rs.getBlob("reimb_receipt");
		int rsUser = rs.getInt("reimb_author"); 
		int resolver = rs.getInt("reimb_resolver");
		int status = rs.getInt("reimb_status_id");
		int type = rs.getInt("reimb_type_id");
		String author = rs.getString(11);
		String resolverName = rs.getString(12);
		return new Reimbursement(id, amount, submitted, resolved, desc, receipt, rsUser, resolver, status, type, author, resolverName);
	}
	
	@Override
	public List<Reimbursement> viewAll() {
		try (Connection c = ConnectionUtil.getConnection()) {
			String query = "SELECT er.reimb_id, er.reimb_amount, er.reimb_submitted, er.reimb_resolved, er.reimb_description, er.reimb_receipt, er.reimb_author, er.reimb_resolver, er.reimb_status_id, er.reimb_type_id, eu.user_first_name, eu2.user_first_name FROM (ers_reimbursement er LEFT JOIN ers_users eu ON (er.reimb_author = eu.ers_users_id)) LEFT JOIN ers_users eu2 ON (er.reimb_resolver = eu2.ers_users_id)";

			PreparedStatement ps = c.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			List<Reimbursement> reimburse = new ArrayList<Reimbursement>();
			while(rs.next()) {
				Reimbursement reim = new Reimbursement(rs.getInt("reimb_id"), rs.getFloat("reimb_amount"), rs.getTimestamp("reimb_submitted"), rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"), rs.getBlob("reimb_receipt"),
						rs.getInt("reimb_author"), rs.getInt("reimb_resolver"), rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"), rs.getString(11), rs.getString(12));
				reimburse.add(reim);
			}
			return reimburse;
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public List<Reimbursement> viewByStatus(int id) {
		try (Connection c = ConnectionUtil.getConnection()) {
			String query = "SELECT er.reimb_id, er.reimb_amount, er.reimb_submitted, er.reimb_resolved, er.reimb_description, er.reimb_receipt, er.reimb_author, er.reimb_resolver, er.reimb_status_id, er.reimb_type_id, eu.user_first_name, eu2.user_first_name  FROM ers_reimbursement er\n" + 
					"LEFT JOIN ers_users eu\n" + 
					"ON er.reimb_author = eu.ers_users_id\n" + 
					"LEFT JOIN ers_users eu2\n" + 
					"ON er.reimb_resolver = eu2.ers_users_id\n" + "WHERE reimb_status_id = ?";

			PreparedStatement ps = c.prepareStatement(query);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			
			List<Reimbursement> reimburse = new ArrayList<Reimbursement>();
			while(rs.next()) {
				Reimbursement reim = new Reimbursement(rs.getInt("reimb_id"), rs.getFloat("reimb_amount"), rs.getTimestamp("reimb_submitted"), rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"), rs.getBlob("reimb_receipt"),
						rs.getInt("reimb_author"), rs.getInt("reimb_resolver"), rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"),rs.getString(11),rs.getString(12));
				reimburse.add(reim);
			}
			return reimburse;
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public List<Reimbursement> viewByAuthor(int author) {
		try (Connection c = ConnectionUtil.getConnection()) {
			String query = "SELECT er.reimb_id, er.reimb_amount, er.reimb_submitted, er.reimb_resolved, er.reimb_description, er.reimb_receipt, er.reimb_author, er.reimb_resolver, er.reimb_status_id, er.reimb_type_id, eu.user_first_name, eu2.user_first_name  FROM ers_reimbursement er\n" + 
					"LEFT JOIN ers_users eu\n" + 
					"ON er.reimb_author = eu.ers_users_id\n" + 
					"LEFT JOIN ers_users eu2\n" + 
					"ON er.reimb_resolver = eu2.ers_users_id\n" + "WHERE reimb_author = ?";

			PreparedStatement ps = c.prepareStatement(query);
			ps.setInt(1, author);

			ResultSet rs = ps.executeQuery();
			
			List<Reimbursement> reimburse = new ArrayList<Reimbursement>();
			while(rs.next()) {
				Reimbursement reim = new Reimbursement(rs.getInt("reimb_id"), rs.getFloat("reimb_amount"), rs.getTimestamp("reimb_submitted"), rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"), rs.getBlob("reimb_receipt"),
						rs.getInt("reimb_author"), rs.getInt("reimb_resolver"), rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"), rs.getString(11), rs.getString(12));
				reimburse.add(reim);
			}
			return reimburse;
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public List<Reimbursement> viewByAuthorStatus(int author, int id) {
		try (Connection c = ConnectionUtil.getConnection()) {
			String query = "SELECT er.reimb_id, er.reimb_amount, er.reimb_submitted, er.reimb_resolved, er.reimb_description, er.reimb_receipt, er.reimb_author, er.reimb_resolver, er.reimb_status_id, er.reimb_type_id, eu.user_first_name, eu2.user_first_name  FROM ers_reimbursement er\n" + 
					"LEFT JOIN ers_users eu\n" + 
					"ON er.reimb_author = eu.ers_users_id\n" + 
					"LEFT JOIN ers_users eu2\n" + 
					"ON er.reimb_resolver = eu2.ers_users_id\n" + "WHERE reimb_author = ? AND reimb_status_id = ?";

			PreparedStatement ps = c.prepareStatement(query);
			ps.setInt(1, author);
			ps.setInt(2, id);

			ResultSet rs = ps.executeQuery();
			
			List<Reimbursement> reimburse = new ArrayList<Reimbursement>();
			while(rs.next()) {
				Reimbursement reim = new Reimbursement(rs.getInt("reimb_id"), rs.getFloat("reimb_amount"), rs.getTimestamp("reimb_submitted"), rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"), rs.getBlob("reimb_receipt"),
						rs.getInt("reimb_author"), rs.getInt("reimb_resolver"), rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"),rs.getString(11),rs.getString(12));
				reimburse.add(reim);
			}
			return reimburse;
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public Reimbursement createReimb(int id, float amount, String description, int type) {
		try (Connection c = ConnectionUtil.getConnection()) {

			String query = "INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id )"
					+ " VALUES (reimb_id_seq.nextval, ?, SYSTIMESTAMP, null, ?, null, ?, null, 1, ?)";

			PreparedStatement ps = c.prepareStatement(query);
			ps.setFloat(1, amount);
			ps.setString(2, description);
			ps.setInt(3, id);
			ps.setInt(4, type);

			ps.executeUpdate();
			
			String query1 = "SELECT er.reimb_id, er.reimb_amount, er.reimb_submitted, er.reimb_resolved, er.reimb_description, er.reimb_receipt, er.reimb_author, er.reimb_resolver, er.reimb_status_id, er.reimb_type_id, eu.user_first_name, eu2.user_first_name FROM (ers_reimbursement er LEFT JOIN ers_users eu ON (er.reimb_author = eu.ers_users_id)) LEFT JOIN ers_users eu2 ON (er.reimb_resolver = eu2.ers_users_id) WHERE er.reimb_description = ?";
			
			PreparedStatement ps1 = c.prepareStatement(query1);
			ps1.setString(1, description);

			ResultSet rs = ps1.executeQuery();
			Reimbursement out = null;
			
			while(rs.next()) {
				out = extractReim(rs);
			}
			
			return out;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			Reimbursement o = new Reimbursement(); 
			return o;
		}
	}

	@Override
	public int resolveReimb(int reimid, int id, int status) {
		try (Connection c = ConnectionUtil.getConnection()) {

			String query = "UPDATE ers_reimbursement SET reimb_resolved = SYSTIMESTAMP, reimb_resolver = ?, reimb_status_id = ? WHERE reimb_id = ?";

			PreparedStatement ps = c.prepareStatement(query);
			ps.setInt(1, id);
			ps.setInt(2, status);
			ps.setInt(3, reimid);

			return ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return 0;
		}
	}

	
}
