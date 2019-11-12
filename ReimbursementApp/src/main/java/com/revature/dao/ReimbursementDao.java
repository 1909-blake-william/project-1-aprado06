package com.revature.dao;

import java.util.List;

import com.revature.models.Reimbursement;

public interface ReimbursementDao {
	
	ReimbursementDao currentImplementation = new ReimbursementDaoSQL();
	
	List<Reimbursement> viewAll();
	
	List<Reimbursement> viewByStatus(int id);
	
	List<Reimbursement> viewByAuthor(int author);
	
	List<Reimbursement> viewByAuthorStatus(int author, int id);
	
	int createReimb (int id, float amount, String description, int type);
	
	int resolveReimb (int reimid, int id, int status);
	
}
