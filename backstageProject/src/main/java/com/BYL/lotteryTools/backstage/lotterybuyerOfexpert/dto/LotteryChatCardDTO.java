package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.dto;


public class LotteryChatCardDTO {

	private String id;
	
	
	private String name;//卡名
	
	private Double price;//售价
	
	private String introduce;//介绍描述
	
	private Double discount;
	
	private Integer count;//当前用户的剩余当前卡片的个数
	
	

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	
}
