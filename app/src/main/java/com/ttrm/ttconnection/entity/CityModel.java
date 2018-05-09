package com.ttrm.ttconnection.entity;

import java.util.List;

public class CityModel {
	private String name;
	private String zipcode;
	private List<DistrictModel> districtList;
	
	public CityModel() {
		super();
	}
	public CityModel(String zipcode,String name){
		super();
		this.name=name;
		this.zipcode=zipcode;
	}

	public CityModel(String name, List<DistrictModel> districtList) {
		super();
		this.name = name;
		this.districtList = districtList;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DistrictModel> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<DistrictModel> districtList) {
		this.districtList = districtList;
	}

	@Override
	public String toString() {
		return "CityModel [name=" + name + ", districtList=" + districtList
				+ "]";
	}
	
}
