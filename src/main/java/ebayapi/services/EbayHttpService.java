package ebayapi.services;

import org.springframework.stereotype.Service;

@Service
public class EbayHttpService {

    private String publicUrl = "https://www.ebay.de";

    public String httpGet(String url) {
        return publicUrl + url;
    }

}
