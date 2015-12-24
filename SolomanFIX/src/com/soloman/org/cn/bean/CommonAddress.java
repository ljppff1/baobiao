package com.soloman.org.cn.bean;

import java.io.Serializable;

/**
 * 常用地址
 * 
 * @author Mac
 * 
 */
public class CommonAddress implements Serializable
{
	private String name;
	private String phone;
	private int sex;// 0男1女
	private String complex;
	private String location_info;
	private int user_id;
	private int id;
	// 详细地址
	private String info;
	// 创建时间
	private String created_at;
	// 更新时间
	private String updated_at;

	private boolean is_default;

	private String city;

	public int getUser_id()
	{
		return user_id;
	}

	public void setUser_id(int user_id)
	{
		this.user_id = user_id;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}

	public String getCreated_at()
	{
		return created_at;
	}

	public void setCreated_at(String created_at)
	{
		this.created_at = created_at;
	}

	public String getUpdated_at()
	{
		return updated_at;
	}

	public void setUpdated_at(String updated_at)
	{
		this.updated_at = updated_at;
	}

	public boolean getIs_default()
	{
		return is_default;
	}

	public void setIs_default(boolean is_default)
	{
		this.is_default = is_default;
	}

	/**
	 * 城市名称
	 * 
	 * @return
	 */
	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public int getSex()
	{
		return sex;
	}

	public void setSex(int sex)
	{
		this.sex = sex;
	}

	public String getComplex()
	{
		return complex;
	}

	public void setComplex(String complex)
	{
		this.complex = complex;
	}

	public String getLocation_info()
	{
		return location_info;
	}

	public void setLocation_info(String location_info)
	{
		this.location_info = location_info;
	}

	public CommonAddress(String name, String phone, int sex, String complex,
			String location_info, int user_id, int id, String info,
			String created_at, String updated_at, boolean is_default,
			String city)
	{
		super();
		this.name = name;
		this.phone = phone;
		this.sex = sex;
		this.complex = complex;
		this.location_info = location_info;
		this.user_id = user_id;
		this.id = id;
		this.info = info;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.is_default = is_default;
		this.city = city;
	}

	public CommonAddress(int user_id, int id, String info, String created_at,
			String updated_at, boolean is_default, String city)
	{
		super();
		this.user_id = user_id;
		this.id = id;
		this.info = info;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.is_default = is_default;
		this.city = city;
	}

	public CommonAddress()
	{
		super();
		// TODO Auto-generated constructor stub
	}

}
