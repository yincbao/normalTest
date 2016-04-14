package com.cpw.netty.netty3;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelDownstreamHandler;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;




public class Server {
  public static void main(String args[]) {
	  Server server = new Server();
    ServerBootstrap bootsrap = new ServerBootstrap(
        new NioServerSocketChannelFactory(Executors
            .newCachedThreadPool(), Executors.newCachedThreadPool()));
    bootsrap.setPipelineFactory(server.new PipelineFactoryTest());
    bootsrap.bind(new InetSocketAddress(8888));
  }

  /**
   * UpstreamHandler 添加多个以责任链模式（line61代码）按添加顺序执行。负责接收请求方 上行 数据流量，提供2个接口，messageReceived(conext, event),以及异常处理
   * DownstreamHandler 刚好相反，执行顺序也和添加进来的顺序相反
   * upstream event 分上行事件和下行事件。不能传递上行事件到下行事件 line 65,66
   * 所有往Channel里面写操作会触发 upstreamHandler事件。
   * 不要在downstreamHandler里面触发下行事件（即写操作），否则会无限循环触发downstreamhandler（line128） 
   * 在任意一个upstreamHandler的写事件都会触发downloadStream。
   * 关于ctx.sendUpstream(e)方法与Filter donextChan一样，典型责任链模式。
   * 该方法去调下一个handler执行。所以line69~71组合，看到最后才打印to next fliter，并且第一个写事件触发的downloadhandler是在后面执行的，
   * 就是因为方法栈先进新出的原则，
   * 
   * 
   * 总结来说：netty里面的AddLast添加的handler，语言上看，是典型的filter。但是逻辑上又是action了，业务逻辑就写在了handler里面
   * 但是，downStreamHandler比较特殊，在负责拦截过滤所有往外发的事件。类似于servlet的后置处理
   * ClassName: PipelineFactoryTest
   * @description
   * @author yin_changbao
   * @Date   Oct 22, 2015
   *
   */
public class PipelineFactoryTest implements ChannelPipelineFactory {

  @Override
  public ChannelPipeline getPipeline() throws Exception {
	  System.out.println("a connection accpeted");
    ChannelPipeline pipeline =  Channels.pipeline();
    pipeline.addLast("1", new UpstreamHandlerA());
   pipeline.addLast("2", new UpstreamHandlerB());
    pipeline.addLast("3", new DownstreamHandlerA());
    pipeline.addLast("4", new DownstreamHandlerB());
    pipeline.addLast("5", new UpstreamHandlerX());
    return pipeline;
  }
}

public class UpstreamHandlerA extends SimpleChannelUpstreamHandler {
  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
      throws Exception {
    Channel echannel =  e.getChannel();
    System.out.println("channle  "+echannel.hashCode());
    /*System.out.println("handler  "+this.hashCode());
    System.out.println(ctxchannel.equals(echannel));//handle和event共享一个channel
    System.out.println("Thread Name:  "+Thread.currentThread().getName());*/
    String msg = parseRequest(e);
    
  // System.out.println(" messageReceived:  " +msg +" ||from: "+echannel.getRemoteAddress() +"channle  "+echannel.hashCode()+"Thread Name:  "+Thread.currentThread().getName());
   //ctx.sendUpstream(e);
    //ctx.sendDownstream(e);
    //e.getChannel().write(e.getMessage());
    ctx.sendUpstream(e);
    e.getChannel().write(wrapResponse("push  [this is testing]"));
   // System.out.println("to next fliter，");
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
   // System.out.println("UpstreamHandlerA.exceptionCaught:" + e.toString());
    e.getChannel().close();
  }
}

public class UpstreamHandlerB extends SimpleChannelUpstreamHandler {
  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
      throws Exception {
	  
  /* System.out
        .println("UpstreamHandlerB.messageReceived：" + e.getMessage());*/
    ctx.sendUpstream(e);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
   // System.out.println("UpstreamHandlerB.exceptionCaught：" + e.toString());
    ctx.sendUpstream(e);
    e.getChannel().close();
  }
}



public class UpstreamHandlerX extends SimpleChannelUpstreamHandler {
  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
      throws Exception {
	  
  // e.getChannel().write(wrapResponse(" | "+parseRequest(e)+" from handlerX"));
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
   // System.out.println("UpstreamHandlerX.exceptionCaught");
    e.getChannel().close();
  }
}

public class DownstreamHandlerA extends SimpleChannelDownstreamHandler {
  public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent e)
      throws Exception {
    //System.out.println("DownstreamHandlerA.handleDownstream");
    super.handleDownstream(ctx, e);
  }
}

public class DownstreamHandlerB extends SimpleChannelDownstreamHandler {
  public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent e)
      throws Exception {
   // System.out.println("DownstreamHandlerB.handleDownstream:");
    super.handleDownstream(ctx, e);
    //e.getChannel().write("downstreamB add");
  }
}

public static ChannelBuffer wrapResponse(String msg){
	 ChannelBuffer buffer = ChannelBuffers.buffer(msg.length());
     buffer.writeBytes(msg.getBytes());
     return buffer;
}

public static String parseRequest(MessageEvent e){
	ChannelBuffer acceptBuff = (ChannelBuffer) e.getMessage();  
    return acceptBuff.toString(Charset.defaultCharset());  

}
}