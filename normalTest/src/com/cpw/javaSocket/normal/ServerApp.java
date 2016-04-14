package com.cpw.javaSocket.normal;

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

			ServerSocket sSocket = new ServerSocket(8821);
			System.out.println("Server listen on:" + sSocket.getLocalPort());

			while (flag) {
				System.out.println("enter flag,");
				clientSocket = sSocket.accept();
				System.out.println("client "+clientSocket.getLocalAddress());
				DataInputStream is = new DataInputStream(
						new BufferedInputStream(clientSocket.getInputStream()));
				OutputStream os = clientSocket.getOutputStream();

				while ((inputLine = is.readLine()) != null) {
					// ���ͻ�������stop��ʱ�����������������ֹ��
					System.out.println("server read input stream");
					if (inputLine.equals("stop")) {
						flag = false;
						break;
					} else {
						System.out.println("you got a message from client: "+inputLine);
						System.out.println(((c = System.in.read()) != -1));
						while ((c = System.in.read()) != -1) {
							os.write((byte) c);
							if (c == '\n') {
								os.flush(); // ����Ϣ���͵��ͻ���
								System.out.println("server,os.flush");
								break;
							}
						}
						System.out.println("ssss");
					}

				}
				System.out.println(" session going done");
				is.close();
				os.close();
				clientSocket.close();

			}
			System.out.println(" server is shutting down");
			sSocket.close();
		} catch (Exception e) {
			System.out.println("Exception :" + e.getMessage());
		}
	}
}