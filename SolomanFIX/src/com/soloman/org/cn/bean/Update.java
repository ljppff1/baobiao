package com.soloman.org.cn.bean;

import java.io.Serializable;
/**
 * 是否更新
 * @author Mac
 *
 */
public class Update implements Serializable
{
	//版本号
	private Double current_version;
	private String desc;
	private String min_version;
	//是否更新 true更新    false不更新
	private Boolean force_upgrade;

	public Double getCurrent_version()
	{
		return current_version;
	}

	public void setCurrent_version(Double current_version)
	{
		this.current_version = current_version;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getMin_version()
	{
		return min_version;
	}

	public void setMin_version(String min_version)
	{
		this.min_version = min_version;
	}

	public Boolean getForce_upgrade()
	{
		return force_upgrade;
	}

	public void setForce_upgrade(Boolean force_upgrade)
	{
		this.force_upgrade = force_upgrade;
	}

	public Update()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public Update(Double current_version, String desc, String min_version,
			Boolean force_upgrade)
	{
		super();
		this.current_version = current_version;
		this.desc = desc;
		this.min_version = min_version;
		this.force_upgrade = force_upgrade;
	}

}
