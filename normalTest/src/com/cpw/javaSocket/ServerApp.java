package com.cpw.javaSocket;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
	public static void main(String args[]) {
		try {
			boolean flag = true;
			Socket clientSocket = null;
			String inputLine;
			int c;

			ServerSocket sSocket = new ServerSocket(8018);
			System.out.println("Server listen on:" + sSocket.getLocalPort());

			while (flag) {
				clientSocket = sSocket.accept();
				DataInputStream is = new DataInputStream(
						new BufferedInputStream(clientSocket.getInputStream()));
				OutputStream os = clientSocket.getOutputStream();

				while ((inputLine = is.readLine()) != null) {
					// ���ͻ�������stop��ʱ�����������������ֹ��
					if (inputLine.equals("stop")) {
						flag = false;
						break;
					} else {
						System.out.println(inputLine);

						while ((c = System.in.read()) != -1) {
							os.write((byte) c);
							if (c == '\n') {
								os.flush(); // ����Ϣ���͵��ͻ���
								break;
							}
						}
					}

				}
				is.close();
				os.close();
				clientSocket.close();

			}
			sSocket.close();
		} catch (Exception e) {
			System.out.println("Exception :" + e.getMessage());
		}
	}
}