package com.cartel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CartelMealOnlineLauncher {

	public static void main(String[] args){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:MealOnline-spring-context.xml");
	}
}
