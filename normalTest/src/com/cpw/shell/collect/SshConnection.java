package com.cpw.shell.collect;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SshConnection {
	
	private static final Log logger = LogFactory.getLog(SshConnection.class);
	
	private static final int PORT_DEFAULT_SSH = 22;
	private int ltime = 0;
	
	InputStream in;
	
	 private Session session = null;
	 
	 protected static final int DEFAULT_TIMEOUT_EXEC = 30000;
	 
	 private static JSch jsch = new JSch();
	 
	 private Channel channel;
	 OutputStream commandIO;
	 private InputStream sessionInput = null;
	 private static List<Expect> sshExpects = null;
		
		static{
			sshExpects = new ArrayList<Expect>();
			List<String> expectStrs = new ArrayList<String>();
			expectStrs.add(".*#");
			expectStrs.add(".*\\$$");
			expectStrs.add(".*>");
			expectStrs.add(".*%");
			expectStrs.add(".*in unit1 login");
			expectStrs.add(".*#\\s*\\[m");
			Expect expect = new Expect();
			expect.setExpectStrs(expectStrs);
			expect.setFailExpect(false);
			sshExpects.add(expect);
		}
	 private void loginAsset(String ip, String port,String accountName,String password){


	        int iPort = PORT_DEFAULT_SSH;
	        if (port != null && port.length() > 0) {
	            iPort = Integer.parseInt(port);
	        }
	        try {
	        	if (!connectRemoteHost(ip, iPort, accountName, password)) {
	        		String err = "Failed to connect remote host : " + (ip == null ? "NULL" : ip) + " on " + iPort;
	        		logger.error(err);
	        		throw new Exception(err);
	        	}
	    		readUntil(in, sshExpects, 60000); 
	    		List<String> commandList = new ArrayList<String>();
	    		commandList.add("cd /surcdn/log");
	    		commandList.add("tail -100f portal.log");
	    		exeCommand(commandList);
	        	/*切换账号，或者输入super 口令
	        	 * ltime++;
	            execSwitchAccount(df);
	            ltime++;
	            execEnable(df);
	            ltime++;*/
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	
	public boolean connectRemoteHost(String ip, int port, String loginName, String password) {
    	boolean r = false;
    	for (int i = 0; i < 3; i++) {
			logger.debug("Trying SSH login ... " + (i + 1));
			r = connectRemoteHost0(ip, port, loginName, password);
			if (r)
				break; //如果成功登录则退出
			try {
				Thread.sleep(1000); //暂停1秒后，再次尝试
			} catch (Exception ex) {
				logger.warn("Thread sleep error while trying SSH login");
			}
		}
    	return r;
    }
	
	 public boolean connectRemoteHost0(String ip, int port, String loginName, String password) {
			java.util.Properties config1 = new java.util.Properties();
			config1.put("StrictHostKeyChecking", "no");

			java.util.Properties config2 = new java.util.Properties();
			config2.put("StrictHostKeyChecking", "no");
			config2.put("PreferredAuthentications", "publickey,password");

			if (connectAsset(ip, port, loginName, password,config1)) {
				return true;
			} else if(connectAsset(ip, port, loginName, password,config2)){
				return true;
			}
	    	return false;
	    }
	 
	 private boolean connectAsset(String ip, int iPort, String accountName, String password, Properties config) {
	    	try {
		        session = jsch.getSession(accountName, ip, iPort);
		        
				session.setConfig(config);
				
		        session.setPassword(password);
		        session.setTimeout(DEFAULT_TIMEOUT_EXEC);
		        session.connect();
		        
		        channel = session.openChannel("shell");
		        commandIO = new PipedOutputStream();
		        sessionInput = new PipedInputStream((PipedOutputStream) commandIO);
		        channel.setInputStream(sessionInput);
		        in = channel.getInputStream();
		        channel.connect();
		        logger.debug("Succeed to connect to host using SSH2 : " + ip + " on " + iPort);
		        return true;
	    	} catch (Exception e) {
	    		logger.error("Failed to login remot host using SSH2 by default configuration, " + e.getMessage() + (e.getCause() == null ? "" : e.getCause()), e);
	    		return false;
			}
	    }
	 
	 private static int SLEEP_WAIT = 100;
	 protected String readUntil(InputStream in, List<Expect> expects,int timeout) throws Exception{
			String outStr = "";
			Long startTime = System.currentTimeMillis();
			StringBuffer sb = new StringBuffer();

			boolean expected = false;
			while (!expected && System.currentTimeMillis() - startTime < timeout)
			{
				
				sb.append(readResult(in));
				outStr = sb.toString();
				for (Expect expect : expects)
				{
					if (expect.match(outStr.trim()))
					{
						expected = true;
						if (expect.isFailExpect())
						{
							throw new Exception("Excute Failed:" + outStr + "\n");
						}
						break;
					}
				}
				
				Thread.sleep(SLEEP_WAIT);
			}
			logger.debug("data from ssh :"+outStr);
			if(!expected)
			{
				throw new Exception("Excute command Timeout Or no expected String:" + outStr + "\n");
			}
			return outStr;
		}
	 private static final int BUFFER_SIZE = 1024;
	 private String readResult(InputStream in) throws IOException
		{
			StringBuffer sb = new StringBuffer();
			byte[] tmp = new byte[BUFFER_SIZE];
			String encoding = "";
			if (encoding==null || "".equals(encoding.trim())) {
				encoding = "GBK";
			}
			while (in.available() > 0)
			{
				int i = in.read(tmp);
				if (i < 0)
				{
					break;
				}
				String tmpStr = null;
				tmpStr = new String(tmp,0,i,encoding);
				tmpStr = XmlUtil.replaceSpecialCharWithBlank(tmpStr);
				sb.append(tmpStr);
			}
			return sb.toString();
		}
	 
	 
	 public static void main(String[] args) {
		 PropertyConfigurator.configure("etc/properties/log4j.properties");
		 SshConnection ssh = new SshConnection();
		 ssh.loginAsset("172.16.1.80", "22", "root", "cjtxrss123");
		 
	}
	 
	 public void exeCommand(List<String> commandList) throws Exception{
		 
		 for(String command:commandList){
			 commandIO.write((command+" \n").getBytes());
			 commandIO.flush();
			 Thread.sleep(SLEEP_WAIT);
				// channel.getInputStream()
				logger.debug("ddd: "+readResult( in)); 
		 }
		 /*commandIO.write(" ".getBytes());
		 commandIO.flush();
		 commandIO.write(("ls \n").getBytes());
		 commandIO.flush();
		 Thread.sleep(SLEEP_WAIT);
		// channel.getInputStream()
		logger.debug("ddd: "+readResult( in)); */
	 }
	 
	 
	 
	 
	 
	 
	 ////////////////////
	 
	 
	 /**
	  * 利用JSch包实现远程主机SHELL命令执行
	  * @param ip 主机IP
	  * @param user 主机登陆用户名
	  * @param psw  主机登陆密码
	  * @param port 主机ssh2登陆端口，如果取默认值，传-1
	  * @param privateKey 密钥文件路径
	  * @param passphrase 密钥的密码
	  */
	 public static void sshShell(String ip, String user, String psw
	 		,int port ,String privateKey ,String passphrase) throws Exception{
	 	Session session = null;
	 	Channel channel = null;

	 	
	 	JSch jsch = new JSch();

	 	//设置密钥和密码
	 	if (privateKey != null && !"".equals(privateKey)) {
	            if (passphrase != null && "".equals(passphrase)) {
	            	//设置带口令的密钥
	                jsch.addIdentity(privateKey, passphrase);
	            } else {
	            	//设置不带口令的密钥
	                jsch.addIdentity(privateKey);
	            }
	        }
	 	
	 	if(port <=0){
	 		//连接服务器，采用默认端口
	 		session = jsch.getSession(user, ip);
	 	}else{
	 		//采用指定的端口连接服务器
	 		session = jsch.getSession(user, ip ,port);
	 	}

	 	//如果服务器连接不上，则抛出异常
	 	if (session == null) {
	 		throw new Exception("session is null");
	 	}
	 	
	 	//设置登陆主机的密码
	 	session.setPassword(psw);//设置密码   
	 	//设置第一次登陆的时候提示，可选值：(ask | yes | no)
	 	session.setConfig("StrictHostKeyChecking", "no");
	 	//设置登陆超时时间   
	 	session.connect(30000);
	 		
	 	try {
	 		//创建sftp通信通道
	 		channel = (Channel) session.openChannel("shell");
	 		channel.connect(1000);

	 		//获取输入流和输出流
	 		InputStream instream = channel.getInputStream();
	 		OutputStream outstream = channel.getOutputStream();
	 		
	 		//发送需要执行的SHELL命令，需要用\n结尾，表示回车
	 		String shellCommand = "ls \n";
	 		outstream.write(shellCommand.getBytes());
	 		outstream.flush();


	 		//获取命令执行的结果
	 		if (instream.available() > 0) {
	 			byte[] data = new byte[instream.available()];
	 			int nLen = instream.read(data);
	 			
	 			if (nLen < 0) {
	 				throw new Exception("network error.");
	 			}
	 			
	 			//转换输出结果并打印出来
	 			String temp = new String(data, 0, nLen,"iso8859-1");
	 			System.out.println(temp);
	 		}
	 	    outstream.close();
	 	    instream.close();
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 	} finally {
	 		session.disconnect();
	 		channel.disconnect();
	 	}
	 }
	 
	 
}
