package com.ddd.service;

import com.ddd.domain.Tag;
import com.ddd.domain.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    /**
     * 新增
     * @param: type
     */
    Tag saveTag(Tag tag);
    /**
     * 根据id查询
     * @param: id
     */
    Tag getTag(Long id);
    /**
     *  分页查询
     * @param: pageable
     */
    Page<Tag> listPage(Pageable pageable);
    /**
     * 更新
     * @param: id
     * @param: type
     */
    Tag updateTag(Long id,Tag tag);
    /**
     *删除
     * @param: id
     */
    void deleteTag(Long id);
    Tag findByName(String name);
    List<Tag> listTag();
    List<Tag> listTag(String ids);
    List<Tag> listTagTop(Integer size);
}
