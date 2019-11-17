package com.ty.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ty.entity.User;

public class UserDao {
	String url="jdbc:mysql://localhost:3306/myj_stock";
	String dbname="root";
	String dbpwd="5478";
	/**
	 * 登录
	 * @throws ClassNotFoundException 
	 */
	public User login(String username,String password) throws Exception{
		//jdbc操作
		//注册驱动
		Class.forName("com.mysql.jdbc.Driver");
		//1.连接协议jdbc:mysql://[ip]:[端口号]/数据库名称，用户名，密码
		
		//获取连接对象
		Connection con=DriverManager.getConnection(url, dbname, dbpwd);
		//定义sql语句
		String sql="select * from user where userCode=? and userPassword=? ";
		//创建执行对象
		PreparedStatement ps=con.prepareStatement(sql);
		//对占位符进行赋值操作
		ps.setString(1, username);
		ps.setString(2, password);
		User user=null;
		//执行sql executeUpdate()做增删改操作 executeQuery()查询操作
		ResultSet rs=ps.executeQuery();
		if(rs.next()) //next读取一行数据,是否有下一行数据
		{
			user=new User(); //如果读取到了数据，将user，模板进行赋值操作
			user.setAddress(rs.getString("address"));
			user.setBirthday(rs.getDate("birthday"));
			user.setCreatedBy(rs.getInt("createdBy"));
			user.setCreationDate(rs.getDate("creationDate"));
			user.setGender(rs.getInt("gender"));
			user.setId(rs.getInt("id"));
			user.setPhone(rs.getString("phone"));
			user.setUserCode(rs.getString("userCode"));
			user.setUserName(rs.getString("userName"));
			user.setUserPassword(rs.getString("userPassword"));
			user.setUserRole(rs.getInt("userRole"));
			
			String  filedUsername= rs.getString("username");
			System.out.println(filedUsername); //打印读取的名称
		}
		//释放资源
		rs.close();
		ps.close();
		con.close();
		return user;
		
	}
	
	public static void main(String[] args) throws Exception {
		UserDao userDao=new UserDao();
		User user=userDao.login("admin","1234");
		System.out.println(user);
	}

	public List<User> selectAll(String queryname,String queryUserRole)throws Exception {
		//jdbc操作
		//注册驱动
		Class.forName("com.mysql.jdbc.Driver");
		//1.连接协议jdbc:mysql://[ip]:[端口号]/数据库名称，用户名，密码
		
		//获取连接对象
		Connection con=DriverManager.getConnection(url, dbname, dbpwd);
		//定义sql语句
		String sql="select a.*,b.roleName from user a inner join role b on a.userRole=b.id where 1=1 ";
		//判断用户名不能为空,做where条件sql语句的拼接
		if(queryname!=null&&!"".equals(queryname)){
			sql=sql+" and a.userCode='"+queryname+"'";
		}
		
		//判断用户名不能为空,做where条件sql语句的拼接
		if(queryUserRole!=null&&!"".equals(queryUserRole)){
			sql=sql+" and a.userRole='"+queryUserRole+"'";
		}
		
		//创建执行对象
		PreparedStatement ps=con.prepareStatement(sql);
	
		User user=null;
		//执行sql executeUpdate()做增删改操作 executeQuery()查询操作
		ResultSet rs=ps.executeQuery();
		//存储每一次循环的user数据
		List<User> userDataList=new ArrayList<User>(); 
		while(rs.next()) //next读取一行数据,是否有下一行数据
		{
			user=new User(); //如果读取到了数据，将user，模板进行赋值操作
			user.setAddress(rs.getString("address"));
			user.setBirthday(rs.getDate("birthday"));
			user.setCreatedBy(rs.getInt("createdBy"));
			user.setCreationDate(rs.getDate("creationDate"));
			user.setGender(rs.getInt("gender"));
			user.setId(rs.getInt("id"));
			user.setPhone(rs.getString("phone"));
			user.setUserCode(rs.getString("userCode"));
			user.setUserName(rs.getString("userName"));
			user.setUserPassword(rs.getString("userPassword"));
			user.setUserRole(rs.getInt("userRole")); 
			user.setRoleName(rs.getString("roleName"));
			String  filedUsername= rs.getString("username");
			System.out.println(filedUsername); //打印读取的名称
			userDataList.add(user); //保存到集合中
		}
		//释放资源
		rs.close();
		ps.close();
		con.close();
		return userDataList;
	}

	
	public int exist(String userCode)throws Exception {
		//注册驱动
		Class.forName("com.mysql.jdbc.Driver");
		//1.连接协议jdbc:mysql://[ip]:[端口号]/数据库名称，用户名，密码
		
		//获取连接对象
		Connection con=DriverManager.getConnection(url, dbname, dbpwd);
		String sql="select count(1) as count from user where userCode='"+userCode+"'";
		PreparedStatement ps=con.prepareStatement(sql); //创建sql执行对象
		ResultSet rs=ps.executeQuery();  //执行sql语句
		int count=0;  //存储当前的读取的总行数
		if(rs.next()){  //读取一行数据
			count=rs.getInt("count"); //将返回的总行数存储到变量中
		}
		//释放资源
		rs.close();
		ps.close();
		con.close();
		return count;
	}

	//新增的方法
	public int insert(User user)throws Exception {
		//注册驱动
		Class.forName("com.mysql.jdbc.Driver");
		//1.连接协议jdbc:mysql://[ip]:[端口号]/数据库名称，用户名，密码
		String url="jdbc:mysql://localhost:3306/myj_stock";
		String dbname="root";
		String dbpwd="5478";
		//获取连接对象
		Connection con=DriverManager.getConnection(url, dbname, dbpwd);
		//定义sql语句
		String sql="INSERT INTO user (userCode, userName, userPassword, gender, birthday, phone, address, userRole, createdBy, creationDate)"+ 
				"VALUES ( ?, ?,?,?, ?, ?, ?, ?,?,?)";
		PreparedStatement ps=con.prepareStatement(sql);
		//设置参数,将占位符替换成值
		ps.setString(1, user.getUserCode());
		ps.setString(2, user.getUserName());
		ps.setString(3, user.getUserPassword());
		ps.setInt(4, user.getGender());
		ps.setObject(5, user.getBirthday());
		ps.setString(6, user.getPhone());
		ps.setString(7, user.getAddress());
		ps.setInt(8,user.getUserRole());
		ps.setInt(9, user.getCreatedBy());
		ps.setObject(10,user.getCreationDate());
		//执行sql语句
		int rowcount=ps.executeUpdate();
		ps.close();
		con.close();
		return rowcount;
	}
	
}
