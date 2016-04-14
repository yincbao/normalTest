package com.cpw.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * reference包括
 * strongreference、softreference、weakReference、PhantomReference。引用强度一次减弱
 * ClassName: WeakRefMain
 * 
 * @description
 * @author yin_changbao
 * @Date Aug 13, 2015
 *
 */
public class RefMain {

	public static void createRefObj() {
		// strongreference

		SoftReference<RefEntity> soft = new SoftReference<RefEntity>(new RefEntity(2, "soft"));
		System.out.println("before GC");
		System.out.print("soft refernece object: ");
		printEntity(soft.get());
		WeakReference<RefEntity> weak = new WeakReference<RefEntity>(new RefEntity(3, "weak"));

		System.out.print("weak refernece object: ");
		printEntity(weak.get());

		ReferenceQueue<RefEntity> rq = new ReferenceQueue<RefEntity>();
		PhantomReference<RefEntity> phantom = new PhantomReference<RefEntity>(new RefEntity(4, "phantom"), rq);
		
		try {

			System.out.print("phantom in queue object: ");
			Reference<? extends RefEntity> bfref = rq.poll();
			printEntity(bfref.get());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		mkGCOccured();
		System.out.println("after GC");
		System.out.print("soft refernece object: ");
		printEntity(soft.get());
		System.out.print("weak refernece object: ");
		printEntity(weak.get());
		try {
			System.out.print("phantom in queue object: ");
			Reference<? extends RefEntity> aftref = rq.remove();
			RefEntity e4 = aftref.get();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	private static void printEntity(Object entity) {
		System.out.println(entity == null ? "null" : entity.toString());
	}

	public static void main(String arg[]) {
		RefMain.createRefObj();
	}

	public static void mkMiroGC() {
		new RefEntity((long) Math.random() * 1000, DateFormatUtils.format(new Date(), "HHmmssSSS")
				+ RandomStringUtils.randomNumeric(4));
	}

	public static void mkFullGC(List<RefEntity> col) {
		col.add(new RefEntity((long) Math.random() * 1000, DateFormatUtils.format(new Date(), "HHmmssSSS")
				+ RandomStringUtils.randomNumeric(4)));

	}

	public static void main2(String[] args) throws InterruptedException {
		Object obj = new Object();
		ReferenceQueue<Object> refQueue = new ReferenceQueue<Object>();
		WeakReference<Object> weakRef = new WeakReference<Object>(obj, refQueue);
		System.out.println(obj);
		System.out.println(weakRef.get());
		System.out.println(refQueue.poll());
		obj = null;
		mkGCOccured();
		System.out.println(weakRef.get());
		System.out.println(refQueue.poll());
		mkGCOccured();
		System.out.println(refQueue.poll());
	}

	public static boolean mkGCOccured() {
		Map<String, Object> wmap = new WeakHashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		wmap.put(DateFormatUtils.format(new Date(), "HHmmssSSS"), new Object());
		map.put(DateFormatUtils.format(new Date(), "HHmmssSSS"), new Object());
		while (wmap.size() == map.size()) {
			if (wmap.size() != map.size())
				return true;
			else
				java.lang.Runtime.getRuntime().gc();

		}
		return false;
	}
}
