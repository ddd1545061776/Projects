package com.ddd.domain;

import lombok.Data;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "t_user")
public class User {
       @Id
       @GeneratedValue
       private Long id;
       private  String nickname;
       private  String username;
       private  String  password;
       private  String email;
       private  String avatar;
       private  Integer type;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createtime;//创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatetime;//更新时间
       @OneToMany(mappedBy = "user")
    private List<Blog> blogs=new ArrayList<>();

}
