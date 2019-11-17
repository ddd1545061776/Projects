package com.ty.entity;

import java.util.Date;

/**
 * 存储角色的信息
 * @author Administrator
 *
 */
public class Role {

	private Integer id;
	private String roleName; //角色名称
	private Integer createdBy; //创建者编号
	private Date creationDate; //创建时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
