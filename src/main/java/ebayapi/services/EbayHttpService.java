package ebayapi.services;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EbayHttpService {

    private HttpClient httpClient;
    private String baseUrl = "https://www.ebay.de";

    public EbayHttpService() {
        httpClient = HttpClientBuilder.create().build();
    }

    public String httpGet(String url) {
        try {
            return IOUtils.toString(httpClient.execute(new HttpGet(baseUrl + url)).getEntity().getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
