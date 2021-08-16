package com.ddd.service.impl;

import com.ddd.NotFoundException;
import com.ddd.dao.TagDao;
import com.ddd.domain.Tag;
import com.ddd.domain.Type;
import com.ddd.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDao tagDao;
    @Override
    @Transactional
    public Tag saveTag(Tag tag) {
        return tagDao.save(tag);
    }

    @Override
    @Transactional
    public Tag getTag(Long id) {
        return tagDao.getOne(id);
    }

    @Override
    @Transactional
    public Page<Tag> listPage(Pageable pageable) {
        return tagDao.findAll(pageable);
    }

    @Override
    @Transactional
    public Tag updateTag(Long id, Tag tag) {
        Tag tagOne = tagDao.findOne(id);
        if(tagOne==null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag,tagOne);
        return tagDao.save(tagOne);
    }
    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagDao.delete(id);
    }
    @Transactional
    @Override
    public Tag findByName(String name) {
        return tagDao.findByName(name);
    }

    @Override
    public List<Tag> listTag() {
        return tagDao.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        List<Long> longs = convertToList(ids);
        return tagDao.findAll(longs);
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=new PageRequest(0,size,sort);
        return tagDao.findTop(pageable);
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }
}
