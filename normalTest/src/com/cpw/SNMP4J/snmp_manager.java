package com.cpw.SNMP4J;

import java.io.IOException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.UserTarget;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.PrivDES;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class snmp_manager {
	private Snmp snmp = null;
	private String version = null;

	/**
	 * 
	 * @param version
	 */
	public snmp_manager(String version) {
		try {
			this.version = version;
			TransportMapping transport = new DefaultUdpTransportMapping();
			snmp = new Snmp(transport);
			if (version.equals("3")) {
				// ���ð�ȫģʽ
				USM usm = new USM(SecurityProtocols.getInstance(),
						new OctetString(MPv3.createLocalEngineID()), 0);
				SecurityModels.getInstance().addSecurityModel(usm);
			}
			// ��ʼ������Ϣ
			transport.listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param syn
	 *            �Ƿ���ͬ��ģʽ
	 * @param bro
	 *            �Ƿ��ǹ㲥
	 * @param pdu
	 *            Ҫ���͵ı���
	 * @param addr
	 *            Ŀ���ַ
	 * @throws IOException
	 */
	public void sendMessage(Boolean syn, final Boolean bro, PDU pdu, String addr)
			throws IOException {
		// ���Ŀ���ַ����
		Address targetAddress = GenericAddress.parse(addr);
		Target target = null;
		if (version.equals("3")) {
			// ����û�
			snmp.getUSM().addUser(
					new OctetString("MD5DES"),
					new UsmUser(new OctetString("MD5DES"), AuthMD5.ID,
							new OctetString("MD5DESUserAuthPassword"),
							PrivDES.ID, new OctetString(
									"MD5DESUserPrivPassword")));

			target = new UserTarget();
			// ���ð�ȫ����
			((UserTarget) target).setSecurityLevel(SecurityLevel.AUTH_PRIV);
			((UserTarget) target).setSecurityName(new OctetString("MD5DES"));
			target.setVersion(SnmpConstants.version3);
		} else {
			target = new CommunityTarget();
			if (version.equals("1")) {
				target.setVersion(SnmpConstants.version1);
				((CommunityTarget) target).setCommunity(new OctetString(
						"public"));
			} else {
				target.setVersion(SnmpConstants.version2c);
				((CommunityTarget) target).setCommunity(new OctetString(
						"public"));
			}

		}
		// Ŀ������������
		target.setAddress(targetAddress);
		target.setRetries(5);
		target.setTimeout(1000);

		if (syn.equals(true)) {
			// ���ͱ��� ���ҽ�����Ӧ
			ResponseEvent response = snmp.send(pdu, target);
			// ������Ӧ
			System.out.println("Synchronize message from "
					+ response.getPeerAddress() + "\n request:"
					+ response.getRequest() + "\n response:"
					+ response.getResponse());
		} else {
			// ���ü������
			ResponseListener listener = new ResponseListener() {
				public void onResponse(ResponseEvent event) {
					// TODO Auto-generated method stub
					if (bro.equals(false)) {
						((Snmp) event.getSource()).cancel(event.getRequest(),
								this);
					}
					// ������Ӧ
					PDU request = event.getRequest();
					PDU response = event.getResponse();
					System.out.println("Asynchronise message from "
							+ event.getPeerAddress() + "\n request:" + request
							+ "\n response:" + response);
				}
			};
			// ���ͱ���
			snmp.send(pdu, target, null, listener);
		}
	}

	public static void main(String[] args) {
		snmp_manager manager = new snmp_manager("2c");
		// ���챨��
		PDU pdu = new PDU();
		// PDU pdu = new ScopedPDU();
		// ����Ҫ��ȡ�Ķ���ID
		OID oids = new OID("1.3.6.1.2.1.1.1.0");
		pdu.add(new VariableBinding(oids,new OctetString("ddddd")));
		// ���ñ�������
		pdu.setType(PDU.GETNEXT);
		// ((ScopedPDU) pdu).setContextName(new OctetString("priv"));
		try {
			// ������Ϣ �������һ������Ҫ���͵�Ŀ���ַ
			manager.sendMessage(true, true, pdu, "172.28.50.0/161");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
