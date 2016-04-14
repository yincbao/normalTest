package com.cpw.procedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class CallProcedure {

	private static Connection conn = null;
	CallableStatement call =null;
	
	@Before
	public void init() {
		if(conn ==null)
		conn = BasciJDBC.getConnection();
	}
	
	@Test
	public void testInsertOneLine(){
		
		try {
			call = conn.prepareCall("{call insert_book(?,?,?)}");
			call.setInt(1, 10001);
			call.setString(2, "COVISINT VI required 10001");
			call.setString(3,"Compuware.com 10001");
			call.execute();
			call.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInsertMulitLine(){
		try {
			call = conn.prepareCall("{call insert_mulit_line(?,?)");
			call.setString(1,"covisint");
			call.setString(2, "compuware.com");
			call.execute();
			call.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 调用单一记录的存储过程
	 */
	@Test
	public void TestSelectBookLine(){
		try {
			
			call = conn.prepareCall("{call select_book_line(?,?)}");
			call.setInt(1,10003);
			call.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);
			call.execute();
			String name = call.getString(2);
			System.out.println("test get one line result procedure--> book name: "+name);
			call.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 调用多条记录的存储过程
	 */
	@Test
	public void TestSelectBookList(){
		try{
			call = conn.prepareCall("{call select_book_list(?,?)");
			call.setInt(1, 10001);
			call.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet)call.getObject(2);
			while(rs.next()){
				System.out.println("|book id: "+rs.getInt(1)+"| book name: "+rs.getString(2)+"| publish house: "+rs.getString(3)+" |");
				System.out.println("\r\n");
			}
		}catch(Exception e ){
			e.printStackTrace();
		}
	}
	
	/**
	 * param 1:每页数量
	 * param 2:页数
	 * param 3：返回游标
	 */
	@Test
	public void testPagingResultPro(){
		try{
			call = conn.prepareCall("{call paging_result_pro(?,?,?)}");
			call.setInt(1, 10);
			call.setInt(2, 3);//第一页
			call.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(3);
			while(rs.next()){
				System.out.println("|book id: "+rs.getInt(1)+"| book name: "+rs.getString(2)+"| publish house: "+rs.getString(3)+" |");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGeneralPpgingPro(){
		try{
			call = conn.prepareCall("{call general_paging_pro(?,?,?,?,?,?)}");
			call.setInt(1, 10);
			call.setInt(2, 2);
			call.setString(3, "book");
			call.registerOutParameter(4, oracle.jdbc.OracleTypes.INTEGER);
			call.registerOutParameter(5, oracle.jdbc.OracleTypes.INTEGER);
			call.registerOutParameter(6, oracle.jdbc.OracleTypes.CURSOR);
			call.execute();
			int rows =  call.getInt(4);
			int pages = call.getInt(5);
			ResultSet rs = (ResultSet) call.getObject(6);
			while(rs.next()){
				System.out.println("|book id: "+rs.getInt(1)+"| book name: "+rs.getString(2)+"| publish house: "+rs.getString(3)+" |");
			}
			System.out.println("totally recodes: "+rows+ ", totally pages: "+pages);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
