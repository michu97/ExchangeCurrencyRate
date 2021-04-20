package pl.michaldurawa.nbpexchangerate.api;

import java.math.BigDecimal;
import java.time.LocalDate;

import pl.michaldurawa.nbpexchangerate.api.service.HttpRequestService;
import pl.michaldurawa.nbpexchangerate.api.service.HttpRequestServiceImpl;

public class NbpApi implements ExchangeRateApi {
	private final HttpRequestService service;
	
	public NbpApi() {
		this.service = new HttpRequestServiceImpl();
	}

	@Override
	public BigDecimal getAmountFromPLN(LocalDate date, BigDecimal pln,
			CurrencyCode code) {
		final ResponseReciver reciver = new NbpResponseReciver(date, code, service);
		return pln.multiply(reciver.getExchangeRate());
	}

	@Override
	public BigDecimal getAmountFromPLN(BigDecimal pln, CurrencyCode code) {
		return getAmountFromPLN(LocalDate.now(), pln, code);
	}

}
