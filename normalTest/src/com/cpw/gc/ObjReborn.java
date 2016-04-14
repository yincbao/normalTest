package com.cpw.gc;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang.time.DateFormatUtils;

public class ObjReborn {
	
	public static void main(String arg[]) throws InterruptedException{
		EntityContainer container = new EntityContainer(new Entity("reboen me",1));
		EntityContainerHolder.contianer = container;
		container = null;
		EntityContainerHolder.contianer = null;
		mkGCOccured();
		Thread.sleep(1000);//debug发现，慢点执行不报空指针，为啥gc有时耗，finalize还没执行到？？
		System.out.println(EntityContainerHolder.contianer.entity);  
		//ReferenceQueue<EntityContainer> rqueue = new ReferenceQueue<EntityContainer>();
		WeakReference<EntityContainer> weak = new WeakReference<EntityContainer>(EntityContainerHolder.contianer);
		EntityContainerHolder.contianer = null;
		container = null;
		System.out.println("before gc: "+weak.get().entity);
		Thread.sleep(1000);
		mkGCOccured();
		Thread.sleep(20000);//debug发现，慢点执行不报空指针，为啥gc有时耗，finalize还没执行到？？
		System.out.println(weak.get().entity);
		mkGCOccured();
		Thread.sleep(1000);
		//System.out.println(rqueue.poll().get());
		//这一次是不进finalize了，是不是意味着堆里面实际的new EntityContainer(new Entity("reboen me",1))对象以后都回收不掉了？
		System.out.println(EntityContainerHolder.contianer.entity);
	}
	
	public static boolean mkGCOccured() {
		Map<String, Object> wmap = new WeakHashMap<String, Object>();
		wmap.put(DateFormatUtils.format(new Date(), "HHmmssSSS"), new Object());
		while (!wmap.isEmpty()) {
			java.lang.Runtime.getRuntime().gc();
		}
		return true;
	}
}



class Entity{
	
	String name;
	
	int id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Entity(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}

	@Override
	public String toString() {
		return "Entity [name=" + name + ", id=" + id + "]";
	}
	
}

class EntityContainer{
	Entity entity;

	public EntityContainer(Entity entity) {
		super();
		this.entity = entity;
	}
	
	@Override
	public void finalize() {
		System.out.println("finalizable");
		EntityContainerHolder.contianer = this;
	}
}

class EntityContainerHolder{
	static EntityContainer contianer;
}
