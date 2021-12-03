package com.smarthost.SmartHost.controller;

import java.text.DecimalFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoreController {
	
	private final double[] offers = {23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209};
	
	@RequestMapping("/welcome")
	public String welcome() {
		return "Welcome";
	}
	
	@GetMapping(value= "/income", produces = MediaType.TEXT_HTML_VALUE)
	public String getIncome(@RequestParam int premiumRooms, @RequestParam int economyRooms) {
		
		//This method returns the optimized income that can be made per night
		
		//Sort the array of customers to easily separate premium customers from economy customers based on the amount offered
		Arrays.sort(offers);
		
		//Store the arguments passed in
		int economyRoomCount = economyRooms;
		int premiumRoomCount = premiumRooms;
		
		//Initialise variables to store final income
		double premiumUsage = 0;
		double economyUsage = 0;
		
		//Initialise variables to store number of rooms allocated to customers
		int allocatedPremiumOutput = 0;
		int allocatedEconomyOutput = 0;
		
		//Initialise variables to store the number of premium and economy customers who want rooms
		int economyCustomers = 0;
		int premiumCustomers = 0;
		
		//Count the number of premium and economy customers
		for(int x = 0; x < offers.length; x++) {
			if(offers[x] >= 100) {
				economyCustomers = x;
				premiumCustomers = offers.length - economyCustomers;
				break;
			}
		}
		
		//Determine if economy customers will be upgraded to premium rooms
		boolean upgradeRequired = economyCustomers - economyRoomCount > 0 && premiumRoomCount - premiumCustomers > 0 ? true : false;
	
		//Calculate the number of premium rooms that will be allocated and the income
		double[] result1 = allocatePremium(offers, economyCustomers, premiumRoomCount);
		premiumUsage = result1[0];
		allocatedPremiumOutput =  (int)result1[1];
		
		
		if(upgradeRequired) {
			
			//Calculate the number of economy customers to be upgraded
			//and amount to be realised
			int emptyPremium = premiumRoomCount - premiumCustomers;
			double[] result2 =  allocateEconomy(offers, economyCustomers, emptyPremium);
			premiumUsage += result2[0];
			allocatedPremiumOutput += result2[1];
			
			//Calculate the number of economy customers who will get economy rooms
			double[] result3 = allocateEconomy(offers, economyCustomers - emptyPremium, economyRoomCount );
			economyUsage = result3[0];
			allocatedEconomyOutput = (int)result3[1];
		} else {
			
			//Calculate the number of economy customers who will get economy rooms
			double[] result4 = allocateEconomy(offers, economyCustomers, economyRoomCount );
			economyUsage = result4[0];
			allocatedEconomyOutput = (int)result4[1];
		}
		
		//Trim the floating point values
		String p  = (100 * premiumUsage) % 100 == 0 || (10 * premiumUsage) % 10 == 0  ? String.valueOf( (int) premiumUsage) : String.valueOf( premiumUsage);
		String e = (100 * economyUsage) % 100 == 0 || (10 * economyUsage) % 10 == 0 ? String.valueOf((int) economyUsage) : String.valueOf(economyUsage);
		
		
		return String.format("<p>Usage premium: %d(EUR <b>%s</b>)</p><br><p>Usage economy: %d(EUR <b>%s</b>)</p>", allocatedPremiumOutput, p, allocatedEconomyOutput, e);
	}
	
	private double[] allocatePremium(double[] sortedOffers, int boundary, int count) {
		
		//Initialise variables to store output
		double premiumIncome = 0;
		int allocatedPremium = 0;
		
		int index = sortedOffers.length - 1; 
		
		//Allocate rooms
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
		
		//Initialise variables to store output
		double economyIncome = 0;
		int allocatedEconomy = 0;
		
		int index = boundary - 1; 
		
		//Allocate rooms
		while(index >= 0 && count > 0) {
			economyIncome += sortedOffers[index];
			index--;
			count--;
			allocatedEconomy++;
		}
		
		double[] output = {economyIncome, allocatedEconomy};
		
		return output;
	}
	
}
