package com.cpw.set;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
 
 
class TreeSetDemo2  
{
    public static void main(String[] args)  
    {
        Set<Student> ts = new TreeSet<Student>();
 
        ts.add(new Student("lisi0",30));
        ts.add(new Student("lisixx",25));
        ts.add(new Student("lisi9",29));
        ts.add(new Student("lisi8",38));
        ts.add(new Student("lisixx",25));
        ts.add(new Student("lisi4",14));
        ts.add(new Student("lisixx",29));
        ts.add(new Student("lisi7",27));
        
        for(Iterator<Student> ite =ts.iterator();ite.hasNext();){
        	Student s = ite.next();
        	System.out.println("hashcode :"+s.hashCode()+ "    "+s);
        }
        
 
        
    }
}
 
//同姓名同年龄的学生视为同一个学生。按照学生的年龄排序。
class Student implements Comparable
{
    private int age;
    private String name;
    Student(String name,int age)
    {
        this.age = age;
        this.name = name;
    }
 
    public int compareTo(Object obj)
    {
        
        Student stu = (Student)obj;
        
        int num = new Integer(this.age).compareTo(new Integer(stu.age));
 
        return num==0?this.name.compareTo(stu.name):num;
 
        /*
        if(this.age>stu.age)
            return 1;
        if(this.age==stu.age)
            return this.name.compareTo(stu.name);
        return -1;
        */
        /**/
    }
 
    public int getAge()
    {
        return age;
    }
    public String toString()
    {
        return name+"::"+age;
    }
    
    
    @Override
    public int hashCode(){
    	int hashcode = this.name.hashCode();
    	return hashcode;
    }
}

 

