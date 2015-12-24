package com.soloman.org.cn.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 用户
 * 
 * @author Mac
 * 
 */
public class User implements Serializable
{
	
	private String phone_number;
	private int sex;
	private int level;
	private String city;
	private String description;
	private String avatar_image_key;
	private String birthday;
	private String height;
	private String weight;
	private String certificate_type;
	private String certificate_number;
	private String map_id;
	private String location;
	private boolean is_bodyguard;
	/**
	 * 用户id
	 */
	private int user_id;
	/**
	 * 订单id
	 */
	private int id;

	/**
	 * 名称
	 */
	private String name;
	/**
	 * 电话号码
	 */
	private String phone_num;
	/**
	 * 
	 */
	private String used_at;

	private String created_at;
	private String updated_at;
	private String enable;


	public String getPhone_number()
	{
		return phone_number;
	}

	public void setPhone_number(String phone_number)
	{
		this.phone_number = phone_number;
	}

	public int getSex()
	{
		return sex;
	}

	public void setSex(int sex)
	{
		this.sex = sex;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getBirthday()
	{
		return birthday;
	}

	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}

	public String getHeight()
	{
		return height;
	}

	public void setHeight(String height)
	{
		this.height = height;
	}

	public String getWeight()
	{
		return weight;
	}

	public void setWeight(String weight)
	{
		this.weight = weight;
	}

	public String getCertificate_type()
	{
		return certificate_type;
	}

	public void setCertificate_type(String certificate_type)
	{
		this.certificate_type = certificate_type;
	}

	public String getCertificate_number()
	{
		return certificate_number;
	}

	public void setCertificate_number(String certificate_number)
	{
		this.certificate_number = certificate_number;
	}

	public String getMap_id()
	{
		return map_id;
	}

	public void setMap_id(String map_id)
	{
		this.map_id = map_id;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPhone_num()
	{
		return phone_num;
	}

	public void setPhone_num(String phone_num)
	{
		this.phone_num = phone_num;
	}

	public String getUsed_at()
	{
		return used_at;
	}

	public void setUsed_at(String used_at)
	{
		this.used_at = used_at;
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

	public String getEnable()
	{
		return enable;
	}

	public void setEnable(String enable)
	{
		this.enable = enable;
	}

	public String getAvatar_image_key()
	{
		return avatar_image_key;
	}

	public void setAvatar_image_key(String avatar_image_key)
	{
		this.avatar_image_key = avatar_image_key;
	}

	public boolean isIs_bodyguard()
	{
		return is_bodyguard;
	}

	public void setIs_bodyguard(boolean is_bodyguard)
	{
		this.is_bodyguard = is_bodyguard;
	}

	public User(int user_id, int id, String name, String phone_num,
			String used_at, String created_at, String updated_at, String enable)
	{
		super();
		this.user_id = user_id;
		this.id = id;
		this.name = name;
		this.phone_num = phone_num;
		this.used_at = used_at;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.enable = enable;
	}

	public User(String phone_number, int sex, int level, String city,
			String description, String avatar_image_key, String birthday,
			String height, String weight, String certificate_type,
			String certificate_number, String map_id, String location,
			boolean is_bodyguard, int user_id, int id, String name,
			String phone_num, String used_at, String created_at,
			String updated_at, String enable)
	{
		super();
		this.phone_number = phone_number;
		this.sex = sex;
		this.level = level;
		this.city = city;
		this.description = description;
		this.avatar_image_key = avatar_image_key;
		this.birthday = birthday;
		this.height = height;
		this.weight = weight;
		this.certificate_type = certificate_type;
		this.certificate_number = certificate_number;
		this.map_id = map_id;
		this.location = location;
		this.is_bodyguard = is_bodyguard;
		this.user_id = user_id;
		this.id = id;
		this.name = name;
		this.phone_num = phone_num;
		this.used_at = used_at;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.enable = enable;
	}


	public User()
	{
		super();
		// TODO Auto-generated constructor stub
	}

}
