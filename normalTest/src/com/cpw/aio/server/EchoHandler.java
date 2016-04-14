package com.cpw.aio.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

class EchoHandler implements CompletionHandler<Integer, ByteBuffer> {  
    private static Charset utf8 = Charset.forName("utf-8");  
    AsynchronousSocketChannel channel;  
    ByteBuffer buffer;  
  
    public EchoHandler(AsynchronousSocketChannel channel, ByteBuffer buffer) {  
        this.channel = channel;  
        this.buffer = buffer;  
    }  
  
    @Override  
    public void completed(Integer result, ByteBuffer buff) {  
        if (result == -1) {  
            try {  
                channel.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        } else if (result > 0) {  
            buffer.flip();  
            System.out.println(result);
            String msg = utf8.decode(buffer).toString();  
            System.out.println("echo: " + msg);  
            Future<Integer> w = channel.write(utf8.encode(msg));  
            try {  
                w.get();  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            } catch (ExecutionException e) {  
                e.printStackTrace();  
            }  
              
            buffer.clear();  
            channel.read(buff, buff, this);  
        }  
    }  
  
    @Override  
    public void failed(Throwable exc, ByteBuffer buff) {  
        // TODO Auto-generated method stub  
    }  
}  