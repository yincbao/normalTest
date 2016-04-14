package com.cpw.concurrent.future;
import java.util.Calendar;
/**
 *
Title: LoonFramework

 *
Description:

 *
Copyright: Copyright (c) 2007

 *
Company: LoonFramework

 * @author chenpeng 
 * @email：ceponline@yahoo.com.cn 
 * @version 0.1
 */
interface DateTest{
    String getDate();
}
class DateTestImpl implements DateTest{
    
    private String _date=null;
     
    public DateTestImpl(){
        try{
            _date+=Calendar.getInstance().getTime();
            //设定五秒延迟
            Thread.sleep(5000);
        }catch(InterruptedException e){
        }
    }
    
    public String getDate() {
        return "date "+_date;
    }
}
class DateTestFactory extends FutureProxy<DateTest>{
    @Override
    protected DateTest createInstance() {
        return new DateTestImpl();
    }
    @Override
    protected Class<? extends DateTest> getInterface() {
        return DateTest.class;
    }
}
public class Test{
    public static void main(String[] args) {
    
        DateTestFactory factory = new DateTestFactory();
        DateTest[] dts = new DateTest[100];
        for(int i=0;i<dts.length;i++){
            dts[i]=factory.getProxyInstance();
        }
        //遍历执行
        for(DateTest dt : dts){
            System.out.println(dt.getDate());
        }
    }
} 