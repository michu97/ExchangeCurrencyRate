package pl.michaldurawa.nbpexchangerate.api;

import java.net.URL;
import java.time.LocalDate;

public interface ApiUrlGenerator {
	URL getURL(LocalDate date, CurrencyCode code);
}
