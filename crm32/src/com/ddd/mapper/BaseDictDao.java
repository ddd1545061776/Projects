package com.ddd.mapper;

import java.util.List;

import com.ddd.entity.BaseDict;

public interface BaseDictDao  {
      public List<BaseDict> selectBaseDictListBycode(String code);
}
