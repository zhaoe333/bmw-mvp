package com.cm.common.mybatis;

public interface SqlMapper<T> {
	
	void create(T t)throws Exception;
	
	void update(T t)throws Exception;

}
