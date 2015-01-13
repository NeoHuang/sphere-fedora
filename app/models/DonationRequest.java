package models;

import java.util.ArrayList;
import java.util.List;

public class DonationRequest {
	private List<Integer> receiverIdList = new ArrayList<Integer>();
	private int suggestedAmount;
	private int donationInCent;
	private boolean receipt;
	private boolean elefunds_agree;
	private List<String> receiverNameList = new ArrayList<String>();
	private List<Integer> availableReceiverIdList = new ArrayList<Integer>();
	
	private String foreignId;
	
	private String firstName;
	private String lastName;
	private String email;
	private String street;
	private String zip;
	private String city;
	private String countryCode;
	private String company;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getForeignId() {
		return foreignId;
	}

	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}

	public void setAgree(boolean agree){
		this.elefunds_agree = agree;
	}
	
	public boolean getAgree(){
		return this.elefunds_agree;
	}
	
	public void setReceiverList(String commaList){
		receiverIdList.clear();
		String[] ids = commaList.split(",");
		for (int i = 0; i < ids.length; i++){
			try {
				Integer id = Integer.parseInt(ids[i]);
				this.receiverIdList.add(id);
			}catch(NumberFormatException e){
				continue;
			}
			
		}
	}
	
	public List<Integer> getReceiverIdList() {
		return receiverIdList;
	}
	
	public void setSuggestedAmount(int amount) {
		this.suggestedAmount = amount;
	}
	
	public int getSuggestedAmount() {
		return this.suggestedAmount;
	}
	
	public void setDonation(int amount) {
		this.donationInCent = amount;
	}
	
	public int getDonation() {
		return this.donationInCent;
	}
	
	public void setReceipt(boolean receipt) {
		this.receipt = receipt;
	}
	
	public boolean getReceipt() {
		return this.receipt;
	}
	
	public void setReceiverNameList(String commaList){
		this.receiverNameList.clear();
		String[] names = commaList.split(",");
		for (int i = 0; i < names.length; i++){
			try {
				this.receiverNameList.add(names[i]);
			}catch(NumberFormatException e){
				continue;
			}
			
		}
	}
	
	public List<String> getReceiverNameList() {
		return this.receiverNameList;
	}
	
	public void setAvailabelReceivers(String commaList){
		this.availableReceiverIdList.clear();
		String[] ids = commaList.split(",");
		for (int i = 0; i < ids.length; i++){
			try {
				Integer id = Integer.parseInt(ids[i]);
				this.availableReceiverIdList.add(id);
			}catch(NumberFormatException e){
				continue;
			}
			
		}
	}
	
	public List<Integer> getAvailableReceivers() {
		return this.availableReceiverIdList;
	}
	
	
}
