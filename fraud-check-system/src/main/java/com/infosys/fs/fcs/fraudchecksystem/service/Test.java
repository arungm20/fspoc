package com.infosys.fs.fcs.fraudchecksystem.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class Test {
	
	
	private static String properties="Mark Imaginary|Govind Real|Shakil Maybe|Chang Imagine";
	public static void main(String[] args) {
		
		String[] splits = properties.split("\\|");
		
		System.out.println("blackListedNameList :" + splits[0]);
		
		System.out.println("Time : " + getResponseTimeStamp());
	}
	

	private static String getResponseTimeStamp() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		String formatDateTime = now.format(formatter);
		return formatDateTime;
	}
	
	
}
