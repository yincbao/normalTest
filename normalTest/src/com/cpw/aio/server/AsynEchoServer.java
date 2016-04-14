package com.cpw.aio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsynEchoServer {  
  
    private int port = 8999;  
  
    private int backlog = 50;  
  
    private int threadPoolSize = 20;  
  
    private int initialSize = 5;  
  
    public void start() throws IOException {  
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);  
        AsynchronousChannelGroup group = AsynchronousChannelGroup.withCachedThreadPool(executor, initialSize);  
        AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open(group);  
        listener.bind(new InetSocketAddress(port), backlog);  
      
        /*不推荐，原因 future.get是阻塞的
         * Future<AsynchronousSocketChannel> fu =  listener.accept();
        try {
			AsynchronousSocketChannel socketChannel	= fu.get();
			ByteBuffer buffer = ByteBuffer.allocate(512); 
			channel.read(buffer, buffer, new EchoHandler(channel, buffer));  
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
        
        listener.accept(listener, new CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel>() {  
  
            @Override  
            public void completed(AsynchronousSocketChannel channel, AsynchronousServerSocketChannel listener) {  
                listener.accept(listener, this);  
                ByteBuffer buffer = ByteBuffer.allocate(512);  
                channel.read(buffer, buffer, new EchoHandler(channel, buffer));  
            }  
  
            @Override  
            public void failed(Throwable exc, AsynchronousServerSocketChannel listener) {  
                exc.printStackTrace();  
                try {  
                    listener.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                } finally {  
                    System.exit(-1);  
                }  
            }  
        });  
        
    }  
  
    /** 
     * @param args 
     * @throws IOException 
     */  
    public static void main(String[] args) throws IOException {  
        AsynEchoServer server = new AsynEchoServer();  
        server.start();  
    }  
  
    // getter & setters  
} 