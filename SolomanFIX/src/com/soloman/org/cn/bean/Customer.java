package com.soloman.org.cn.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable
{
	/**
	 * 客户
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	// 电话
	private String customer;
	// 保镖开通城市
	private ArrayList<String> cities = new ArrayList<String>();
	private String name;
	private String desc;

	public Customer()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(int id, String customer, ArrayList<String> cities,
			String name, String desc)
	{
		super();
		this.id = id;
		this.customer = customer;
		this.cities = cities;
		this.name = name;
		this.desc = desc;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getCustomer()
	{
		return customer;
	}

	public void setCustomer(String customer)
	{
		this.customer = customer;
	}

	public ArrayList<String> getcities()
	{
		return cities;
	}

	public void setLy(ArrayList<String> cities)
	{
		this.cities = cities;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

}
