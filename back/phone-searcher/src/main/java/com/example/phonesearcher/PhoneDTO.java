package com.example.phonesearcher;

public class PhoneDTO {
	
	String url, title, image, brand, model, warranty, color, intMemory, extMemory, ram, os, osVersion, processor, procVelocity, screenTech, screenRes, backCamNum, backCamRes, frontCamNum, frontCamRes, vidRecord, fingerprint;
	Integer battery;
	float price, screenSize;
	int more;
	public PhoneDTO() {
		
	}
	
	
	public PhoneDTO(String brand) {
		
		this.brand = brand;
	}
	
	public int getMore() {
		return more;
	}

	public void setMore(int more) {
		this.more = more;
	}
	
	public String getBrand() {
		
		return this.brand;
	}
	
	
	public void setBrand(String brand) {
		
		this.brand = brand;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getWarranty() {
		return warranty;
	}

	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getIntMemory() {
		return intMemory;
	}

	public void setIntMemory(String intMemory) {
		this.intMemory = intMemory;
	}

	public String getExtMemory() {
		return extMemory;
	}

	public void setExtMemory(String extMemory) {
		this.extMemory = extMemory;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public Integer getBattery() {
		return battery;
	}

	public void setBattery(Integer battery) {
		this.battery = battery;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getProcVelocity() {
		return procVelocity;
	}

	public void setProcVelocity(String procVelocity) {
		this.procVelocity = procVelocity;
	}

	public float getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(float screenSize) {
		this.screenSize = screenSize;
	}

	public String getScreenTech() {
		return screenTech;
	}

	public void setScreenTech(String screeTech) {
		this.screenTech = screeTech;
	}

	public String getScreenRes() {
		return screenRes;
	}

	public void setScreenRes(String screenRes) {
		this.screenRes = screenRes;
	}

	public String getBackCamNum() {
		return backCamNum;
	}

	public void setBackCamNum(String backCamNum) {
		this.backCamNum = backCamNum;
	}

	public String getBackCamRes() {
		return backCamRes;
	}

	public void setBackCamRes(String backCamRes) {
		this.backCamRes = backCamRes;
	}

	public String getFrontCamNum() {
		return frontCamNum;
	}

	public void setFrontCamNum(String frontCamNum) {
		this.frontCamNum = frontCamNum;
	}

	public String getFrontCamRes() {
		return frontCamRes;
	}

	public void setFrontCamRes(String frontCamRes) {
		this.frontCamRes = frontCamRes;
	}

	public String getVidRecord() {
		return vidRecord;
	}

	public void setVidRecord(String vidRecord) {
		this.vidRecord = vidRecord;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	

}
