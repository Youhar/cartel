package com.cartel.service;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;


public interface ResourceService extends ResourceLoaderAware {
	public Resource getResource(String location);
}
