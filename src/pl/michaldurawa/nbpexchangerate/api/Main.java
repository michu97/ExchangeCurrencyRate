package pl.michaldurawa.nbpexchangerate.api;

import pl.michaldurawa.nbpexchangerate.api.exceptions.ConnectionToExternalApiException;

public class Main {

	public static void main(String[] args) {
		SaleDocumentService saleDocumentService = new SaleDocumentService();
		try {
			saleDocumentService.insert();
		} catch (ConnectionToExternalApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
