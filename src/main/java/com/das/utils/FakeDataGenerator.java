package com.das.utils;

import com.github.javafaker.Faker;

public class FakeDataGenerator {
	
	private FakeDataGenerator() {}
	
	public static String randomFirstName() {
		return new Faker().name().firstName().toUpperCase();
	}
	
	public static String randomLastName() {
		new Faker().code().toString();
		return new Faker().name().lastName().toUpperCase();
	}
	
	public static int randomNumber() {
		return new Faker().number().numberBetween(5, 100);
	}
	
	public static String randomBookingRef() {
		return new Faker().artist().name().toUpperCase();
	}
	
	public static String randomAirportName() {
		return new Faker().aviation().airport().substring(2, 4).toUpperCase();
	}
	
	public static String randomAirline() {
		return new Faker().aviation().aircraft().toUpperCase();
	}
	
	public static String randomPassport() {
		return (new Faker().aviation().airport() + new Faker().number().digits(6)).toUpperCase();
	}
	
	public static String randomNationality() {
		return new Faker().country().countryCode2().toUpperCase();
	}

}
