package com.soloman.org.cn.bean;

import java.io.Serializable;
import java.util.List;

public class Bodyguard implements Serializable
{
	private boolean is;
	private int id;
	private String phone_number;
	private int sex;
	private String city;
	private String avatar_image_key;
	private String birthday;
	private String height;
	private String weight;
	private String certificate_type;
	private String certificate_number;
	private String map_id;
	private String location;
	private String address;
	private String bank_name;
	private String bank_branch_name;
	private String bank_account;
	private String emergency_contact_person_name;
	private String emergency_contact_person_phone_number;
	private String emergency_contact_person_relationship;

	private String certificate_status;
	private String bank_status;
	private String verify_status;
	private String certificate_image_key;
	private String bank_image_key;
	private String receivable;

	private String gross_income;
	private String serviced_people_count;
	private String serviced_time;
	private String distance;
	
	/**
	 * 头像
	 */
	private String avatar_url;
	/**
	 * 距离
	 */
	private String _distance;
	/**
	 * 名称
	 */
	private String _name;
	/**
	 * 创建时间
	 */
	private String _createtime;
	/**
	 * 地址
	 */
	private String _address;
	/**
	 * 特卫级别
	 */
	private int level;

	/**
	 * id
	 */
	private int _id;
	/**
	 * 保镖id
	 */
	private int bodyguard_id;

	/**
	 * 描述
	 */
	private String description;

	private String name;
	private List<String> _image;
	private String user_id;
	/**
	 * 经纬度
	 */
	private String _location;
	/**
	 * 更新时间
	 */
	private String _updatetime;

	public String getDistance()
	{
		return distance;
	}

	public void setDistance(String distance)
	{
		this.distance = distance;
	}

	public boolean getIs()
	{
		return is;
	}

	public void setIs(boolean is)
	{
		this.is = is;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

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

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getAvatar_image_key()
	{
		return avatar_image_key;
	}

	public void setAvatar_image_key(String avatar_image_key)
	{
		this.avatar_image_key = avatar_image_key;
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

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getBank_name()
	{
		return bank_name;
	}

	public void setBank_name(String bank_name)
	{
		this.bank_name = bank_name;
	}

	public String getBank_branch_name()
	{
		return bank_branch_name;
	}

	public void setBank_branch_name(String bank_branch_name)
	{
		this.bank_branch_name = bank_branch_name;
	}

	public String getBank_account()
	{
		return bank_account;
	}

	public void setBank_account(String bank_account)
	{
		this.bank_account = bank_account;
	}

	public String getEmergency_contact_person_name()
	{
		return emergency_contact_person_name;
	}

	public void setEmergency_contact_person_name(
			String emergency_contact_person_name)
	{
		this.emergency_contact_person_name = emergency_contact_person_name;
	}

	public String getEmergency_contact_person_phone_number()
	{
		return emergency_contact_person_phone_number;
	}

	public void setEmergency_contact_person_phone_number(
			String emergency_contact_person_phone_number)
	{
		this.emergency_contact_person_phone_number = emergency_contact_person_phone_number;
	}

	public String getEmergency_contact_person_relationship()
	{
		return emergency_contact_person_relationship;
	}

	public void setEmergency_contact_person_relationship(
			String emergency_contact_person_relationship)
	{
		this.emergency_contact_person_relationship = emergency_contact_person_relationship;
	}

	public String getCertificate_status()
	{
		return certificate_status;
	}

	public void setCertificate_status(String certificate_status)
	{
		this.certificate_status = certificate_status;
	}

	public String getBank_status()
	{
		return bank_status;
	}

	public void setBank_status(String bank_status)
	{
		this.bank_status = bank_status;
	}

	public String getVerify_status()
	{
		return verify_status;
	}

	public void setVerify_status(String verify_status)
	{
		this.verify_status = verify_status;
	}

	public String getCertificate_image_key()
	{
		return certificate_image_key;
	}

	public void setCertificate_image_key(String certificate_image_key)
	{
		this.certificate_image_key = certificate_image_key;
	}

	public String getBank_image_key()
	{
		return bank_image_key;
	}

	public void setBank_image_key(String bank_image_key)
	{
		this.bank_image_key = bank_image_key;
	}

	public String getReceivable()
	{
		return receivable;
	}

	public void setReceivable(String receivable)
	{
		this.receivable = receivable;
	}

	public String getGross_income()
	{
		return gross_income;
	}

	public void setGross_income(String gross_income)
	{
		this.gross_income = gross_income;
	}

	public String getServiced_people_count()
	{
		return serviced_people_count;
	}

	public void setServiced_people_count(String serviced_people_count)
	{
		this.serviced_people_count = serviced_people_count;
	}

	public String getServiced_time()
	{
		return serviced_time;
	}

	public void setServiced_time(String serviced_time)
	{
		this.serviced_time = serviced_time;
	}

	public String getAvatar_url()
	{
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url)
	{
		this.avatar_url = avatar_url;
	}

	public String get_distance()
	{
		return _distance;
	}

	public void set_distance(String _distance)
	{
		this._distance = _distance;
	}

	public String get_name()
	{
		return _name;
	}

	public void set_name(String _name)
	{
		this._name = _name;
	}

	public String get_createtime()
	{
		return _createtime;
	}

	public void set_createtime(String _createtime)
	{
		this._createtime = _createtime;
	}

	public String get_address()
	{
		return _address;
	}

	public void set_address(String _address)
	{
		this._address = _address;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public int get_id()
	{
		return _id;
	}

	public void set_id(int _id)
	{
		this._id = _id;
	}

	public int getBodyguard_id()
	{
		return bodyguard_id;
	}

	public void setBodyguard_id(int bodyguard_id)
	{
		this.bodyguard_id = bodyguard_id;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<String> get_image()
	{
		return _image;
	}

	public void set_image(List<String> _image)
	{
		this._image = _image;
	}

	public String getUser_id()
	{
		return user_id;
	}

	public void setUser_id(String user_id)
	{
		this.user_id = user_id;
	}

	public String get_location()
	{
		return _location;
	}

	public void set_location(String _location)
	{
		this._location = _location;
	}

	public String get_updatetime()
	{
		return _updatetime;
	}

	public void set_updatetime(String _updatetime)
	{
		this._updatetime = _updatetime;
	}

	public Bodyguard(String avatar_url, String _distance, String _name,
			String _createtime, String _address, int level, int _id,
			int bodyguard_id, String description, String name,
			List<String> _image, String user_id, String _location,
			String _updatetime)
	{
		super();
		this.avatar_url = avatar_url;
		this._distance = _distance;
		this._name = _name;
		this._createtime = _createtime;
		this._address = _address;
		this.level = level;
		this._id = _id;
		this.bodyguard_id = bodyguard_id;
		this.description = description;
		this.name = name;
		this._image = _image;
		this.user_id = user_id;
		this._location = _location;
		this._updatetime = _updatetime;
	}

	public Bodyguard(int id, String phone_number, int sex, String city,
			String avatar_image_key, String birthday, String height,
			String weight, String certificate_type, String certificate_number,
			String map_id, String location, String address, String bank_name,
			String bank_branch_name, String bank_account,
			String emergency_contact_person_name,
			String emergency_contact_person_phone_number,
			String emergency_contact_person_relationship,
			String certificate_status, String bank_status,
			String verify_status, String certificate_image_key,
			String bank_image_key, String receivable, String gross_income,
			String serviced_people_count, String serviced_time,
			String avatar_url, String _distance, String _name,
			String _createtime, String _address, int level, int _id,
			int bodyguard_id, String description, String name,
			List<String> _image, String user_id, String _location,
			String _updatetime)
	{
		super();
		this.id = id;
		this.phone_number = phone_number;
		this.sex = sex;
		this.city = city;
		this.avatar_image_key = avatar_image_key;
		this.birthday = birthday;
		this.height = height;
		this.weight = weight;
		this.certificate_type = certificate_type;
		this.certificate_number = certificate_number;
		this.map_id = map_id;
		this.location = location;
		this.address = address;
		this.bank_name = bank_name;
		this.bank_branch_name = bank_branch_name;
		this.bank_account = bank_account;
		this.emergency_contact_person_name = emergency_contact_person_name;
		this.emergency_contact_person_phone_number = emergency_contact_person_phone_number;
		this.emergency_contact_person_relationship = emergency_contact_person_relationship;
		this.certificate_status = certificate_status;
		this.bank_status = bank_status;
		this.verify_status = verify_status;
		this.certificate_image_key = certificate_image_key;
		this.bank_image_key = bank_image_key;
		this.receivable = receivable;
		this.gross_income = gross_income;
		this.serviced_people_count = serviced_people_count;
		this.serviced_time = serviced_time;
		this.avatar_url = avatar_url;
		this._distance = _distance;
		this._name = _name;
		this._createtime = _createtime;
		this._address = _address;
		this.level = level;
		this._id = _id;
		this.bodyguard_id = bodyguard_id;
		this.description = description;
		this.name = name;
		this._image = _image;
		this.user_id = user_id;
		this._location = _location;
		this._updatetime = _updatetime;
	}

	public Bodyguard(boolean is, int id, String phone_number, int sex,
			String city, String avatar_image_key, String birthday,
			String height, String weight, String certificate_type,
			String certificate_number, String map_id, String location,
			String address, String bank_name, String bank_branch_name,
			String bank_account, String emergency_contact_person_name,
			String emergency_contact_person_phone_number,
			String emergency_contact_person_relationship,
			String certificate_status, String bank_status,
			String verify_status, String certificate_image_key,
			String bank_image_key, String receivable, String gross_income,
			String serviced_people_count, String serviced_time,
			String distance, String avatar_url, String _distance, String _name,
			String _createtime, String _address, int level, int _id,
			int bodyguard_id, String description, String name,
			List<String> _image, String user_id, String _location,
			String _updatetime)
	{
		super();
		this.is = is;
		this.id = id;
		this.phone_number = phone_number;
		this.sex = sex;
		this.city = city;
		this.avatar_image_key = avatar_image_key;
		this.birthday = birthday;
		this.height = height;
		this.weight = weight;
		this.certificate_type = certificate_type;
		this.certificate_number = certificate_number;
		this.map_id = map_id;
		this.location = location;
		this.address = address;
		this.bank_name = bank_name;
		this.bank_branch_name = bank_branch_name;
		this.bank_account = bank_account;
		this.emergency_contact_person_name = emergency_contact_person_name;
		this.emergency_contact_person_phone_number = emergency_contact_person_phone_number;
		this.emergency_contact_person_relationship = emergency_contact_person_relationship;
		this.certificate_status = certificate_status;
		this.bank_status = bank_status;
		this.verify_status = verify_status;
		this.certificate_image_key = certificate_image_key;
		this.bank_image_key = bank_image_key;
		this.receivable = receivable;
		this.gross_income = gross_income;
		this.serviced_people_count = serviced_people_count;
		this.serviced_time = serviced_time;
		this.distance = distance;
		this.avatar_url = avatar_url;
		this._distance = _distance;
		this._name = _name;
		this._createtime = _createtime;
		this._address = _address;
		this.level = level;
		this._id = _id;
		this.bodyguard_id = bodyguard_id;
		this.description = description;
		this.name = name;
		this._image = _image;
		this.user_id = user_id;
		this._location = _location;
		this._updatetime = _updatetime;
	}

	public Bodyguard()
	{
		super();
		// TODO Auto-generated constructor stub
	}

}
