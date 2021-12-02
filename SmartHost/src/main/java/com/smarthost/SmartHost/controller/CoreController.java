package com.smarthost.SmartHost.controller;

import java.text.DecimalFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoreController {
	
	private final double[] offers = {23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209};
	
//	private int allocatedPremium = 0;
//	private int allocatedEconomy = 0;
	
	@GetMapping("/income")
	public String getIncome(@RequestParam int premiumRooms, @RequestParam int economyRooms) {

		Arrays.sort(offers);
		
		int economyRoomCount = economyRooms;
		int premiumRoomCount = premiumRooms;
		
		
		double premiumUsage = 0;
		double economyUsage = 0;
		
//		int allocatedPremium = 0;
//		int allocatedEconomy = 0;

		int allocatedPremiumOutput = 0;
		int allocatedEconomyOutput = 0;
		
		int economyCustomers = 0;
		int premiumCustomers = 0;
		
		for(int x = 0; x < offers.length; x++) {
			if(offers[x] >= 100) {
				economyCustomers = x;
				premiumCustomers = offers.length - economyCustomers;
				break;
			}
		}
		
		boolean upgradeRequired = economyCustomers - economyRoomCount > 0 && premiumRoomCount - premiumCustomers > 0 ? true : false;
		
		
//		System.out.println( allocatePremium(offers, economyCustomers, premiumRoomCount) );
		
		double[] result1 = allocatePremium(offers, economyCustomers, premiumRoomCount);
		premiumUsage = result1[0];
		allocatedPremiumOutput =  (int)result1[1];
		
		if(upgradeRequired) {
			int emptyPremium = premiumRoomCount - premiumCustomers;
			double[] result2 =  allocateEconomy(offers, economyCustomers, emptyPremium);
			premiumUsage += result2[0];
			allocatedPremiumOutput += result2[1];
			
			double[] result3 = allocateEconomy(offers, economyCustomers - emptyPremium, economyRoomCount );
			economyUsage = result3[0];
			allocatedEconomyOutput = (int)result3[1];
		} else {
			double[] result4 = allocateEconomy(offers, economyCustomers, economyRoomCount );
			economyUsage = result4[0];
			allocatedEconomyOutput = (int)result4[1];
		}
		
		System.out.println("Line 72");
		System.out.println(premiumUsage);
		System.out.println(economyUsage);
		
//		DecimalFormat df=new DecimalFormat("#.#");
//		double d=127.70000;
//		String s=df.format(d);
//		System.out.println(s);
		
		String p  = (100 * premiumUsage) % 100 == 0 || (10 * premiumUsage) % 10 == 0  ? String.valueOf( (int) premiumUsage) : String.valueOf( premiumUsage);
		String e = (100 * economyUsage) % 100 == 0 || (10 * economyUsage) % 10 == 0 ? String.valueOf((int) economyUsage) : String.valueOf(economyUsage);
		
//		return String.format("Usage premium: %d(EUR %f)\nUsage economy: %d(EUR %f)", allocatedPremiumOutput, premiumUsage, allocatedEconomyOutput, economyUsage);
		
		System.out.println("Formatted: " + convertToString(premiumUsage));
		System.out.println("Stringify: " + p + "\t" + e);
		
		return String.format("Usage premium: %d(EUR %s)\nUsage economy: %d(EUR %s)", allocatedPremiumOutput, p, allocatedEconomyOutput, e);
	}
	
	private double[] allocatePremium(double[] sortedOffers, int boundary, int count) {
		
		double premiumIncome = 0;
		int allocatedPremium = 0;
		
		int index = sortedOffers.length - 1; 
		
		while(index >= boundary && count > 0) {
			
			premiumIncome += sortedOffers[index];
			index--;
			count--;
			allocatedPremium++;
		}
		
		double[] output = {premiumIncome, allocatedPremium};
		
		return output;
	}
	
	private double[] allocateEconomy(double[] sortedOffers, int boundary, int count) {
		
		double economyIncome = 0;
		int allocatedEconomy = 0;
		
		int index = boundary - 1; 
		
		while(index >= 0 && count > 0) {
			economyIncome += sortedOffers[index];
			index--;
			count--;
			allocatedEconomy++;
		}
		
		double[] output = {economyIncome, allocatedEconomy};
		
		return output;
	}
	
	private String convertToString(double amount) {
		String[] input = String.valueOf(amount).split(".");
		
		String output = "";
		
		if(input.length > 1) {System.out.println(input.length > 1);
			output = input[1].equalsIgnoreCase("00") || input[1].equalsIgnoreCase("0") ? input[0] : String.valueOf(amount);
		} else output = String.valueOf(amount);
		
		System.out.println("Output: " + output);
		return output;
	}
}
