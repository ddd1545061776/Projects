package com.ddd.service;

import java.util.List;

import com.ddd.entity.BaseDict;

public interface BaseDictService {
	 public List<BaseDict> selectBaseDictListBycode(String code);
}
