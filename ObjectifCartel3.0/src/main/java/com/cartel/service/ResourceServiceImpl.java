package com.cartel.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {
	
	private ResourceLoader resourceLoader;
	
	public ResourceServiceImpl(){
		
	}
	
	@Override
	public void setResourceLoader(ResourceLoader arg0) {
		this.resourceLoader=arg0;
	}
	
	public Resource getResource(String location){
		return resourceLoader.getResource(location);
	}

}
