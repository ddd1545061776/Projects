package com.ddd.dao;

import com.ddd.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogDao extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
   /**
    * 查询推荐博客
   * @param: pageable
   */
   @Query("select  b from Blog b  where b.recommend=true ")
    List<Blog> findTop(Pageable pageable);
    /**
     * 对博客的内容合作标题模糊查询
     * @param: query
     * @param: pageable
     */
  @Query("select b from  Blog  b where b.content like  ?1 or b.title like ?1")
  Page<Blog> findByQuery(String query, Pageable pageable);
  /**
   * 更新博客浏览次数
  * @param: id
  */
  @Transactional
  @Modifying
  @Query("update  Blog b set b.views = b.views+1 where b.id =?1")
  int updateViews(Long id);
  /**
   * 查询博客所有的年份
  * @param:
  */
 @Query("select function('DATE_FORMAT',b.updatetime,'%Y') as year from  Blog b group by function('DATE_FORMAT',b.updatetime,'%Y') order by year desc")
   List<String> findGroupYear();
 /**
  * 根据对应的年份查询博客
 * @param: year
 */
 @Query("select b from Blog b where function('DATE_FORMAT',b.updatetime,'%Y')= ?1 ")
 List<Blog> findByYear(String  year);
}
