package pl.michaldurawa.nbpexchangerate.api;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ExchangeRateApi {
	BigDecimal getAmountFromPLN(LocalDate date, BigDecimal pln, CurrencyCode code);
	BigDecimal getAmountFromPLN(BigDecimal pln, CurrencyCode code);
}
