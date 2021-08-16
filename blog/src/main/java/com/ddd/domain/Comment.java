package com.ddd.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
@Entity
@Table(name = "t_comment")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String nickname;//昵称
    private  String email;
    private   String content;
    private  String  avatar;// 头像
    @Temporal(TemporalType.TIMESTAMP)//指定在数据库中生成的数据类型
    private Date createtime;
    @ManyToOne
    private  Blog blog;
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments=new ArrayList<>();
    @ManyToOne
    private Comment parentComment;
        private boolean    adminComment;

}
