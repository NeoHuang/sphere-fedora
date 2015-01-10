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
