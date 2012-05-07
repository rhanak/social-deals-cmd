package com.randal.model;

import java.sql.Timestamp;

public class Deal {
	private String id;
	private String description;
	private Integer original_price;
	private Integer quantity;
	private Timestamp sale_start;
	private Timestamp sale_end;
	private Integer deal_price;
	private Timestamp expiration;
	private String type_id;
	private String merchant_id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getOriginalPrice() {
		return original_price;
	}
	public void setOriginalPrice(Integer original_price) {
		this.original_price = original_price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Timestamp getSaleStart() {
		return sale_start;
	}
	public void setSaleStart(Timestamp sale_start) {
		this.sale_start = sale_start;
	}
	public Timestamp getSaleEnd() {
		return sale_end;
	}
	public void setSaleEnd(Timestamp sale_end) {
		this.sale_end = sale_end;
	}
	public Integer getDealPrice() {
		return deal_price;
	}
	public void setDealPrice(Integer deal_price) {
		this.deal_price = deal_price;
	}
	public Timestamp getExpiration() {
		return expiration;
	}
	public void setExpiration(Timestamp expiration) {
		this.expiration = expiration;
	}
	public String getTypeId() {
		return type_id;
	}
	public void setTypeId(String type_id) {
		this.type_id = type_id;
	}
	public String getMerchantId() {
		return merchant_id;
	}
	public void setMerchantId(String merchant_id) {
		this.merchant_id = merchant_id;
	}
}

