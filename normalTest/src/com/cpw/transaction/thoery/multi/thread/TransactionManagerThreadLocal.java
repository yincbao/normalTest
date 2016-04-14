package com.cpw.transaction.thoery.multi.thread;

import java.sql.Connection;

import com.cpw.transaction.thoery.single.thread.TransactionManager;
import com.cpw.transaction.thoery.single.thread.TransactionManagerStackImpl;

public class TransactionManagerThreadLocal implements TransactionManager {
	
	private static final ThreadLocal<TransactionManager> tranManager = new ThreadLocal<TransactionManager>() {
		protected TransactionManager initialValue() {
			System.out.println(this.toString() + "--Thread Local Initialize--");
			return new TransactionManagerStackImpl();
			}
		};
		@Override
		public void beginTransaction() {
			tranManager.get().beginTransaction();
			}
		@Override
		public void commit() {
			tranManager.get().commit();
			}
		@Override
		public void rollback() {
			tranManager.get().rollback();
			}
		@Override
		public Connection getConnection() {
			return tranManager.get().getConnection();
			}
		}
