package com.cpw.SNMP4J.pair;

import java.io.IOException;
import java.util.Vector;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
  
/** 
 * ����ˣ�����trap��Ϣ 
 * 
 */  
public class SendTrap {  
    private Snmp snmp = null;  
  
    private Address targetAddress = null;  
  
    public void initComm() throws IOException {  
  
        // ���ù����̵�IP�Ͷ˿�  
        targetAddress = GenericAddress.parse("172.28.50.0/162");  
        TransportMapping transport = new DefaultUdpTransportMapping();  
        snmp = new Snmp(transport);  
        transport.listen();  
  
    }  
  
    /** 
     * ������̷���Trap���� 
     *  
     * @throws IOException 
     */  
    public void sendPDU() throws IOException {  
  
        // ���� target  
        CommunityTarget target = new CommunityTarget();  
        target.setAddress(targetAddress);  
  
        // ͨ�Ų��ɹ�ʱ�����Դ���  
        target.setRetries(2);  
        // ��ʱʱ��  
        target.setTimeout(1500);  
        // snmp�汾  
        target.setVersion(SnmpConstants.version2c);  
  
        // ���� PDU  
        PDU pdu = new PDU();  
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.3377.10.1.1.1.1"),  
                new OctetString("SnmpTrap")));  
        pdu.add(new VariableBinding(new OID("1.3.6.1.2.3377.10.1.1.1.2"),  
                new OctetString("JavaEE")));  
        pdu.setType(PDU.TRAP);  
  
        // ��Agent����PDU��������Response  
        ResponseEvent respEvnt = snmp.send(pdu, target);  
        
//        System.out.println(respEvnt.getPeerAddress());
  
        // ����Response  
        if (respEvnt != null && respEvnt.getResponse() != null) {  
            @SuppressWarnings("unchecked")
			Vector<VariableBinding> recVBs = respEvnt.getResponse()  
                    .getVariableBindings();  
            for (int i = 0; i < recVBs.size(); i++) {  
                VariableBinding recVB = recVBs.elementAt(i);  
                System.out.println(recVB.getOid() + " : " + recVB.getVariable());  
            }  
        }  
    }  
  
    public static void main(String[] args) {  
        try {  
            SendTrap sender = new SendTrap();  
            sender.initComm();  
            sender.sendPDU();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}
