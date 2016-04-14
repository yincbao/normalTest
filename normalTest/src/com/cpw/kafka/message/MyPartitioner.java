package com.cpw.kafka.message;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class MyPartitioner implements Partitioner {
	
	transient int hashSeed = 0;
	public MyPartitioner(VerifiableProperties props) {

	}

	/*
	 * @see kafka.producer.Partitioner#partition(java.lang.Object, int)
	 */
	@Override
	public int partition(Object key, int partitionCount) {
		System.out.println("key: "+key+" and partitionCount: "+partitionCount);
		//return Integer.valueOf((String) key) % partitionCount;
		int idx = 0;
		try{
			idx = indexFor( hash(key),partitionCount) ;
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(String.format("key [%s] allocated to partition [No.%d] ", key,idx));
		return idx;
	}
	
	final int hash(Object k) {
        int h = hashSeed;
        h ^= k.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
	
	static int indexFor(int h, int length) {
        return h & (length-1);
    }
	
}