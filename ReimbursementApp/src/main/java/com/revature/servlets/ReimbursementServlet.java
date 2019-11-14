package com.revature.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.ReimbursementDao;
import com.revature.models.Reimbursement;

public class ReimbursementServlet extends HttpServlet {

	private ReimbursementDao reimDao = ReimbursementDao.currentImplementation;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
		resp.addHeader("Access-Control-Allow-Origin", "http://localhost:5500");
		resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		resp.addHeader("Access-Control-Allow-Headers",
				"Origin, Methods, Credentials, X-Requested-With, Content-Type, Accept");
		resp.addHeader("Access-Control-Allow-Credentials", "true");
		resp.setContentType("application/json");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<Reimbursement> reims = new ArrayList<Reimbursement>();

		reims = reimDao.viewAll();

		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(reims);

		resp.addHeader("content-type", "application/json");
		resp.getWriter().write(json);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// read the pokemon from the request body
		if ("/ReimbursementApp/reimbursements/update".equals(req.getRequestURI())) {
			ObjectMapper om = new ObjectMapper();
			Reimbursement p = (Reimbursement) om.readValue(req.getReader(), Reimbursement.class);

			int o = reimDao.resolveReimb(p.getReimb_id(), p.getReimb_resolver(), p.getReimb_status_id());
			//System.out.println(o);
			
			String json = om.writeValueAsString(o);

			resp.getWriter().write(json);
			resp.setStatus(201); // created status code
		} else {
		ObjectMapper om = new ObjectMapper();
		Reimbursement p = (Reimbursement) om.readValue(req.getReader(), Reimbursement.class);

		Reimbursement o = reimDao.createReimb(p.getReimb_author(), p.getReimb_amount(), p.getReimb_description(), p.getReimb_type_id());
		//System.out.println(o);
		
		String json = om.writeValueAsString(o);

		resp.getWriter().write(json);
		resp.setStatus(201); // created status code
		}
	}
	
}
