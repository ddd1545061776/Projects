package com.ddd.domain;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@DynamicUpdate
@Data
@Entity
@Table(name = "t_blog")
public class Blog {
    @Id
    @GeneratedValue
    private   Long id ;//id
    private  String  title;//标题
    private  String content;//内容
    private     String firstPicture;//首页
    private  String flag;//标记
    @Column(updatable = false)
    private  Integer views;//浏览次数
    private boolean appreciation;//赞赏是否开启
    private boolean share_statement;//转载声明是否开启te
    private  boolean commentabled;//评论
    private  boolean published;//发布
    private  boolean recommend;//推荐
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createtime;//创建时间
    @Temporal(TemporalType.TIMESTAMP)
        private Date updatetime;//更新时间
    @ManyToOne
     private Type type;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags=new ArrayList<>();
    @ManyToOne
    private  User user;
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments=new ArrayList<>();
    @Transient
private  String tagIds;
    private String description;
    public void init() {
        this.tagIds = tagsToIds(this.getTags());
    }
    //1,2,3
    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }
}
