package com.ddd.dao.impl;

import com.ddd.entiy.User;
import com.ddd.util.JDBCutil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements  UserDao {
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JDBCutil.getDataSource());
    public User findUserByUsernameAndPassword(String username, String password) {
        try {
            String sql="select * from user where username= ? and password= ?";
            User user2=   jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username,password);
            return user2;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void addUser(User user) {
        String sql="insert into user values(null,?,?,?,?,?,?,null,null)";
        jdbcTemplate.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail());
    }

    @Override
    public void deleteUserByid(int i) {
        String sql="delete from user where id=?";
        jdbcTemplate.update(sql,i);
    }

    @Override
    public User findUserById(int id) {
        String sql="select * from user where id=?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),id);
    }

    @Override
    public void updateUserById(User user) {
        String sql="update user set name=?,gender=?,age=?,address=?,qq=?,email=? where id=?";
        jdbcTemplate.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        //定义一个初始化模板sql
        String sql="select count(*) from user where 1=1 ";
        StringBuilder sb=new StringBuilder(sql);
        List<Object> params=new ArrayList<Object>();
        //遍历condition有没有值来进行sql拼接
        Set<String> keySet = condition.keySet();
        for (String key : keySet) {
            //排除非分页查询的条件参数
            if("currentPage".equals(key)|| "rows".equals(key)){
                continue;
            }
            String value = condition.get(key)[0];
            if(value!=null&&!"".equals(value)) {
                sb.append(" and " + key + " like ?");
                params.add("%"+value+"%");//加这个条件的值
            }
        }
        return jdbcTemplate.queryForObject(sb.toString(),Integer.class,params.toArray());
    }

    @Override
    public List<User> findByPage(int start, int rows,Map<String, String[]> condition) {
        String sql="select * from user where 1=1";
        StringBuilder sb=new StringBuilder(sql);
        List<Object> params=new ArrayList<Object>();
        //遍历condition有没有值来进行sql拼接
        Set<String> keySet = condition.keySet();
        for (String key : keySet) {
            //排除非分页查询的条件参数
            if("currentPage".equals(key)|| "rows".equals(key)){
                continue;
            }
            String value = condition.get(key)[0];
            if(value!=null&&!"".equals(value)) {
                sb.append(" and " + key + " like ?");
                params.add("%"+value+"%");//加这个条件的值
            }
        }
        sb.append(" limit ?,?") ;
        params.add(start);
        params.add(rows);
        return jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<User>(User.class),params.toArray());
    }

    @Override
    public List<User> findall() {
        String sql="select * from user";
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }


}

