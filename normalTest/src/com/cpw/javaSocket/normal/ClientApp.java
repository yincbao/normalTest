package com.cpw.javaSocket.normal;

import java.io.DataInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientApp {
	public static void main(String args[]) {

		try {
			// ����ͨѶ���Һ�����Rock����
			Socket cSocket = new Socket("10.20.71.150", 2181);
			System.out.println("connection established");
			// �����Socket������/�����
			OutputStream os = cSocket.getOutputStream();
			DataInputStream is = new DataInputStream(cSocket.getInputStream());
			// InputStreamReader isr = new
			// InputStreamReader(cSocket.getInputStream());
			// char[] buffer = new char[1024 * 6];
			// int len = 0;
			// while ((len = isr.read(buffer)) > 0) {
			//
			// }

			int c;
			boolean flag = true;

			String responseline;

			while (flag) {
				// �ӱ�׼������������ַ���д��ϵͳ
				while ((c = System.in.read()) != -1) {
					os.write((byte) c);
					if (c == '\n') {
						os.flush();
						// ����������ֱ���ش���Ϣ���յ��������ڱ�׼�������ʾ����
						responseline = is.readLine();
						System.out.println("Message is:" + responseline);
					}
				}
			}
			os.close();
			is.close();
			cSocket.close();

		} catch (Exception e) {
			System.out.println("Exception :" + e.getMessage());
		}
	}
}
