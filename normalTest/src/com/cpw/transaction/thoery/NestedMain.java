package com.cpw.transaction.thoery;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.cpw.transaction.thoery.multi.thread.TransactionManagerThreadLocal;


/**
 * 模拟 spring txMgr 传播行为 nested，
 * 
 * ClassName: NestedMain
 * @description
 * @author yin_changbao
 * @Date   Jul 16, 2015
 *
 */
public class NestedMain implements Runnable {
	private int v = 0;
	private String name;
	NestedMain(int v, String name) {
		this.v = v;        this.name = name;
		}
	public static void main(String[] args) throws Exception{
		for (int i = 0; i< 3; i++) {
			NestedMain main = new NestedMain(i * 10, "Ravi" + i);
			new Thread(main).start();
			}
		}
	@Override
	public void run() {
		try {
			TransactionManagerThreadLocal local = new TransactionManagerThreadLocal();
			// Transaction 1 ( outer )
			local.beginTransaction();
			Connection con = local.getConnection();
			String sql = "INSERT INTO test_tran (emp_id, name) VALUES ('1"+v+"', '"+ name+v+"')";
			this.insert(con, sql);
			// Transaction 2 ( Inner )
			
			local.beginTransaction();
			con = local.getConnection();
			sql = "INSERT INTO test_tran (emp_id, name) VALUES ('2"+v+"', '"+ name+v+"')";
			this.insert(con, sql);
			local.commit(); // Committing 2
			local.rollback(); // Rollback 1 Outer
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	private void insert(Connection con, String sql) throws SQLException {
		Statement statement = con.createStatement();

		int updateResult = statement.executeUpdate(sql);

	}
}