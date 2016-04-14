package com.cpw.shell.collect;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * <p><b>���⣺</b>XmlUtil.</p>
 * <p><b>������Xml������.</b></p>
 * <p><b>��Ȩ��</b>Copyright (c) 2012 ��������</p>
 * <p><b>���̣�</b>collector</p>
 * @author bianpx
 * @version 1.0.0
 * @since 2012-1-31 ����5:29:58
 */
@SuppressWarnings("unchecked")
public final class XmlUtil
{
	/**
	 * ��־.
	 */
	private static final Log logger = LogFactory.getLog(XmlUtil.class);

	private static OutputFormat format = OutputFormat.createPrettyPrint();

	private static OutputFormat formatUTF = OutputFormat.createPrettyPrint();
	
	static{
		formatUTF.setEncoding("UTF-8");
	}
	/**
	 * ���캯��.
	 */
	private XmlUtil()
	{
		
	}
	
	/**
	 * ��ȡ�����ӽڵ�.
	 * @param fElement acl�ڵ�
	 * @param nodeName �ڵ�����
	 * @return �ӽڵ�
	 */
	public static Element getSubNode(Element fElement,String nodeName)
	{
		Element resultElement = null;
		
		for (Iterator<Element> iterator = fElement.elementIterator(); iterator.hasNext();)
		{
			Element element = (Element) iterator.next();
			if(nodeName.equals(element.getName()))
			{
				resultElement = element;
			}
		}
		return resultElement;
	}
	
	/**
	 * ��ȡ�ӽڵ㼯��.
	 * @param parentElement ac�ڵ�
	 * @param nodeName �ڵ�����
	 * @return �ӽڵ��б�
	 */
	public static List<Element> getSubNodes(Element element,String nodeName) {
		List<Element> resultElements = new ArrayList<Element>();
		//���δ������� sunxin
		if (element!=null && nodeName!=null) {
			for (Iterator<Element> iterator = element.elementIterator(); iterator.hasNext();) {
				Element item = (Element) iterator.next();
				if (nodeName.equals(item.getName())) {
					resultElements.add(item);
				}
			}
		}
		return resultElements;
	}

	/**
	 * ��ȡ�ӽڵ�����.
	 * @param parentElement �ڵ�
	 * @param nodeName �ӽڵ�
	 * @return ����
	 */
	public static String getSubNodeContent(Element parentElement,String nodeName)
	{
		String returnStr = null;
		for (Iterator<Element> iterator = parentElement.elementIterator(); iterator.hasNext();)
		{
			Element element = iterator.next();
			if(nodeName.equals(element.getName()))
			{
				returnStr = element.getTextTrim();
			}
		}
		if(returnStr == null)
		{
			returnStr = "";
		}
		return returnStr;
	}

	/**
	 * ��ȡ�ڵ�����ֵ.
	 * @param element �ڵ�
	 * @param attr ��������
	 * @return ֵ
	 */
	public static String getNodeAttribute(Element element,String attr)
	{
		return element.attributeValue(attr);
	}

	/**
	 * дXML�ļ�.
	 * @param doc document
	 * @param fileName �ļ���
	 * @throws Exception �쳣
	 */
	public static void writeXml(Document doc,String fileName) throws Exception
	{
		XMLWriter xmlWriter = null;
		Writer wr = null;
		try
		{
			wr = new java.io.OutputStreamWriter(new java.io.FileOutputStream(fileName),formatUTF.getEncoding());
			xmlWriter = new XMLWriter(wr,formatUTF);
			xmlWriter.write(doc);
//			xmlWriter = new XMLWriter(new FileWriter(fileName),formatUTF);
//			doc.write(wr);
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (xmlWriter != null)
			{
				xmlWriter.close();
			}
			if (wr != null)
			{
				wr.close();
			}
		}
	}

	/**
	 * ��ȡxml�ֽ�����.
	 * @param doc document
	 * @return �ֽ�����
	 * @throws IOException �쳣
	 */
	public static String getXmlBytes(Document doc) throws IOException{
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		XMLWriter xmlout = new XMLWriter(bo,format);
		xmlout.write(doc);
		return bo.toString();
	}

	/**
	 * ��ȡxml�ļ���Document����.
	 * @param xmlFileName xml�ļ�
	 * @return document����
	 * @throws Exception �쳣
	 */
	public static Document getXmlFileDoc(String xmlFileName) throws Exception{
		Document doc = null;
		FileInputStream inputStream = null;
		try
		{
			SAXReader reader = new SAXReader();
			inputStream = new FileInputStream(xmlFileName);
			doc = reader.read(inputStream);
		}
		catch (Exception e)
		{
			logger.error("xml�ļ�" + xmlFileName + "����ʧ��:", e);
			throw new Exception("xml�ļ�" + xmlFileName + "����ʧ��:" + e.getMessage());
		}finally
		{
			if(inputStream!=null)
			{
				inputStream.close();
			}
		}
		return doc;
	}

	/**
	 * Ϊ�ڵ��������.
	 * @param attrName ��������
	 * @param value ����ֵ
	 */
	public static void addAttribute(Element element,String attrName,String value){
		value = replaceSpecialCharWithBlank(value);
		element.addAttribute(attrName, value);
	}

	/**
	 * Ϊ�ڵ�����CDATA����.
	 * @param text ����ֵ
	 */
	public static void addCDATA(Element element,String text){
		text = replaceSpecialCharWithBlank(text);
		element.addCDATA(text);
	}

	/**
	 * �ÿո��滻xml��֧�ֵ��ַ�.
	 * @param text �ı�
	 * @return �滻�����ı�
	 */
	public static String replaceSpecialCharWithBlank(String text){
		if(text == null || text.length() == 0)
		{
			return "";
		}
		char ch = 0;
		StringBuffer sb = new StringBuffer(text);
		
		for (int i = 0; i < sb.length(); i++)
		{
			ch = sb.charAt(i);
			if((ch >= 0x00 && ch <= 0x08) || (ch >= 0x0b && ch <= 0x0c) || (ch >= 0x0e && ch <= 0x1f)){
				sb.replace(i, i + 1, " ");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Ϊ�ڵ���������.
	 * @param text ����ֵ
	 */
	public static void setText(Element element,String text){
		text = replaceSpecialCharWithBlank(text);
		element.setText(text);
	}
}
