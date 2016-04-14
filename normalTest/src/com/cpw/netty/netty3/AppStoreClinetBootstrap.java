package com.cpw.netty.netty3;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class AppStoreClinetBootstrap {
  public static void send(int clientId){
    ExecutorService bossExecutor = Executors.newCachedThreadPool();
    ExecutorService workerExecutor = Executors.newCachedThreadPool();
    ChannelFactory channelFactory = new NioClientSocketChannelFactory(bossExecutor, workerExecutor);
    ClientBootstrap bootstarp = new ClientBootstrap(channelFactory);
    bootstarp.setPipelineFactory(new AppClientChannelPipelineFactory());

    ChannelFuture future = bootstarp.connect(new InetSocketAddress("127.0.0.1", 8888));
    future.awaitUninterruptibly();
    if(future.isSuccess()){
    	for(int i=0;i<10;i++){
    		 String msg = "40407F000431303031313132353239393837000000000000001001C1F06952FDF069529C91110000000000698300000C0000000000036401014C00030001190A0D04121A1480D60488C5721800000000AF4944445F3231364730325F532056312E322E31004944445F3231364730325F482056312E322E31000000DF640D0A";
    		 msg+="Seq: "+i;
    		 msg ="clientId:"+ clientId+":"+msg;
    	      ChannelBuffer buffer = ChannelBuffers.buffer(msg.length());
    	      buffer.writeBytes(msg.getBytes());
    	      future.getChannel().write(buffer);
    	}
     
     
    }
  }
  
  
  public static void main(String args[]){
	  
	  //multi client
	  for(int i=0;i<2;i++){
		  send(i);
	  }
  }

}


