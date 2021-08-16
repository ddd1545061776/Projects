package com.ddd.service.impl;

import com.ddd.NotFoundException;
import com.ddd.dao.TypeDao;
import com.ddd.domain.Type;
import com.ddd.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    TypeDao typeDao;
    @Override

    public Type saveType(Type type) {
        return typeDao.save(type);
    }

    @Override
    @Transactional
    public Type getType(Long id) {
        return typeDao.findOne(id);
    }

    @Override
    @Transactional
    public Page<Type> listPage(Pageable pageable) {
        return typeDao.findAll(pageable);
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=new PageRequest(0,size,sort);
        return typeDao.findTop(pageable);
    }

    @Override
    @Transactional
    public Type updateType(Long id, Type type) {
        Type typeone = typeDao.findOne(id);
        if(typeone==null){
            throw new  NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,typeone);
        return typeDao.save(typeone);
    }

    @Override
    @Transactional
    public void deleteType(Long id) {
        typeDao.delete(id);
    }

    @Override
    public Type findByName(String name) {
        return typeDao.findByName(name);
    }

    @Override
    public List<Type> listType() {
        return typeDao.findAll();
    }
}
