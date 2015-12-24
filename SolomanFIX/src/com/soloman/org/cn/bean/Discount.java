package com.soloman.org.cn.bean;

import java.io.Serializable;

/**
 * 优惠券
 * 
 * @author Mac
 * 
 */
public class Discount implements Serializable
{
	private String created_at;

	private String enable;

	/**
	 * 到期时间
	 */
	private String expect_at;
	private String id;
	private String updated_at;
	private String user_id;
	private String value;

	public String getCreated_at()
	{
		return created_at;
	}

	public void setCreated_at(String created_at)
	{
		this.created_at = created_at;
	}

	public String getEnable()
	{
		return enable;
	}

	public void setEnable(String enable)
	{
		this.enable = enable;
	}

	public String getExpect_at()
	{
		return expect_at;
	}

	public void setExpect_at(String expect_at)
	{
		this.expect_at = expect_at;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUpdated_at()
	{
		return updated_at;
	}

	public void setUpdated_at(String updated_at)
	{
		this.updated_at = updated_at;
	}

	public String getUser_id()
	{
		return user_id;
	}

	public void setUser_id(String user_id)
	{
		this.user_id = user_id;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public Discount(String created_at, String enable, String expect_at,
			String id, String updated_at, String user_id, String value)
	{
		super();
		this.created_at = created_at;
		this.enable = enable;
		this.expect_at = expect_at;
		this.id = id;
		this.updated_at = updated_at;
		this.user_id = user_id;
		this.value = value;
	}

	public Discount()
	{
		super();
		// TODO Auto-generated constructor stub
	}

}
