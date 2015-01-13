package services;

import java.util.Calendar;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.WS;
import models.DonationRequest;
import play.libs.Json;

@Singleton
public class ElefundsServiceImp implements ElefundsService {
	
	
    
	@Inject
    public ElefundsServiceImp() {
    }
	public Promise<WS.Response> SendDonate(DonationRequest dr){
		
		ObjectNode json = Json.newObject();
		
			json.put("foreignId", dr.getForeignId());
			String thisMoment = String.format("%tFT%<tRZ",
                    Calendar.getInstance(TimeZone.getTimeZone("Z")));
			json.put("donationTimestamp", thisMoment);
			json.put("donationAmount", dr.getDonation());
			json.put("receivers", Json.toJson(dr.getReceiverIdList()));
			json.put("receiversAvailable", Json.toJson(dr.getAvailableReceivers()));
			json.put("grandTotal", 1000);
			json.put("donationAmountSuggested", dr.getSuggestedAmount());
			
			ObjectNode donator = Json.newObject();
			donator.put("email", dr.getEmail());
			donator.put("firstName", dr.getFirstName());
			donator.put("lastName", dr.getLastName());
			donator.put("streetAddress", dr.getStreet());
			donator.put("zip", dr.getZip());
			donator.put("city", dr.getCity());
			donator.put("countryCode", dr.getCountryCode());
			donator.put("company", dr.getCompany());
			json.put("donator", donator);
			
		
		String jsonStr = "[" + json.toString() + "]";
		Promise<WS.Response> result = WS.url("https://connect.elefunds.de/donations/?clientId=1001&hashedKey=eb85fa24f23b7ade5224a036b39556d65e764653").post(jsonStr);
		return result;
	}
}
