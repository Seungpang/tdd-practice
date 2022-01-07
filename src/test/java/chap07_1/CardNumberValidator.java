package chap07_1;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CardNumberValidator {
	public CardValidity validate(String cardumber) {
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create("https://some-external-pg.com/card"))
			.header("Content-Type", "text/plain")
			.POST(HttpRequest.BodyPublishers.ofString(cardumber))
			.build();

		try {
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			switch (response.body()) {
				case "ok":
					return CardValidity.VALID;
				case "bad":
					return CardValidity.INVALID;
				case "expired":
					return CardValidity.EXPIRED;
				case "theft":
					return CardValidity.THEFT;
				default:
					return CardValidity.UNKNOWN;
			}
		} catch (IOException | InterruptedException exception) {
			return CardValidity.ERROR;
		}
	}
}
