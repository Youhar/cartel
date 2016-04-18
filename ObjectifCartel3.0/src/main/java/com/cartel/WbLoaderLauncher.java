package com.cartel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WbLoaderLauncher {

	public static void main(String[] args){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:WBLoader-spring-context.xml");
	}
}
