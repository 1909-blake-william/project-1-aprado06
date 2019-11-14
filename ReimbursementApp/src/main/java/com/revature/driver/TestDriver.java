package com.revature.driver;

import java.util.ArrayList;
import java.util.List;

import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.models.Reimbursement;
import com.revature.models.User;


public class TestDriver {

	public static void main(String[] args) {
		UserDao userdao = UserDao.currentImplementation;
		ReimbursementDao reimburse = ReimbursementDao.currentImplementation;
		
//		List<User> output = new ArrayList<User>();
//		output = userdao.findAll();
//		for(User a: output) {
//			System.out.println(a);
//		}
		System.out.println(userdao.findByUsernameAndPassword("nf06","emp2"));
		//reimburse.createReimb(2, 30, "housing", 1);
		List<Reimbursement> output = new ArrayList<Reimbursement>();
		output = reimburse.viewAll();
		for(Reimbursement a: output) {
			System.out.println(a);
		}
	}
}
