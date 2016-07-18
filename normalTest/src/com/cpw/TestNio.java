package com.cpw;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestNio {

	public static void main(String args[]) throws Exception {
		int bufSize = 10000;
		File fin = new File("E:/Yin-Changbao/iso/CentOS-6.4-x86_64-bin-DVD1to2/CentOS-6.4-x86_64-bin-DVD1.iso");
		File fin2 = new File("E:/Yin-Changbao/iso/CentOS-6.4-x86_64-bin-DVD1to2/CentOS-6.4-x86_64-bin-DVD2.iso");
		File fout = new File("E:/Yin-Changbao/iso/CentOS-6.4-x86_64-bin-DVD1to2/CentOS-6.4-x86_64-bin-DVD.iso");
		if(!fout.exists())
			fout.createNewFile();

		FileChannel fcin = new RandomAccessFile(fin, "r").getChannel();
		ByteBuffer rBuffer = ByteBuffer.allocate(bufSize);
		

		FileChannel fcout = new RandomAccessFile(fout, "rws").getChannel();
		ByteBuffer wBuffer = ByteBuffer.allocateDirect(bufSize);

		readFileByLine(bufSize, fcin, rBuffer, fcout, wBuffer);
		System.out.println("CD1 copied done");
		FileChannel fcin2 = new RandomAccessFile(fin2, "r").getChannel();
		ByteBuffer rBuffer2 = ByteBuffer.allocate(bufSize);
		
		readFileByLine(bufSize, fcin2, rBuffer2, fcout, wBuffer);
		
		System.out.print("OK!!!");
	}

	/* 读文件同时写文件 */
	public static void readFileByLine(int bufSize, FileChannel fcin, ByteBuffer rBuffer, FileChannel fcout, ByteBuffer wBuffer) {
		try {
			byte[] bs = new byte[bufSize];

			while (fcin.read(rBuffer) != -1) {
				int rSize = rBuffer.position();
				System.out.println("copy file at position "+rSize);
				rBuffer.rewind();
				rBuffer.get(bs);
				rBuffer.clear();
				writeFileByLine(fcout, wBuffer, bs);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* 写文件 */
	public static void writeFileByLine(FileChannel fcout, ByteBuffer wBuffer, byte[] data) {
		try {
			// write on file head
			// fcout.write(wBuffer.wrap(line.getBytes()));
			// wirte append file on foot
			fcout.write(wBuffer.wrap(data), fcout.size());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
