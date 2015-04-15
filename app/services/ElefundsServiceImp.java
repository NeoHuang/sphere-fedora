package services;

import java.util.Calendar;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.fasterxml.jackson.databind.node.ObjectNode;

import play.Play;
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
			String thisMoment = String.format("%tFT%<tT%<tz",
                    Calendar.getInstance(TimeZone.getDefault()));
		        play.Logger.debug(thisMoment);
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
		play.Logger.debug(jsonStr);
		String eleID = Play.application().configuration().getString("elefunds.id");
		String eleKey = Play.application().configuration().getString("elefunds.key");
		WS.WSRequestHolder request = WS.url("https://connect.elefunds.de/donations/").setQueryParameter("clientId", eleID).setQueryParameter("hashedKey", eleKey).setHeader("Content-Type", "application/json");

		Promise<WS.Response> result = request.post(jsonStr);
		
		result.map(new Function<WS.Response, String>() {
            public String apply(WS.Response response) {
		String json = response.getBody();
		int status = response.getStatus();
		play.Logger.debug("elefunds response " + status);
		play.Logger.debug(json);
                return json;
            }
        });

		return result;
	}
}
