package com.cartel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CartelPayLauncher {
	public static void main(String[] args){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:CartelPay-spring-context.xml");
	}
}