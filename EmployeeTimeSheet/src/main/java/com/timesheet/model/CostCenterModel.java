package com.timesheet.model;
public class CostCenterModel {
	private int id;
	private String name;
	private String description;
	private String orderdetails;
	private int ownerId;
	private String datecreated;
	private String datemodified;
	private String ownername;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public String getDatecreated() {
		return datecreated;
	}
	public void setDatecreated(String datecreated) {
		this.datecreated = datecreated;
	}
	public String getDatemodified() {
		return datemodified;
	}
	public void setDatemodified(String datemodified) {
		this.datemodified = datemodified;
	}
	public String getOwnername() {
		return ownername;
	}
	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOrderdetails() {
		return orderdetails;
	}
	public void setOrderdetails(String orderdetails) {
		this.orderdetails = orderdetails;
	}
}





