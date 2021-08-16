package com.ddd.service;

import com.ddd.domain.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    /**
     * 新增
    * @param: type
    */
    Type saveType(Type type);
    /**
     * 根据id查询
    * @param: id
    */
    Type getType(Long id);
    /**
     *  分页查询
    * @param: pageable
    */
    Page<Type> listPage(Pageable pageable);
    /**
     * 根据size查询条数
    * @param: size
    */
    List<Type> listTypeTop(Integer size);
    /**
     * 更新
    * @param: id
     * @param: type
    */
    Type updateType(Long id,Type type);
    /**
     *删除
    * @param: id
    */
    void deleteType(Long id);
    Type findByName(String name);
    List<Type> listType();
}
