package com.cpw.SNMP4J;

/** *
 * ����snmp4j ��trap�Ľ��շ���.����ֻ������v1��v2��trap.
 * trap����ԭ��:snmpʵ����ע����ʵ��CommandResponder��listener֮��,����ͨ���첽���õķ���
    * ���յ��������.
 * listen()���������߳�,���߳��еĲ����Ǽ���ָ���˿�,���յ�trap�澯֮�󽫵���
 * listener.processPdu(CommandResponderEvent event)����,��processPdu������trap��Ϣ.
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
            //snmp4jͨ��transportmapping�ļ����˿ڽ���SNMP��Ϣ,���������ʼ��һ��
                //transportmapping,
            //ע��������IP��ַ������trap�Ķ˿�.
            TransportMapping transport = new DefaultUdpTransportMapping(new UdpAddress("172.28.50.0/162"));
            //����һ��������Ϣ��snmpʵ��
            Snmp snmp = new Snmp(transport);

            //CommandResponder��һ��listener,���Դ����ȡ��trap��Ϣ
            CommandResponder trapPrinter = new CommandResponder(){
                public synchronized void processPdu(CommandResponderEvent e){
                    PDU command = e.getPDU();
                    if(command!=null){
                        //����ʾ�����trap������.�����trap�����ȹ������������.
                        System.out.println(command.toString());
                    }
                }
            };
            
            //��snmpʵ�������CommandResponder listener
            snmp.addCommandResponder(trapPrinter);
        
            System.out.println("start listening!");
            //��ʼ����trap����.listen()�����ڲ�������һ���߳�,����̼߳������͵�transport�ж���Ķ˿�
                //����Ϣ.
            transport.listen();
            System.out.println(transport.isListening());//���Լ����Ƿ�����
            
            //�ȴ�һ�β���ʱ��,�����ʱ����Է���trap��Ϣ����.
            Thread.sleep(180000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
���н������:
start listening!
true
V1TRAP[reqestID=0,timestamp=0:00:00.00,enterprise=0.0,genericTrap=2,specificTrap=0, VBS[]]
V1TRAP[reqestID=0,timestamp=0:00:00.00,enterprise=0.0,genericTrap=2,specificTrap=0, VBS[]]
TRAP[requestID=1762734632, errorStatus=Success(0), errorIndex=0, VBS[1.3.6.1.2.1.1.3.0 = 0:00:00.00; 1.3.6.1.6.3.1.1.4.1.0 = 1.3.6.1.6.3.1.1.5.3]]
TRAP[requestID=1762734633, errorStatus=Success(0), errorIndex=0, VBS[1.3.6.1.2.1.1.3.0 = 0:00:00.00; 1.3.6.1.6.3.1.1.4.1.0 = 1.3.6.1.6.3.1.1.5.3]]
TRAP[requestID=1762734634, errorStatus=Success(0), errorIndex=0, VBS[1.3.6.1.2.1.1.3.0 = 0:00:00.00; 1.3.6.1.6.3.1.1.4.1.0 = 1.3.6.1.6.3.1.1.5.3]]
TRAP[requestID=1762734635, errorStatus=Success(0), errorIndex=0, VBS[1.3.6.1.2.1.1.3.0 = 0:00:01.11; 1.3.6.1.6.3.1.1.4.1.0 = 1.3.6.1.6.3.1.1.5.3]]

������Է�����6��trap,��1,2��Ϊv1trap,��3,4,5,6��Ϊv2trap.���һ��ʱ�����иı�.
trap����������ݾ���Щ,�������ݰ���ʵ���������.
*/
