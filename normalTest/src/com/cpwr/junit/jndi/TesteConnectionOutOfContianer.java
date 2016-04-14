package com.cpwr.junit.jndi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;

import org.junit.Before;
import org.junit.Test;

public class TesteConnectionOutOfContianer {
	
	public static Connection conn;
	public static  String jndiName = "jdbc/ugm";
	public static Statement st;
	public static OracleConnectionPoolDataSource ds;

	@Before
	public void init() throws Exception {
//		DataSource  dataSource      = null;
//        Context  ctx  = new javax.naming.InitialContext();
//        Context envCtx = (Context) ctx.lookup("java:comp/env");
//        dataSource = (javax.sql.DataSource)envCtx.lookup( jndiName );
//        conn = dataSource.getConnection();
		
		try {
            // Create initial context
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES, 
                "org.apache.naming");            
            InitialContext ic = new InitialContext();

            ic.createSubcontext("java:");
            ic.createSubcontext("java:/comp");
            ic.createSubcontext("java:/comp/env");
            ic.createSubcontext("java:/comp/env/jdbc");
           
            // Construct DataSource
            ds = new OracleConnectionPoolDataSource();
            ds.setURL("jdbc:oracle:thin:@172.28.52.248:1521:ORCL");
            ds.setUser("ugm");
            ds.setPassword("ugm");
           
            ic.bind("java:/comp/env/jdbc/ugm", ds);
        } catch (NamingException ex) {
//            Logger.getLogger(MyDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
	@Test
	public void testI() throws Exception{
		 Context initContext = new InitialContext();
	        Context webContext = (Context)initContext.lookup("java:/comp/env");

	        DataSource ds = (DataSource) webContext.lookup(jndiName);
	        conn = ds.getConnection();
		if(conn==null){
			System.out.println("conn is null");
			System.exit(0);
		}
		
		st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from user_domain");
		while(rs.next()){
			System.out.println("user domain key "+ rs.getInt(1));
		}
		
	}
}
