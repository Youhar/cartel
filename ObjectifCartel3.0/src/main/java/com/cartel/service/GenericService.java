package com.cartel.service;

import java.util.List;

public interface GenericService <E,K>{
	public Integer add(E entity);
	public void update(E entity);
	public void saveOrUpdate(E entity);
	public void remove(E entity);
	public E find(K key);
	public List<E> getAll();
	
}
