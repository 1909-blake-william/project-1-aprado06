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
		return new Reimbursement(id, amount, submitted, resolved, desc, receipt, rsUser, resolver, status, type);
	}
	
	@Override
	public List<Reimbursement> viewAll() {
		try (Connection c = ConnectionUtil.getConnection()) {
			String query = "Select * from ers_reimbursement";

			PreparedStatement ps = c.prepareStatement(query);

			ResultSet rs = ps.executeQuery();
			List<Reimbursement> reimburse = new ArrayList<Reimbursement>();
			while(rs.next()) {
				Reimbursement reim = new Reimbursement(rs.getInt("reimb_id"), rs.getFloat("reimb_amount"), rs.getTimestamp("reimb_submitted"), rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"), rs.getBlob("reimb_receipt"),
						rs.getInt("reimb_author"), rs.getInt("reimb_resolver"), rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"));
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
			String query = "Select * from ers_reimbursement " + "WHERE reimb_status_id = ?";

			PreparedStatement ps = c.prepareStatement(query);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			
			List<Reimbursement> reimburse = new ArrayList<Reimbursement>();
			while(rs.next()) {
				Reimbursement reim = new Reimbursement(rs.getInt("reimb_id"), rs.getFloat("reimb_amount"), rs.getTimestamp("reimb_submitted"), rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"), rs.getBlob("reimb_receipt"),
						rs.getInt("reimb_author"), rs.getInt("reimb_resolver"), rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"));
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
			String query = "Select * from ers_reimbursement " + "WHERE reimb_author = ?";

			PreparedStatement ps = c.prepareStatement(query);
			ps.setInt(1, author);

			ResultSet rs = ps.executeQuery();
			
			List<Reimbursement> reimburse = new ArrayList<Reimbursement>();
			while(rs.next()) {
				Reimbursement reim = new Reimbursement(rs.getInt("reimb_id"), rs.getFloat("reimb_amount"), rs.getTimestamp("reimb_submitted"), rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"), rs.getBlob("reimb_receipt"),
						rs.getInt("reimb_author"), rs.getInt("reimb_resolver"), rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"));
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
			String query = "Select * from ers_reimbursement " + "WHERE reimb_author = ? AND reimb_status_id = ?";

			PreparedStatement ps = c.prepareStatement(query);
			ps.setInt(1, author);
			ps.setInt(2, id);

			ResultSet rs = ps.executeQuery();
			
			List<Reimbursement> reimburse = new ArrayList<Reimbursement>();
			while(rs.next()) {
				Reimbursement reim = new Reimbursement(rs.getInt("reimb_id"), rs.getFloat("reimb_amount"), rs.getTimestamp("reimb_submitted"), rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"), rs.getBlob("reimb_receipt"),
						rs.getInt("reimb_author"), rs.getInt("reimb_resolver"), rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"));
				reimburse.add(reim);
			}
			return reimburse;
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public int createReimb(int id, float amount, String description, int type) {
		try (Connection c = ConnectionUtil.getConnection()) {

			String query = "INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id )"
					+ " VALUES (reimb_id_seq.nextval, ?, SYSTIMESTAMP, null, ?, null, ?, null, 1, ?)";

			PreparedStatement ps = c.prepareStatement(query);
			ps.setFloat(1, amount);
			ps.setString(2, description);
			ps.setInt(3, id);
			ps.setInt(4, type);

			return ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return 0;
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
