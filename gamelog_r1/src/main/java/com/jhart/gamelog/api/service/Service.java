package com.jhart.gamelog.api.service;

import java.util.List;

public interface Service<T> {
	boolean persist(List<T> o);

}
