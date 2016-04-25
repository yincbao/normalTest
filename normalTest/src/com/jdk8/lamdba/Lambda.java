package com.jdk8.lamdba;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * ClassName:Lambda.java
 * @description
 * @author: yin_changbao
 * @Date  : Oct 27, 2015
 *
 */
public class Lambda {
	
	public static void testList(){
		String[] atp = {"Rafael Nadal", "Novak Djokovic",  
			       "Stanislas Wawrinka",  
			       "David Ferrer","Roger Federer",  
			       "Andy Murray","Tomas Berdych",  
			       "Juan Martin Del Potro"};  
			List<String> players =  Arrays.asList(atp);  
		players.forEach((player) -> System.out.print(player + "; "));  
		players.forEach(System.out::println);  
	}
		
	
	
	public static void testOptions(){
		
		String id = UUID.randomUUID().toString();
		Optional<com.jdk8.lamdba.User> op = Optional.ofNullable(new User("admin"));
		Optional<com.jdk8.lamdba.User> op1 = op.map(new Function<User,User>(){
			@Override
			public User apply(User t) {
				if(t.id.equals("admin"))
					return new User(id+"-modified");
				return t;
			}});
		
		Optional<com.jdk8.lamdba.User> op2 = op.map(
				(User user)->{
					if(user.id.equals("admin"))
						return new User("123");
					return user;
				}
		);
		System.out.println(op.get());
		System.out.println(op1.get());
		System.out.println(op2.get());
	}
	
	
	public static void testStream(){
		Stream<String> names = Stream.of("Lamurudu", "Okanbi", "Oduduwa");
		Stream<String> names1 = Stream.of("Lamurudu", "Okanbi", "Oduduwa");
		names.filter(new Predicate<String>(){
			@Override
			public boolean test(String t) {
				if(t.startsWith("L"))
					return true;
				return false;
			}}).forEach(new Consumer<String>(){
				@Override
				public void accept(String t) {
					System.out.println(t);
					
				}});
		//names1.filter((String in)->  in.startsWith("L")).forEach(System.out::println); //会出异常stream has already been operated upon or closed
		names1.filter((String in)->  in.startsWith("L")).forEach(System.out::println);
	}
	
	public static void main(String args[]){
		testStream();
	}

}
