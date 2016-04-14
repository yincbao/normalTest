package com.cpw.transaction.thoery.single.thread;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

/**
 * 
 * ClassName: TransactionManagerStackImpl
 * @description
 * @author yin_changbao
 * @Date   Jul 16, 2015
 *
 */
public class TransactionManagerStackImpl implements TransactionManager {
	private Stack<Connection> connections = new Stack<Connection>();

	@Override
	public Connection getConnection() {
		if (connections.isEmpty()) {
			this.addConn();
		}
		return connections.peek();
	}

	@Override
	public void beginTransaction() {
		this.addConn();
	}

	@Override
	public void commit() {
		try {
			if (connections.peek() != null && !connections.peek().isClosed()) {
				System.out.println(connections.peek().toString() + "--Commit---");
				connections.peek().commit();
				connections.pop().close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void rollback() {
		try {
			if (connections.peek() != null && !connections.peek().isClosed()) {
				System.out.println(connections.peek().toString() + "--Rollback---");
				connections.peek().rollback();
				connections.pop().close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addConn() {
		try {
			Connection con = this.getMysqlConnection();
			con.setAutoCommit(false);
			connections.push(con);
			System.out.println(con.toString() + "--Conection---");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Connection getMysqlConnection() {
		return getConnection("com.mysql.jdbc.Driver", "jdbc:mysql://10.20.71.50:3306/test", "c4d_admin", "123456");
	}

	private Connection getConnection(String driver, String connection, String user, String password) {
		try {
			Class.forName(driver);
			return DriverManager.getConnection(connection, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}