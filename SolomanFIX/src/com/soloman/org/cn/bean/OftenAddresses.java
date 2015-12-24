package com.soloman.org.cn.bean;

import java.io.Serializable;
/**
 * 常用地址
 * @author Mac
 *
 */
public class OftenAddresses implements Serializable
{
	private int user_id;
	private int id;
	private String info;
	private String created_at;
	private String updated_at;
	private boolean is_default;
	// 城市
	private String city;

	public OftenAddresses()
	{
		super();
	}

	public OftenAddresses(int user_id, int id, String info, String created_at,
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

	public boolean isIs_default()
	{
		return is_default;
	}

	public void setIs_default(boolean is_default)
	{
		this.is_default = is_default;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

}
