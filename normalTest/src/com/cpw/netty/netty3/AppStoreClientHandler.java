package com.cpw.netty.netty3;

import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class AppStoreClientHandler extends SimpleChannelUpstreamHandler {  


	 @Override

	  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)    throws Exception {
		 ChannelBuffer acceptBuff = (ChannelBuffer) e.getMessage();  
		 String info = acceptBuff.toString(Charset.defaultCharset());  
		 System.out.println("getting reponse: "+info);
	  }

	  @Override  

	 public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)    throws Exception {   

	 // TODO Auto-generated method stub   super.exceptionCaught(ctx, e);

	  }

	 }