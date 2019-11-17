package com.ddd.service;

import com.ddd.dao.impl.UserDao;
import com.ddd.dao.impl.UserDaoImpl;
import com.ddd.entiy.PageBean;
import com.ddd.entiy.User;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public List<User> findall() {
        return userDao.findall();
    }

    @Override
    public User login(User user) {
        return userDao.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public void deleteUserByid(int i) {
        userDao.deleteUserByid(i);
    }

    @Override
    public User findByIdUser(int i) {
        return userDao.findUserById(i);
    }

    @Override
    public void updateUserById(User user) {
        userDao.updateUserById(user);
    }

    @Override
    public void deleteSelectedUser(String[] uids) {
     /*   for (int i = 0; i < uids.length; i++) {
            String uid = uids[i];
            int i1 = Integer.parseInt(uid);
            userDao.deleteUserByid(i1);
        }*/
        if (uids != null && uids.length > 0) {
            for (String id : uids) {
                userDao.deleteUserByid(Integer.parseInt(id));
            }
        }
    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {
        int currentPage=Integer.parseInt(_currentPage);
        int rows=Integer.parseInt(_rows);
        if(currentPage<=0){
            currentPage=1;
        }

        //创建一个空的PageBean对象
        PageBean<User> pb=new PageBean<User>();
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        //调用userDao查询总记录数
     int  totalCount= userDao.findTotalCount(condition);
       pb.setTotalCount(totalCount);
       //调用userDao查询List集合
        //计算开始记录的索引
        int start=(currentPage-1)*rows;
      List<User> list=  userDao.findByPage(start,rows,condition);
      pb.setList(list);
      //计算总页码
        int totalPage=(totalCount%rows)==0 ?totalCount/rows:(totalCount/rows)+1;
        pb.setTotalPage(totalPage);
        return pb;
    }
}
