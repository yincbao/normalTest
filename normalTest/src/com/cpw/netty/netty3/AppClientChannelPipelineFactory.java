package com.cpw.netty.netty3;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
  public class AppClientChannelPipelineFactory implements ChannelPipelineFactory{

	  public ChannelPipeline getPipeline() throws Exception {  

	  ChannelPipeline pipeline = Channels.pipeline();  

	  //pipeline.addLast("encode", new StringEncoder());   

	 pipeline.addLast("handler", new AppStoreClientHandler());   return pipeline;  

	 }

	 }

	 