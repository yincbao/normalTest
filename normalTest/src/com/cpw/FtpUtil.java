package com.cpw;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;


public class FtpUtil {
	
	public static void main(String[] args) throws IOException {
		FtpUtil ft = new FtpUtil("172.16.2.80", 21, "upload", "upload");
		ft.connect();
		ft.disconnect();
		
	}
	private static final Log log = LogFactory.getLog(FtpUtil.class);

	private static final String ENCODING = "UTF-8";

	public FTPClient ftpClient = new FTPClient();

	private String ftpIp;
	private int ftpPort;

	private String username;
	private String pwd;

	public FtpUtil() {
		super();
	}

	/**
	 * 构�?函数
	 * 
	 * @param ftpIp
	 *            FTP服务器地�?
	 * @param ftpPort
	 *            FTP服务器端�?
	 * @param username
	 *            FTP用户�?
	 * @param pwd
	 *            FTP用户密码
	 */
	public FtpUtil(String ftpIp, int ftpPort, String username, String pwd) {
		this.ftpIp = ftpIp;
		this.ftpPort = ftpPort;
		this.username = username;
		this.pwd = pwd;
	}

	/**
	 * 连接到FTP服务�?
	 * 
	 * @return 链接是否成功
	 * @throws IOException
	 */
	public synchronized boolean connect() throws IOException {
		try {
			// 设置PassiveMode传输
			ftpClient.enterLocalPassiveMode();
			// 设置传输编码
			ftpClient.setControlEncoding(ENCODING);
			// ftpClient.setConnectTimeout(60);
			// ftpClient.setControlKeepAliveReplyTimeout(60);
			// ftpClient.setControlKeepAliveTimeout(60);
			// // 设置将上传过程中使用到的FTP命令输出到控制台
			// this.ftpClient.addProtocolCommandListener(new
			// PrintCommandListener(new PrintWriter(System.out)));
			if(!ftpClient.isConnected()){
				ftpClient.connect(ftpIp, ftpPort);
				if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
					try{
						boolean r = ftpClient.login(username, pwd);
						if (r) {
							log.info("FTP登录成功");
							return true;
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}
			}else{
//				if(!ftpClient.isAvailable()){
//					if (ftpClient.login(username, pwd)) {
//						return true;
//					}
//				}
			}
		} catch (IOException e) {
			try {
				disconnect();
			} catch (Exception e2) {
			}
			throw e;
		}
		return true;
	}

	/**
	 * 断开与远程服务器的连�?
	 * 
	 * @throws IOException
	 */
	public void disconnect() throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.logout();
			ftpClient.disconnect();
		}
	}

	/**
	 * <pre>
	 * 把本地文件上传到FTP服务�?
	 * 把本地文件内容复制到远程文件�?
	 * </pre>
	 * 
	 * @param localFile
	 *            本地文件
	 * @param remoteFile
	 *            远程文件
	 * @return
	 * @throws IOException
	 */
	public UploadStatus uploadFile(String localFile, String remoteFile)
			throws IOException {
		// 置为根目录下
		changeRemoteDir("/");
		UploadStatus result;
		// 设置以二进制流的方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		// 把上传目录中的�?\"替换为�?/�?
		localFile = localFile.replace("\\", "/");
		remoteFile = remoteFile.replace("\\", "/").replace("//", "/");
		// 对远程目录的处理
		String remoteFileName = remoteFile;
		if (remoteFile.contains("/")) {
			remoteFileName = remoteFile
					.substring(remoteFile.lastIndexOf("/") + 1);
			// 创建服务器远程目录结构，创建失败直接返回
			if (createDirecroty(remoteFile) == UploadStatus.Create_Directory_Fail) {
				return UploadStatus.Create_Directory_Fail;
			}
		}
		File lFile = new File(localFile);
		// �?��上传文件
		result = uploadFile2FTP(lFile, remoteFileName);
		return result;
	}

	/**
	 * <pre>
	 * 上传文件夹，把指定文件夹下的文件和子目录都上传到服务器�?
	 * </pre>
	 * 
	 * @param localDir
	 *            本地绝对路径
	 * @param remoteDir
	 *            远程绝对路径，路径名末尾为�?/�?
	 * @throws IOException
	 */
	public void uploadDirectory(String localDir, String remoteDir)
			throws IOException {
		if (localDir == null || remoteDir == null || "".equals(localDir)) {
			return;
		}
		// 置为根目录下
		changeRemoteDir("/");
		// 设置以二进制流的方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		// 把上传目录中的�?\"替换为�?/�?
		localDir = localDir.replace("\\", "/");
		remoteDir = remoteDir.replace("\\", "/");
		// 创建远程目录
		createDirecroty(remoteDir);
		// 改变FTP服务器的路径为remoteDir
		changeRemoteDir(remoteDir);
		// 去掉目录末尾的�?/�?
		int i = localDir.lastIndexOf("/") + 1;
		if (i == localDir.length()) {
			localDir = localDir.substring(0, localDir.lastIndexOf("/"));
		}
		// 获取上传文件路径的根目录
		String root = localDir.substring(localDir.lastIndexOf("/") + 1);
		// 上传目录
		uploadDir(localDir, remoteDir, root);
	}

	/**
	 * 递归创建远程服务器目�?
	 * 
	 * @param remote
	 *            远程服务器文件绝对路径，如果是目录则末尾必须�?/"
	 * @return 目录创建是否成功
	 * @throws IOException
	 */
	private UploadStatus createDirecroty(String remote) throws IOException {
		UploadStatus status = UploadStatus.Create_Directory_Success;
		String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		if (!directory.equals("/")
				&& !ftpClient.changeWorkingDirectory(new String(directory
						.getBytes(ENCODING), "iso-8859-1"))) {
			// 如果远程目录不存在，则�?归创建远程服务器目录
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			while (true) {
				String subDirectory = new String(remote.substring(start, end)
						.getBytes(ENCODING), "iso-8859-1");
				if (!ftpClient.changeWorkingDirectory(subDirectory)) {
					if (ftpClient.makeDirectory(subDirectory)) {
						ftpClient.changeWorkingDirectory(subDirectory);
					} else {
						log.info("创建目录失败");
						return UploadStatus.Create_Directory_Fail;
					}
				}

				start = end + 1;
				end = directory.indexOf("/", start);

				// �?���?��目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return status;
	}

	/**
	 * 上传文件夹，把本地文件夹复制到FTP服务�?
	 * 
	 * @param localDir
	 *            本地绝对路径，路径名�?��不带�?�?
	 * @param remoteDir
	 *            FTP服务器上的工作目�?
	 * @param root
	 *            上传文件目录的根路径
	 * @return
	 * @throws IOException
	 */
	private final UploadStatus uploadDir(String localDir,
			final String remoteDir, final String root) throws IOException {
		// 初始化状�?
		UploadStatus result = UploadStatus.Upload_New_File_Success;
		// 把上传目录中的�?\"替换为�?/�?
		localDir = localDir.replace("\\", "/");

		File lFile = new File(localDir);

		// 如果文件时目�?
		if (lFile.isDirectory()) {

			String baseDir = localDir.substring(localDir.lastIndexOf("/") + 1);

			// 改变FTP服务器的路径
			changeRemoteDir(baseDir);
			// 列出本地文件列表
			File[] subFiles = lFile.listFiles();
			for (final File f : subFiles) {
				// ftpClient.storeFile(f.getName(), new FileInputStream(f));
				uploadDir(f.getPath(), remoteDir, root);
			}
			// 回答上一级目�?
			ftpClient.changeToParentDirectory();
		} else {
			// 改变FTP服务器的路径
			changeRemoteDir(remoteDir);

			int index = localDir.indexOf(root);
			String baseDir = localDir.substring(index,
					localDir.lastIndexOf("/"));

			// 改变FTP服务器的路径
			changeRemoteDir(baseDir);

			String remoteFileName = lFile.getName();
			// �?��上传文件
			result = uploadFile2FTP(lFile, remoteFileName);
		}
		return result;
	}

	private UploadStatus uploadFile2FTP(File lFile, String remoteFileName)
			throws IOException {
		UploadStatus result;
		// �?��远程是否存在文件
		FTPFile[] files = ftpClient.listFiles(remoteFileName);
		if (files.length == 1) {
			// 删除历史文件
			deleteFile(remoteFileName);
		}
		result = continueTransFile(remoteFileName, lFile, 0);
		return result;
	}

	/**
	 * <pre>
	 *   改变FTP服务器的工作路径,
	 *   目录不存在，则创建目录，然后进入目录.
	 * </pre>
	 * 
	 * @param remoteDir
	 * @throws IOException
	 */
	private void changeRemoteDir(String remoteDir) throws IOException {
		if (!ftpClient.changeWorkingDirectory(remoteDir)) {
			if (ftpClient.makeDirectory(remoteDir)) {
				ftpClient.changeWorkingDirectory(remoteDir);
			}
		}
	}

	/**
	 * 上传文件到服务器,文件不存在新上传，如果文件存在则继续上传
	 * 
	 * @param remoteFile
	 *            远程文件名，在上传之前已经将服务器工作目录做了改�?
	 * @param localFile
	 *            本地文件 File句柄，绝对路�?
	 * @param remoteSize
	 *            远程文件大小
	 * @return
	 * @throws IOException
	 */
	private UploadStatus continueTransFile(String remoteFile, File localFile,
			long remoteSize) throws IOException {
		UploadStatus status;
		// 显示进度的上�?
		long step = localFile.length() / 100;
		long process = 0;
		long localreadbytes = 0L;
		RandomAccessFile raf = new RandomAccessFile(localFile, "r");
		OutputStream out = ftpClient.storeFileStream(new String(remoteFile));// .appendFileStream(new
																				// String(remoteFile));
		// 续传
		if (remoteSize > 0) {
			ftpClient.setRestartOffset(remoteSize);
			if (step != 0) {
				process = remoteSize / step;
			}
			raf.seek(remoteSize);
			localreadbytes = remoteSize;
		}
		byte[] bytes = new byte[1024];
		int c;
		while ((c = raf.read(bytes)) != -1) {
			out.write(bytes, 0, c);
			localreadbytes += c;
			if (step != 0) {
				if (localreadbytes / step != process) {
					process = localreadbytes / step;
				}
			}
		}
		out.flush();
		raf.close();
		out.close();
		boolean result = ftpClient.completePendingCommand();
		if (remoteSize > 0) {
			status = result ? UploadStatus.Upload_From_Break_Success
					: UploadStatus.Upload_From_Break_Failed;
		} else {
			status = result ? UploadStatus.Upload_New_File_Success
					: UploadStatus.Upload_New_File_Failed;
		}
		return status;
	}

	/**
	 * 删除文件
	 * 
	 * @param filename
	 * @return boolean
	 * @throws IOException 
	 */
	public boolean deleteFile(String filename) throws IOException {
		boolean flag = true;
		try {
			FTPFile[] files = ftpClient.listFiles(filename);
			if (files.length > 0)
				flag = ftpClient.deleteFile(filename);
		} catch (IOException e) {
			throw e;
		}
		return flag;
	}

	/**
	 * 删除目录
	 * 
	 * @param DirName
	 * @return boolean
	 */
	public boolean deleteDirectory(String dirName) {
		boolean flag = true;
		try {
			FTPFile[] files = ftpClient.listFiles(dirName);
			for (FTPFile file : files) {
				String fileName = dirName + "/" + file.getName();
				if (file.isDirectory()) {
					flag = deleteDirectory(fileName);
				} else {
					flag = deleteFile(fileName);
				}
			}
			if (flag) {
				flag = ftpClient.removeDirectory(dirName);
			}
		} catch (IOException e) {
			flag = false;
		}
		return flag;
	}

	public void moveFile(String from, String to) throws IOException {
		try {
			FTPFile[] files = ftpClient.listFiles(from);
			if (files.length > 0) {
				to = to.replace("\\", "/");
				String baseDir = to.substring(0, to.lastIndexOf("/"));
				String file = to.substring(to.lastIndexOf("/") + 1);
				this.changeRemoteDir(baseDir);
				ftpClient.rename(from, file);
			}

		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 获取当前FTP工作路径
	 * 
	 * @return String
	 * @throws IOException
	 */
	public String getWorkingDirectory() throws IOException {
		return ftpClient.printWorkingDirectory();
	}


	/**
	 * 列出目录下的�?��文件，不包括子目�?
	 * 
	 * @param from
	 * @return
	 * @throws IOException
	 */
	public FTPFile[] listFile(String from) throws IOException {
		return ftpClient.listFiles(from);
	}


	/**
	 * 读取FTP上的文件
	 * 
	 * @param fileName
	 * @return
	 */
	public String readFile(String fileName) {
		InputStream ins = null;
		StringBuilder sb = new StringBuilder(150);
		BufferedReader reader = null;
		try {
			ins = ftpClient.retrieveFileStream(fileName);
			reader = new BufferedReader(new InputStreamReader(ins, ENCODING));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
				}
			}
		}
		try {
			// 主动调用�?��getReply()把接下来�?26消费�? 这样做是可以解决这个返回null问题
			ftpClient.getReply();
		} catch (IOException e) {
		}
		return sb.toString();
	}

	public String getFtpIp() {
		return ftpIp;
	}

	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}

	public int getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}

/**
 * FTP上传文件过程的状�?
 * 
 * @author Administrator
 * @version 1.0, 2011-8-4
 * @since
 */
enum UploadStatus {
	Create_Directory_Fail, // 远程服务器相应目录创建失�?
	Create_Directory_Success, // 远程服务器闯将目录成�?
	Upload_New_File_Success, // 上传新文件成�?
	Upload_New_File_Failed, // 上传新文件失�?
	File_Exits, // 文件已经存在
	Remote_Bigger_Local, // 远程文件大于本地文件
	Upload_From_Break_Success, // 断点续传成功
	Upload_From_Break_Failed, // 断点续传失败
	Delete_Remote_Faild; // 删除远程文件失败
}

class FileAttribute {
	private boolean isDirectory;
	private String filePath;

	public boolean isDirectory() {
		return isDirectory;
	}

	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	
	
	
}
