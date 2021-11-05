package br.aym.base.util;

import java.io.Serializable;

import org.springframework.data.domain.Page;




public interface CrudService<T, ID> extends Serializable {

	T save(T entities);
	void delete(ID id);
	T update(T entities);
	T getById(ID id);
	Page<T> buscarAllPagination(int page, int size);
}
