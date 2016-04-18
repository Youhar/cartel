package com.cartel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CartelEntranceOnlineLauncher {

	public static void main(String[] args){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:EntranceOnline-spring-context.xml");
	}
}
