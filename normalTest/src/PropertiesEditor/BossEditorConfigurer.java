package PropertiesEditor;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

/**
 * 本例演示org.springframework.beans.factory.config.CustomEditorConfigurer对象属性转化过程。
 * 引子：
 * 	spring大多数管理对象值得配置，在xml里面都是字符串，比如<value>123</value>,就没有好奇过，这个123是在哪里 Integer.parseInt("123")的吗，同样的问题，还有
 * SpringMVC框架，浏览器或者客户端通过网络传过来的全是String，而在Controller如参里面居然能直接定义成对象？
 * 以上工作其实是由Spring基于jdk PropertyEditorSupport扩展实现的，本例就PropertyEditorSupport使用模拟了spring CustomEditorConfigurer
 * 
 * 先看一下spring里面自定义属性编辑器的用法，XML配置：
 * <bean id= "customEditorConfigurer"    class = "org.springframework.beans.factory.config.CustomEditorConfigurer" >   
  <property name= "customEditors" >   
    <map>   
      <entry key= "com.stamen.propedit.Address" > <!-- 属性类型 -->   
        <bean  class = "com.stamen.propedit.AddressPropertyEditor" /> <!--对应Address的编辑器 -->   
      </entry>   
    </map>   
  </property>   
</bean>   
  
 <bean id= "person"   class = "com.stamen.propedit.Person" >   
    <property name= "name"  value= "Tom" />   
    <property name= "address"  value= "朝阳区,Soho 1601,010101" />   
 </bean>  
 * ClassName: BossEditorConfigurer
 * @description
 * @author yin_changbao
 * @Date   Apr 14, 2016
 *
 */
public class BossEditorConfigurer {
	
	private Map<String, String> map;
	
	private PropertyEditor propertyEditor;

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
	public void setPropertyEditor(PropertyEditor propertyEditor) {
		this.propertyEditor = propertyEditor;
	}

	public Object[] parser(Map<String, String> entity) throws ClassNotFoundException{
		
		if(entity==null||entity.isEmpty())
			return null;
		Object[] result = new Object[entity.size()];
		int idx = 0;
		for(Map.Entry<String, String> t:entity.entrySet()){
			String  value = t.getValue();
			this.propertyEditor.setAsText(value);
			result[idx] = this.propertyEditor.getValue();
			idx++;
		}
		return result;
	}
	
	
	public static void print(Object[] objArr){
		if(objArr==null)
			return;
		StringBuilder sb = new StringBuilder("[");
		for(Object ob:objArr)
			sb.append(ob.toString()).append("],");
		System.out.println(sb.toString());
	}
	
	public static void main(String args[]){
		Map<String,String> configMap = new HashMap<String,String>();
		configMap.put(BossBeanInfo.class.getName(), "Paul,30,2017-10-01 00:00:01.000,CPW corp,Nanjing Jiangsu China");
		try {
			BossEditorConfigurer bec = new BossEditorConfigurer();
			bec.setPropertyEditor(new BossPropertiesEditor());
			print(bec.parser(configMap));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
