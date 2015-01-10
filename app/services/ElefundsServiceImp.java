package services;

import javax.inject.Inject;
import javax.inject.Singleton;

import models.DonationRequest;

@Singleton
public class ElefundsServiceImp implements ElefundsService {
	
	
    
	@Inject
    public ElefundsServiceImp() {
    }
	public int SendDonate(DonationRequest dr){
		return 0;
	}
}
