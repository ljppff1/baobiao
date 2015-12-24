package com.soloman.org.cn.bean;

import java.io.Serializable;

/**
 * 保镖级别实体类
 * 
 * @author Mac
 * 
 */
public class BobygyardKevel implements Serializable {
	private int id;
	private String name;
	private String created_at;
	private String updated_at;
	private int index;
	private boolean enable;
	/**
	 * 小时价格
	 */
	private int price_hour;
	/**
	 * 天价格
	 */
	private int price_day;
	/**
	 * 月价格
	 */
	private int price_month;
	private String flag;
	private String update_manager_id;

	public BobygyardKevel() {
		super();
	}

	public BobygyardKevel(int id, String name, String created_at,
			String updated_at, int index, boolean enable, int price_hour,
			int price_day, int price_month, String flag,
			String update_manager_id) {
		super();
		this.id = id;
		this.name = name;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.index = index;
		this.enable = enable;
		this.price_hour = price_hour;
		this.price_day = price_day;
		this.price_month = price_month;
		this.flag = flag;
		this.update_manager_id = update_manager_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public int getPrice_hour() {
		return price_hour;
	}

	public void setPrice_hour(int price_hour) {
		this.price_hour = price_hour;
	}

	public int getPrice_day() {
		return price_day;
	}

	public void setPrice_day(int price_day) {
		this.price_day = price_day;
	}

	public int getPrice_month() {
		return price_month;
	}

	public void setPrice_month(int price_month) {
		this.price_month = price_month;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getUpdate_manager_id() {
		return update_manager_id;
	}

	public void setUpdate_manager_id(String update_manager_id) {
		this.update_manager_id = update_manager_id;
	}

}