package com.cpw.aio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

class EchoClient {  
    private static Charset utf8 = Charset.forName("utf-8");  
    private int port = 8999;  
    private String remoteHost;  
    private String[] message;  
    private AsynchronousSocketChannel channel;  
  
    public static void main(String args[]) throws Exception {  
        if (args.length >= 2) {  
            String msgs[] = new String[args.length - 1];  
            System.arraycopy(args, 1, msgs, 0, msgs.length);  
            EchoClient client = new EchoClient(args[0], msgs);  
            client.connect();  
            client.sendAndReceive();  
            Thread.sleep(3000);  
            client.close();  
            Thread.sleep(3000);  
        } else {  
            System.out.println("usage EchoClient [remotehost] [messages .... ]");  
        }  
    }  
  
    public void sendAndReceive() throws InterruptedException, ExecutionException {  
        ByteBuffer buffer = ByteBuffer.allocate(512);  
        for (String msg : this.message) {  
            Future<Integer> w = channel.write(utf8.encode(msg));  
            w.get();  
        }  
          
        channel.read(buffer, buffer, new ReceiverHandler(channel, buffer));  
    }  
  
    public void close() throws IOException {  
        channel.shutdownInput();  
        channel.shutdownOutput();  
    }  
  
    public EchoClient(String remoteHost, String[] message) {  
        super();  
        this.remoteHost = remoteHost;  
        this.message = message;  
    }  
  
    public void connect() throws IOException, InterruptedException, ExecutionException {  
        channel = AsynchronousSocketChannel.open();  
        Future<Void> r = channel.connect(new InetSocketAddress(this.remoteHost, this.port));  
        r.get();  
    }  
  
    //  getter & setters  
}  