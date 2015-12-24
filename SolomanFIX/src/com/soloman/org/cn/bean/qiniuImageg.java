package com.soloman.org.cn.bean;

public class qiniuImageg
{
	private String qn_key;
	private String qn_token;
	
	public String getQn_key()
	{
		return qn_key;
	}

	public void setQn_key(String qn_key)
	{
		this.qn_key = qn_key;
	}

	public String getQn_token()
	{
		return qn_token;
	}

	public void setQn_token(String qn_token)
	{
		this.qn_token = qn_token;
	}

	public qiniuImageg()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public qiniuImageg(String qn_key, String qn_token)
	{
		super();
		this.qn_key = qn_key;
		this.qn_token = qn_token;
	}
	
}
