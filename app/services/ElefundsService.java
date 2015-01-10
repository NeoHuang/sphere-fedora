package services;

import play.libs.WS;
import play.libs.F.Promise;
import models.DonationRequest;

public interface ElefundsService {
	public Promise<WS.Response>  SendDonate(DonationRequest dr);
}
