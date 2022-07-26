package com.predica.test.model.response;

import lombok.Data;

@Data
public class IpAddress {
	
	private String status;
	private String country;
	private String countryCode;
	private String region;
	private String regionName;
	private String city;
	private String zip;
	private double lat;
	private double lon;
	private String timezone;
	private String isp;
	private String org;
	private String as;
	private String query;
}
