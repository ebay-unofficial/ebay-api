package ebayapi.services;

import ebayapi.utils.EbaySearchRequest;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EbayHttpService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    private String baseUrl = "https://www.ebay.de";
    private String lastRequest = "";

    public EbayHttpService() {

    }

    public String httpGet(String url) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            lastRequest = url;
            String fullUrl = baseUrl + url;
            HttpResponse response = httpClient.execute(new HttpGet(fullUrl));
            log.info("Request: status: {}, url: {}", response.getStatusLine().getStatusCode(), fullUrl);

            return IOUtils.toString(response.getEntity().getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String httpGet(EbaySearchRequest request) {
        return httpGet(request.toString());
    }

    public String getLastRequest() {
        return baseUrl + lastRequest;
    }
}
