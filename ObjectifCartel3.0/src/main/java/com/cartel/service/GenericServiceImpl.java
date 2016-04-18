package com.cartel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cartel.dao.GenericDAO;

@Service
public abstract class GenericServiceImpl<E,K> implements GenericService<E,K> {

	private GenericDAO<E,K> genericDAO;
	
	public GenericServiceImpl(GenericDAO<E,K> genericDao){
		System.out.println(Thread.currentThread());
		this.genericDAO=genericDao;
	}
	
	public GenericServiceImpl(){

	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer add(E entity) {
		return genericDAO.add(entity);		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(E entity) {
		genericDAO.update(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrUpdate(E entity) {
		genericDAO.saveOrUpdate(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(E entity) {
		genericDAO.remove(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public E find(K key) {
		return genericDAO.find(key);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<E> getAll() {
		return genericDAO.getAll();
	}

	
}
