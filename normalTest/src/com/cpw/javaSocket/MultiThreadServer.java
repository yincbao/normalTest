package com.cpw.javaSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadServer {
    private int port=8821;
    private ServerSocket serverSocket;
    private ExecutorService executorService;//�̳߳�
    private final int POOL_SIZE=10;//����CPU�̳߳ش�С
    
    public MultiThreadServer() throws IOException{
        serverSocket=new ServerSocket(port);
        //Runtime��availableProcessor()�������ص�ǰϵͳ��CPU��Ŀ.
        executorService=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SIZE);
        System.out.println("����������");
    }
    
    public void service(){
        while(true){
            Socket socket=null;
            try {
                //���տͻ�����,ֻҪ�ͻ�����������,�ͻᴥ��accept();�Ӷ�������
                socket=serverSocket.accept();
                executorService.execute(new Handler(socket));
                System.out.println(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        new MultiThreadServer().service();
    }

}

class Handler implements Runnable{
    private Socket socket;
    public Handler(Socket socket){
        this.socket=socket;
    }
    private PrintWriter getWriter(Socket socket) throws IOException{
        OutputStream socketOut=socket.getOutputStream();
        return new PrintWriter(socketOut,true);
    }
    private BufferedReader getReader(Socket socket) throws IOException{
        InputStream socketIn=socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }
    public String echo(String msg){
        return "echo:"+msg;
    }
    public void run(){
        try {
            System.out.println("New connection accepted "+socket.getInetAddress()+":"+socket.getPort());
            BufferedReader br=getReader(socket);
            PrintWriter pw=getWriter(socket);
            String msg=null;
            while((msg=br.readLine())!=null){
                System.out.println(msg);
                pw.println(echo(msg));
                if(msg.equals("bye"))
                    break;
            }
            System.out.println(Thread.currentThread().getId()+" done!");
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(socket!=null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}