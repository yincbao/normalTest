package com.cpw.colloectionTest;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class TreeSetAndMap {
	
	static Set<Entity> set = null;
	static Map<String,Entity> map = new TreeMap<String,Entity>();
	
	/**
	 * ����TreeSet Ԫ��˳���Լ�Ԫ��ʶ��ʽ���Ƿ���Ϊ��ͬһ����ȥ�أ� ��������hashCode�Ƿ���أ�
	 * ���ۣ�û��ֱ�ӹ���
	 * 1.��ʹhashcode���ص��Ǹ����������ж��󶼱���ӽ�treeset�ˣ�
	 * 2.Ԫ��֮���Ƿ�ȥ�ظ���ȡ���ڣ���������compareTo����������0����Ϊʹ��һ������ȥ�ظ��Ԫ��˳��Ҳ�ǰ�compareTo�������ģ�Ĭ��������
	 * 3.������������ʵ����comparable�ӿڵ�
	 */
	public static void testTreeSet(){
		set = new TreeSet<Entity>();//���entity����ʵ����comparable�ӿ�
		for(char c='a';c<'z';c++){//ѭ�����ӻ����Կ���byte��short�����Զ�ת������unicode���Ӧ��ʮ�������֣�+1���������ô����
			set.add(new Entity((byte)c,""+c));
		}
		for(Entity e:set){
			System.out.println(e.getName()+" and hashCode is: "+e.hashCode());
		}
//		for(char c='a'; c<'z';c++){
//			System.out.println(c);
//		}
	}
	/**
	 * ����TreeSet Ԫ��˳���Լ�Ԫ��ʶ��ʽ���Ƿ���Ϊ��ͬһ����ȥ�أ� ��������hashCode�Ƿ���أ�
	 * ���ۣ�ֱ�����
	 * 1. hashSet������equals()����Ϊtrue��2��������֣�ȥ�ظ����
	 * 2. hashSet�ж��Ƿ��أ�ֻ��equals������
	 * 3. java��Ϊhashcodeһ��һ����ͬһ�����󣬲�һ��϶�����һ������֮��дhashcode����������дequals����
	 * 4. ����javaĬ�ϵ�equals������һ��if(this.hashCode()!=obj.hashCode()){return false}��������Щ�����̫���Ͽ���������Ҫ��дhashcode����֤��Щ��֤��ͨ��
	 * 
	 */
	public static void testHashSet(){
		set = new HashSet<Entity>();
		for(char c='a';c<'z';c++){
			set.add(new Entity((byte)c,""+c));
		}
		for(Entity e:set){
			System.out.println(e.getName()+" and hashCode is: "+e.hashCode());
		}
	}
	
	
	
	public static void main(String[] args) {
		//testTreeSet();
		testHashSet();
	}
	
}
