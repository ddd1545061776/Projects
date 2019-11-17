package com.ty.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ty.entity.Role;

/**
 * 操作角色数据表
 * @author Administrator
 *
 */
public class RoleDao {

	/**
	 * 通过角色编号获取一条角色数据
	 * @param id
	 */
	public Role getById(int id)throws Exception{
		//jdbc操作
		//注册驱动
		Class.forName("com.mysql.jdbc.Driver");
		//1.连接协议jdbc:mysql://[ip]:[端口号]/数据库名称，用户名，密码
		String url="jdbc:mysql://localhost:3306/myj_stock";
		String dbname="root";
		String dbpwd="5478";
		//获取连接对象
		Connection con=DriverManager.getConnection(url, dbname, dbpwd);
		//定义sql语句
		String sql="select * from role where id=?";
		//创建执行对象
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setInt(1, id); //赋值的操作
		//1.执行sql语句
		ResultSet rs=ps.executeQuery();
		
		//2.解析返回的结果
		Role role=null; //定义角色对象，但是未实例化
		if(rs.next()){ //读取一行数据
			role=new Role();  //将结果集的数据存储到role对象
			role.setCreatedBy(rs.getInt("createdBy"));
			role.setCreationDate(rs.getDate("creationDate"));
			role.setId(rs.getInt("id"));
			role.setRoleName(rs.getString("roleName"));
		}
		//3.释放资源
		rs.close();
		ps.close();
		con.close();
		return role;
		
		
	}
}
