package com.cpw.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;

public class CallOracleProcedure {
	
	public static void main(String[] args) {
		try {
			Connection conn = BasciJDBC.getConnection();
			conn.setAutoCommit(true);
			//CallableStatement call = conn.prepareCall("{call update_user_domain_pro(?,?)}");
			CallableStatement call = conn.prepareCall("{call insert_book(?,?,?)}");
			call.setInt(1, 10000);
			call.setString(2, "COVISINT VI required");
			call.setString(3,"Compuware.com");
			call.execute();
			call.close();
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
