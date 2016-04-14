package com.cpw.shell.collect;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * <p><b>标题：</b>Expect.</p>
 * <p><b>描述：期望类</b></p>
 * <p><b>版权：</b>Copyright (c) 2012 亚信联创</p>
 * <p><b>工程：</b>collector</p>
 * @author bianpx
 * @version 1.0.0
 * @since 2012-1-31 下午5:10:57
 */
public class Expect
{
	/**
	 * 命令.
	 */
	/**
	 * 编号.
	 */
	private String code;

	/**
	 * 是否为失败期望.
	 */
	private boolean isFailExpect = false;
	
	/**
	 * 是否循环执行翻页.
	 */
	private boolean isLoop = false;
	
	/**
	 * 当执行出错时是否继续后续命令的执行.
	 */
	private boolean isContinue = false;

	/**
	 * @param isContinue the isContinue to set
	 */
	public void setContinue(boolean isContinue)
	{
		this.isContinue = isContinue;
	}

	/**
	 * 期望字符串集合.
	 */
	private List<String> expectStrs = null;

	/**
	 * 期望字符串集合.
	 * @return 期望字符串集合
	 */
	public List<String> getExpectStrs()
	{
		return expectStrs;
	}

	/**
	 * 期望字符串集合.
	 * @param expectStrs 期望字符串集合
	 */
	public void setExpectStrs(List<String> expectStrs)
	{
		this.expectStrs = expectStrs;
	}

	/**
	 * 构造函数.
	 */
	public Expect()
	{
	}

	
	/**
	 * 匹配期望信息.
	 * @param str 匹配的目标字符串
	 * @return 是否匹配
	 */
	public boolean match(String str)
	{
		for (String tmpExpStr : expectStrs)
		{
			Pattern pattern = Pattern.compile(tmpExpStr,Pattern.DOTALL);
			Matcher matcher = pattern.matcher(str);
			if(matcher.matches())
			{
				return true;
			}
		}
		return false;
	}



	/**
	 * 是否失败期望.
	 * @return 是否失败期望
	 */
	public boolean isFailExpect()
	{
		return isFailExpect;
	}

	/**
	 * 是否失败期望.
	 * @param isFailExpect 是否失败期望
	 */
	public void setFailExpect(boolean isFailExpect)
	{
		this.isFailExpect = isFailExpect;
	}

	/**
	 * 是否循环翻页.
	 * @return 是否循环翻页 
	 */
	public boolean isLoop()
	{
		return isLoop;
	}

	/**
	 * 是否循环翻页.
	 * @param isLoop 是否循环翻页
	 */
	public void setLoop(boolean isLoop)
	{
		this.isLoop = isLoop;
	}

	/**
	 * 编号.
	 * @return 编号
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * 编号.
	 * @param code 编号
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	public boolean isContinue()
	{
		return isContinue;
	}
}
