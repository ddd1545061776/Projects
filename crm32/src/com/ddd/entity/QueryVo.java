package com.ddd.entity;

public class QueryVo {
	//客户名称
private String custName;
private String custSource;
private String custIndustry;
private String custLevel;
//当前页
private Integer currentPage;
//每页数
private Integer pageSize;

//开始行
private Integer startRow;
public Integer getStartRow() {
	return startRow;
}
public void setStartRow(Integer startRow) {
	this.startRow=startRow;
}
public Integer getCurrentPage() {
	return currentPage;
}
public void setCurrentPage(Integer currentPage) {
	this.currentPage = currentPage;
}
public Integer getPageSize() {
	return pageSize;
}
public void setPageSize(Integer pageSize) {
	this.pageSize = pageSize;
}
public String getCustName() {
	return custName;
}
public void setCustName(String custName) {
	this.custName = custName;
}
public String getCustSource() {
	return custSource;
}
public void setCustSource(String custSource) {
	this.custSource = custSource;
}
public String getCustIndustry() {
	return custIndustry;
}
public void setCustIndustry(String custIndustry) {
	this.custIndustry = custIndustry;
}
public String getCustLevel() {
	return custLevel;
}
public void setCustLevel(String custLevel) {
	this.custLevel = custLevel;
}




}
