package com.amirmohammed.hti2021androidone.models.login;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("image")
	private Object image;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("name")
	private String name;

	@SerializedName("address_id")
	private Object addressId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("email_verified_at")
	private Object emailVerifiedAt;

	@SerializedName("id")
	private int id;

	@SerializedName("accessToken")
	private String accessToken;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private String status;

	public void setImage(Object image){
		this.image = image;
	}

	public Object getImage(){
		return image;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setAddressId(Object addressId){
		this.addressId = addressId;
	}

	public Object getAddressId(){
		return addressId;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setEmailVerifiedAt(Object emailVerifiedAt){
		this.emailVerifiedAt = emailVerifiedAt;
	}

	public Object getEmailVerifiedAt(){
		return emailVerifiedAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setAccessToken(String accessToken){
		this.accessToken = accessToken;
	}

	public String getAccessToken(){
		return accessToken;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"image = '" + image + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",name = '" + name + '\'' + 
			",address_id = '" + addressId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",email_verified_at = '" + emailVerifiedAt + '\'' + 
			",id = '" + id + '\'' + 
			",accessToken = '" + accessToken + '\'' + 
			",email = '" + email + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}