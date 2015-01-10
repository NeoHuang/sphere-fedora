package models;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class Donation {
	private String foreignId;
	private DateTime donationTime;
	private int	amount;
	private int suggestedAmount;
	private List<Integer> receiverIdList = new ArrayList<Integer>();
	private int grandTotal;
	void setForeignId(String id){
		foreignId = id;
	}
	
  String getForeignId(){
	  return foreignId;
  }

  void setTime(DateTime time){
	  donationTime = time;
  }

  DateTime getTime(){
	  return donationTime;
  }

  void setAmount(int amount){
	  this.amount = amount;
	  
  }

  int getAmount(){
	  return this.amount;
  }

  void setSuggestedAmount(int amount){
	  suggestedAmount = amount;
  }

  int getSuggestedAmount(){
	  return suggestedAmount;
  }

  void addReceiverId(int id){
	  receiverIdList.add(id);
  }

  void setReceiverIds(List<Integer> ids){
	  this.receiverIdList = ids;
  }

  List<Integer> getReceiverIds(){
	  return this.receiverIdList;
  }

  void setGrandTotal(int total){
	  this.grandTotal = total;
  }

  int getGrandTotal(){
	  return this.grandTotal;
  }

}
