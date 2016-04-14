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
 * <p><b>标题：</b>XmlUtil.</p>
 * <p><b>描述：Xml工具类.</b></p>
 * <p><b>版权：</b>Copyright (c) 2012 亚信联创</p>
 * <p><b>工程：</b>collector</p>
 * @author bianpx
 * @version 1.0.0
 * @since 2012-1-31 下午5:29:58
 */
@SuppressWarnings("unchecked")
public final class XmlUtil
{
	/**
	 * 日志.
	 */
	private static final Log logger = LogFactory.getLog(XmlUtil.class);

	private static OutputFormat format = OutputFormat.createPrettyPrint();

	private static OutputFormat formatUTF = OutputFormat.createPrettyPrint();
	
	static{
		formatUTF.setEncoding("UTF-8");
	}
	/**
	 * 构造函数.
	 */
	private XmlUtil()
	{
		
	}
	
	/**
	 * 抽取单个子节点.
	 * @param fElement acl节点
	 * @param nodeName 节点名称
	 * @return 子节点
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
	 * 抽取子节点集合.
	 * @param parentElement ac节点
	 * @param nodeName 节点名称
	 * @return 子节点列表
	 */
	public static List<Element> getSubNodes(Element element,String nodeName) {
		List<Element> resultElements = new ArrayList<Element>();
		//如果未空则添加 sunxin
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
	 * 获取子节点内容.
	 * @param parentElement 节点
	 * @param nodeName 子节点
	 * @return 内容
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
	 * 获取节点属性值.
	 * @param element 节点
	 * @param attr 属性名称
	 * @return 值
	 */
	public static String getNodeAttribute(Element element,String attr)
	{
		return element.attributeValue(attr);
	}

	/**
	 * 写XML文件.
	 * @param doc document
	 * @param fileName 文件名
	 * @throws Exception 异常
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
	 * 获取xml字节数组.
	 * @param doc document
	 * @return 字节数组
	 * @throws IOException 异常
	 */
	public static String getXmlBytes(Document doc) throws IOException{
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		XMLWriter xmlout = new XMLWriter(bo,format);
		xmlout.write(doc);
		return bo.toString();
	}

	/**
	 * 获取xml文件的Document对象.
	 * @param xmlFileName xml文件
	 * @return document对象
	 * @throws Exception 异常
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
			logger.error("xml文件" + xmlFileName + "解析失败:", e);
			throw new Exception("xml文件" + xmlFileName + "解析失败:" + e.getMessage());
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
	 * 为节点加上属性.
	 * @param attrName 属性名称
	 * @param value 属性值
	 */
	public static void addAttribute(Element element,String attrName,String value){
		value = replaceSpecialCharWithBlank(value);
		element.addAttribute(attrName, value);
	}

	/**
	 * 为节点设置CDATA内容.
	 * @param text 属性值
	 */
	public static void addCDATA(Element element,String text){
		text = replaceSpecialCharWithBlank(text);
		element.addCDATA(text);
	}

	/**
	 * 用空格替换xml不支持的字符.
	 * @param text 文本
	 * @return 替换过的文本
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
	 * 为节点设置内容.
	 * @param text 属性值
	 */
	public static void setText(Element element,String text){
		text = replaceSpecialCharWithBlank(text);
		element.setText(text);
	}
}
