package com.ddd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ddd.entity.BaseDict;
import com.ddd.mapper.BaseDictDao;
@Service
public class BaseDictServiceImpl implements BaseDictService{
	@Autowired
    private BaseDictDao baseDictDao;
	public List<BaseDict> selectBaseDictListBycode(String code) {
		// TODO Auto-generated method stub
		return baseDictDao.selectBaseDictListBycode(code);
	}

}
