package pl.michaldurawa.nbpexchangerate.api;

public class Main {

	public static void main(String[] args) {
		NBPExchangeRateApi api = new NBPExchangeRateApi();
		System.out.println(api.getExchangeRateTable());
	}

}
