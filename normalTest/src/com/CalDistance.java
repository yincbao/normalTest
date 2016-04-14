package com;

public class CalDistance {
	
	private  static double EARTH_RADIUS = 6378.137*1000;
	
	private static double rad(double d){
		return d * Math.PI / 180.0;
	}
	public static double getDistance(double lat1, double lng1, double lat2, double lng2){
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
		Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	
	public static double getDistanceSimple(double lat1, double lng1, double lat2, double lng2){
		return Math.sqrt(Math.pow(lat2-lat1,2)+Math.pow(lng2-lng1,2))*111*1000;
	}
	
	
	public static void main(String arg[]){
		System.out.println(getDistance(31.8667427, -116.5963713,33.178708101565,-96.70313291753)/111000);
	}
}
