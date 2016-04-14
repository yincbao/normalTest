package com.cpw.mongodb.origin;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;

/**
* 
*/
public class MongoClient {

	public static void mongoclientMethod() {
		try {
			Mongo mongo = new com.mongodb.MongoClient("10.20.71.161", 27020);
			List<String> dbs = mongo.getDatabaseNames();
			for (String dbName : dbs) {
				System.out.println(dbName);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
	public static void main(String args[]){
		mongoclientMethod(); 
	}

	class Higher{
		com.mongodb.MongoClient mongoClient = null;
		
		public void init(String host,int port){
			MongoClientOptions.Builder build = new MongoClientOptions.Builder();
			build.connectionsPerHost(50);
			// build.autoConnectRetry(true);
			build.threadsAllowedToBlockForConnectionMultiplier(50);
			build.maxWaitTime(1000 * 60 * 2);
			build.connectTimeout(1000 * 60 * 1);
			MongoClientOptions myOptions = build.build();
			// myOptions.isAutoConnectRetry();
			try {
				mongoClient = new com.mongodb.MongoClient(new ServerAddress(host, port), myOptions);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		public boolean delete(String dbName, String collectionName, String[] keys,  Object[] values){
			DB db = null; 
			DBCollection dbCollection = null;  
			if(keys!=null && values!=null){  
				if(keys.length != values.length){ 
					return false; 
				}else{  
					try {  
						db = mongoClient.getDB(dbName); //获取指定的数据库 
						dbCollection = db.getCollection(collectionName);
						BasicDBObject doc = new BasicDBObject();
						WriteResult result = null;
						String resultString = null;
						for(int i=0; i<keys.length; i++){ 
							doc.put(keys[i], values[i]);
						} 
						result = dbCollection.remove(doc);
						resultString = result.getError();
						if(null != db){ 
							try {  
								db.requestDone();
								db = null;  
							} catch (Exception e) {
								e.printStackTrace();  
							}
						} 
						return (resultString!=null) ? false : true; //根据删除执行结果进行判断后返回结果  
					} catch (Exception e) {
						e.printStackTrace();  
					} finally{  
						if(null != db){ 
							db.requestDone();
							db = null; 
						} 
					} 
				}
			}
			 return false; 
							
		}
		public ArrayList<DBObject> find(String dbName, String collectionName,
				String[] keys, Object[] values, int num) {
			ArrayList<DBObject> resultList = new ArrayList<DBObject>();	//创建返回的结果集
			DB db = null;
			DBCollection dbCollection = null;
			DBCursor cursor = null;
			if(keys!=null && values!=null){
				if(keys.length != values.length){
					return resultList;	//如果传来的查询参数对不对，直接返回空的结果集
				}else{
					try {
						db = mongoClient.getDB(dbName);	//获取数据库实例
						dbCollection = db.getCollection(collectionName);	//获取数据库中指定的collection集合
						
						BasicDBObject queryObj = new BasicDBObject();	//构建查询条件
						
						for(int i=0; i<keys.length; i++){	//填充查询条件
							queryObj.put(keys[i], values[i]);
						}				
						cursor = dbCollection.find(queryObj);	//查询获取数据
						int count = 0;
						if(num != -1){	//判断是否是返回全部数据，num=-1返回查询全部数据，num!=-1则返回指定的num数据
							while(count<num && cursor.hasNext()){
								resultList.add(cursor.next());
								count++;
							}
							return resultList;
						}else{
							while(cursor.hasNext()){
								resultList.add(cursor.next());
							}
							return resultList;
						}
					} catch (Exception e) {
						// TODO: handle exception
					} finally{				
						if(null != cursor){
							cursor.close();
						}
						if(null != db){
							db.requestDone();	//关闭数据库请求
						}
					}
				}
			}

			return resultList;
		}

		public DBCollection getCollection(String dbName, String collectionName) {
			// TODO Auto-generated method stub
			return mongoClient.getDB(dbName).getCollection(collectionName);
		}

		public DB getDb(String dbName) {
			// TODO Auto-generated method stub
			return mongoClient.getDB(dbName);
		}

		public boolean inSert(String dbName, String collectionName, String[] keys,
				Object[] values) {
			DB db = null;
			DBCollection dbCollection = null;
			WriteResult result = null;
			String resultString = null;
			if(keys!=null && values!=null){
				if(keys.length != values.length){
					return false;
				}else{
					db = mongoClient.getDB(dbName);	//获取数据库实例
					dbCollection = db.getCollection(collectionName);	//获取数据库中指定的collection集合
					BasicDBObject insertObj = new BasicDBObject();
					for(int i=0; i<keys.length; i++){	//构建添加条件
						insertObj.put(keys[i], values[i]);
					}
					
					try {
						result = dbCollection.insert(insertObj);
						resultString = result.getError();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}finally{
						if(null != db){
							db.requestDone();	//请求结束后关闭db
						}
					}				
					return (resultString != null) ? false : true;
				}
			}
			return false;
		}

		public boolean isExit(String dbName, String collectionName, String key,
				Object value) {
			// TODO Auto-generated method stub
			DB db = null;
			DBCollection dbCollection = null;
			if(key!=null && value!=null){
				try {
					db = mongoClient.getDB(dbName);	//获取数据库实例
					dbCollection = db.getCollection(collectionName);	//获取数据库中指定的collection集合
					BasicDBObject obj = new BasicDBObject();	//构建查询条件
					obj.put(key, value);
					
					if(dbCollection.count(obj) > 0) {
						return true;
					}else{
						return false;
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally{
					if(null != db){
						db.requestDone();	//关闭db
						db = null;
					}
				}
				
			}
			return false;
		}

		public boolean update(String dbName, String collectionName,
				DBObject oldValue, DBObject newValue) {
			DB db = null;
			DBCollection dbCollection = null;
			WriteResult result = null;
			String resultString = null;
			
			if(oldValue.equals(newValue)){
				return true;
			}else{
				try {
					db = mongoClient.getDB(dbName);	//获取数据库实例
					dbCollection = db.getCollection(collectionName);	//获取数据库中指定的collection集合
					
					result = dbCollection.update(oldValue, newValue);
					resultString = result.getError();
					
					return (resultString!=null) ? false : true;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally{
					if(null != db){
						db.requestDone();	//关闭db
						db = null;
					}
				}
				
			}
			
			return false;
		}
		
	}
	
	
	
}
