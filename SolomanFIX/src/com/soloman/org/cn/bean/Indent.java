package com.soloman.org.cn.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * 订单
 * 
 * @author Mac
 * 
 */
public class Indent implements Serializable
{
	private ArrayList<String> commented_bodyguards;
	private ArrayList<Bodyguard> boy;
	private User user;
	private String address_info;
	private String address_complex;
	private String guest_sex;
	private String map_id;
	private String bodyguard_level;
	private String can_comment;
	private String can_cancel;
	private int order_status;
	private String image_qn_key;
	private String bodyguard_reward;

	private int id;
	private String city;
	private String address;
	private String guest_name;
	private String guest_phone_number;
	/**
	 * -1 已拒绝 0 待处理 1 履行中 2 履行完成 3 已放弃
	 */
	private int status;
	/**
	 * 捎一句
	 */
	private String words;
	/**
	 * 时长
	 */
	private String duration;
	/**
	 * 开始时间
	 */
	private String service_at;
	/**
	 * 创建时间
	 */
	private String created_at;
	/**
	 * 更新
	 */
	private String updated_at;
	/**
	 * 结束时间
	 */
	private String finish_at;
	private String level;
	private int price;
	/**
	 * 订单价
	 */
	private String current_price;
	/**
	 * 总终价
	 */
	private String full_price;
	/**
	 * 优惠价
	 */
	private String diff_price;
	/**
	 * 0未支付
	 */
	private int has_paid;
	private String people_count;

	public ArrayList<String> getCommented_bodyguards()
	{
		return commented_bodyguards;
	}

	public void setCommented_bodyguards(ArrayList<String> commented_bodyguards)
	{
		this.commented_bodyguards = commented_bodyguards;
	}

	public ArrayList<Bodyguard> getBoy()
	{
		return boy;
	}

	public void setBoy(ArrayList<Bodyguard> boy)
	{
		this.boy = boy;
	}

	public String getDuration()
	{
		return duration;
	}

	public void setDuration(String duration)
	{
		this.duration = duration;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getGuest_name()
	{
		return guest_name;
	}

	public void setGuest_name(String guest_name)
	{
		this.guest_name = guest_name;
	}

	public String getGuest_phone_number()
	{
		return guest_phone_number;
	}

	public void setGuest_phone_number(String guest_phone_number)
	{
		this.guest_phone_number = guest_phone_number;
	}

	public String getService_at()
	{
		return service_at;
	}

	public void setService_at(String service_at)
	{
		this.service_at = service_at;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getWords()
	{
		return words;
	}

	public void setWords(String words)
	{
		this.words = words;
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

	public String getFinish_at()
	{
		return finish_at;
	}

	public void setFinish_at(String finish_at)
	{
		this.finish_at = finish_at;
	}

	public String getLevel()
	{
		return level;
	}

	public void setLevel(String level)
	{
		this.level = level;
	}

	public int getPrice()
	{
		return price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

	public String getCurrent_price()
	{
		return current_price;
	}

	public void setCurrent_price(String current_price)
	{
		this.current_price = current_price;
	}

	public String getFull_price()
	{
		return full_price;
	}

	public void setFull_price(String full_price)
	{
		this.full_price = full_price;
	}

	public String getDiff_price()
	{
		return diff_price;
	}

	public void setDiff_price(String diff_price)
	{
		this.diff_price = diff_price;
	}

	public int getHas_paid()
	{
		return has_paid;
	}

	public void setHas_paid(int has_paid)
	{
		this.has_paid = has_paid;
	}

	public String getPeople_count()
	{
		return people_count;
	}

	public void setPeople_count(String people_count)
	{
		this.people_count = people_count;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public String getAddress_info()
	{
		return address_info;
	}

	public void setAddress_info(String address_info)
	{
		this.address_info = address_info;
	}

	public String getAddress_complex()
	{
		return address_complex;
	}

	public void setAddress_complex(String address_complex)
	{
		this.address_complex = address_complex;
	}

	public String getGuest_sex()
	{
		return guest_sex;
	}

	public void setGuest_sex(String guest_sex)
	{
		this.guest_sex = guest_sex;
	}

	public String getMap_id()
	{
		return map_id;
	}

	public void setMap_id(String map_id)
	{
		this.map_id = map_id;
	}

	public String getBodyguard_level()
	{
		return bodyguard_level;
	}

	public void setBodyguard_level(String bodyguard_level)
	{
		this.bodyguard_level = bodyguard_level;
	}

	public String getCan_comment()
	{
		return can_comment;
	}

	public void setCan_comment(String can_comment)
	{
		this.can_comment = can_comment;
	}

	public String getCan_cancel()
	{
		return can_cancel;
	}

	public void setCan_cancel(String can_cancel)
	{
		this.can_cancel = can_cancel;
	}

	public int getOrder_status()
	{
		return order_status;
	}

	public void setOrder_status(int order_status)
	{
		this.order_status = order_status;
	}

	public String getImage_qn_key()
	{
		return image_qn_key;
	}

	public void setImage_qn_key(String image_qn_key)
	{
		this.image_qn_key = image_qn_key;
	}

	public String getBodyguard_reward()
	{
		return bodyguard_reward;
	}

	public void setBodyguard_reward(String bodyguard_reward)
	{
		this.bodyguard_reward = bodyguard_reward;
	}

	public Indent(int id, String city, String address, String guest_name,
			String guest_phone_number, String service_at, int status,
			String words, String created_at, String updated_at,
			String finish_at, String level, int price, String current_price,
			String full_price, String diff_price, int has_paid,
			String people_count, String duration)
	{
		super();
		this.id = id;
		this.city = city;
		this.address = address;
		this.guest_name = guest_name;
		this.guest_phone_number = guest_phone_number;
		this.service_at = service_at;
		this.status = status;
		this.words = words;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.finish_at = finish_at;
		this.level = level;
		this.price = price;
		this.current_price = current_price;
		this.full_price = full_price;
		this.diff_price = diff_price;
		this.has_paid = has_paid;
		this.people_count = people_count;
		this.duration = duration;
	}

	public Indent(User user, String address_info, String address_complex,
			String guest_sex, String map_id, String bodyguard_level,
			String can_comment, String can_cancel, int order_status,
			String image_qn_key, String bodyguard_reward, int id, String city,
			String address, String guest_name, String guest_phone_number,
			int status, String words, String duration, String service_at,
			String created_at, String updated_at, String finish_at,
			String level, int price, String current_price, String full_price,
			String diff_price, int has_paid, String people_count)
	{
		super();
		this.user = user;
		this.address_info = address_info;
		this.address_complex = address_complex;
		this.guest_sex = guest_sex;
		this.map_id = map_id;
		this.bodyguard_level = bodyguard_level;
		this.can_comment = can_comment;
		this.can_cancel = can_cancel;
		this.order_status = order_status;
		this.image_qn_key = image_qn_key;
		this.bodyguard_reward = bodyguard_reward;
		this.id = id;
		this.city = city;
		this.address = address;
		this.guest_name = guest_name;
		this.guest_phone_number = guest_phone_number;
		this.status = status;
		this.words = words;
		this.duration = duration;
		this.service_at = service_at;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.finish_at = finish_at;
		this.level = level;
		this.price = price;
		this.current_price = current_price;
		this.full_price = full_price;
		this.diff_price = diff_price;
		this.has_paid = has_paid;
		this.people_count = people_count;
	}

	public Indent(ArrayList<Bodyguard> boy, User user, String address_info,
			String address_complex, String guest_sex, String map_id,
			String bodyguard_level, String can_comment, String can_cancel,
			int order_status, String image_qn_key, String bodyguard_reward,
			int id, String city, String address, String guest_name,
			String guest_phone_number, int status, String words,
			String duration, String service_at, String created_at,
			String updated_at, String finish_at, String level, int price,
			String current_price, String full_price, String diff_price,
			int has_paid, String people_count)
	{
		super();
		this.boy = boy;
		this.user = user;
		this.address_info = address_info;
		this.address_complex = address_complex;
		this.guest_sex = guest_sex;
		this.map_id = map_id;
		this.bodyguard_level = bodyguard_level;
		this.can_comment = can_comment;
		this.can_cancel = can_cancel;
		this.order_status = order_status;
		this.image_qn_key = image_qn_key;
		this.bodyguard_reward = bodyguard_reward;
		this.id = id;
		this.city = city;
		this.address = address;
		this.guest_name = guest_name;
		this.guest_phone_number = guest_phone_number;
		this.status = status;
		this.words = words;
		this.duration = duration;
		this.service_at = service_at;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.finish_at = finish_at;
		this.level = level;
		this.price = price;
		this.current_price = current_price;
		this.full_price = full_price;
		this.diff_price = diff_price;
		this.has_paid = has_paid;
		this.people_count = people_count;
	}

	public Indent(ArrayList<String> commented_bodyguards,
			ArrayList<Bodyguard> boy, User user, String address_info,
			String address_complex, String guest_sex, String map_id,
			String bodyguard_level, String can_comment, String can_cancel,
			int order_status, String image_qn_key, String bodyguard_reward,
			int id, String city, String address, String guest_name,
			String guest_phone_number, int status, String words,
			String duration, String service_at, String created_at,
			String updated_at, String finish_at, String level, int price,
			String current_price, String full_price, String diff_price,
			int has_paid, String people_count)
	{
		super();
		this.commented_bodyguards = commented_bodyguards;
		this.boy = boy;
		this.user = user;
		this.address_info = address_info;
		this.address_complex = address_complex;
		this.guest_sex = guest_sex;
		this.map_id = map_id;
		this.bodyguard_level = bodyguard_level;
		this.can_comment = can_comment;
		this.can_cancel = can_cancel;
		this.order_status = order_status;
		this.image_qn_key = image_qn_key;
		this.bodyguard_reward = bodyguard_reward;
		this.id = id;
		this.city = city;
		this.address = address;
		this.guest_name = guest_name;
		this.guest_phone_number = guest_phone_number;
		this.status = status;
		this.words = words;
		this.duration = duration;
		this.service_at = service_at;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.finish_at = finish_at;
		this.level = level;
		this.price = price;
		this.current_price = current_price;
		this.full_price = full_price;
		this.diff_price = diff_price;
		this.has_paid = has_paid;
		this.people_count = people_count;
	}

	public Indent()
	{
		super();
		// TODO Auto-generated constructor stub
	}

}
