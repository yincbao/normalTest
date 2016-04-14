package com.cpw.transaction.thoery.single.thread;

import java.sql.Connection;

public interface TransactionManager {
	 
    Connection getConnection();
    void beginTransaction();
    void commit();
    void rollback();
}