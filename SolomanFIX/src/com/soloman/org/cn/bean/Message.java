package com.soloman.org.cn.bean;

import java.io.Serializable;

/**
 * 消息列表
 * 
 * @author Mac
 * 
 */
public class Message implements Serializable
{
	private int id;
	/**
	 * 创建时间
	 */
	private String created_at;
	/**
	 * 更新时间
	 */
	private String updated_at;
	/**
	 * 内容
	 */
	private String content;

	private String has_read;

	private String title;

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Message(int id, String created_at, String updated_at,
			String content, String has_read, String title)
	{
		super();
		this.id = id;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.content = content;
		this.has_read = has_read;
		this.title = title;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
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

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getHas_read()
	{
		return has_read;
	}

	public void setHas_read(String has_read)
	{
		this.has_read = has_read;
	}

	public Message(int id, String created_at, String updated_at,
			String content, String has_read)
	{
		super();
		this.id = id;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.content = content;
		this.has_read = has_read;
	}

	public Message()
	{
		super();
		// TODO Auto-generated constructor stub
	}

}
