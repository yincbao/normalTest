package com.cpw.SNMP4J;

/** *
 * 测试snmp4j 中trap的接收方法.这里只测试了v1和v2的trap.
 * trap接收原理:snmp实例在注册了实现CommandResponder的listener之后,可以通过异步调用的方法
    * 将收到内容输出.
 * listen()启动监听线程,该线程中的操作是监听指定端口,在收到trap告警之后将调用
 * listener.processPdu(CommandResponderEvent event)方法,由processPdu来处理trap信息.
 */
import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class TrapTest {
    public static void main(String[] args){
        try {
            //snmp4j通过transportmapping的监听端口接收SNMP信息,所以这里初始化一个
                //transportmapping,
            //注明本机的IP地址及接收trap的端口.
            TransportMapping transport = new DefaultUdpTransportMapping(new UdpAddress("172.28.50.0/162"));
            //创建一个处理消息的snmp实例
            Snmp snmp = new Snmp(transport);

            //CommandResponder是一个listener,用以处理获取的trap消息
            CommandResponder trapPrinter = new CommandResponder(){
                public synchronized void processPdu(CommandResponderEvent e){
                    PDU command = e.getPDU();
                    if(command!=null){
                        //这里示例输出trap的内容.具体的trap解析等工作在这里进行.
                        System.out.println(command.toString());
                    }
                }
            };
            
            //在snmp实例中添加CommandResponder listener
            snmp.addCommandResponder(trapPrinter);
        
            System.out.println("start listening!");
            //开始启动trap监听.listen()方法内部启动了一个线程,这个线程监听发送到transport中定义的端口
                //的消息.
            transport.listen();
            System.out.println(transport.isListening());//测试监听是否正常
            
            //等待一段测试时间,在这段时间可以发送trap信息测试.
            Thread.sleep(180000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
运行结果如下:
start listening!
true
V1TRAP[reqestID=0,timestamp=0:00:00.00,enterprise=0.0,genericTrap=2,specificTrap=0, VBS[]]
V1TRAP[reqestID=0,timestamp=0:00:00.00,enterprise=0.0,genericTrap=2,specificTrap=0, VBS[]]
TRAP[requestID=1762734632, errorStatus=Success(0), errorIndex=0, VBS[1.3.6.1.2.1.1.3.0 = 0:00:00.00; 1.3.6.1.6.3.1.1.4.1.0 = 1.3.6.1.6.3.1.1.5.3]]
TRAP[requestID=1762734633, errorStatus=Success(0), errorIndex=0, VBS[1.3.6.1.2.1.1.3.0 = 0:00:00.00; 1.3.6.1.6.3.1.1.4.1.0 = 1.3.6.1.6.3.1.1.5.3]]
TRAP[requestID=1762734634, errorStatus=Success(0), errorIndex=0, VBS[1.3.6.1.2.1.1.3.0 = 0:00:00.00; 1.3.6.1.6.3.1.1.4.1.0 = 1.3.6.1.6.3.1.1.5.3]]
TRAP[requestID=1762734635, errorStatus=Success(0), errorIndex=0, VBS[1.3.6.1.2.1.1.3.0 = 0:00:01.11; 1.3.6.1.6.3.1.1.4.1.0 = 1.3.6.1.6.3.1.1.5.3]]

这里测试发送了6条trap,第1,2条为v1trap,第3,4,5,6条为v2trap.最后一条时间稍有改变.
trap的输出的内容就这些,具体内容按照实际情况而定.
*/
