package ebayapi.parsers;


import ebayapi.services.EbayHttpService;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class EbayParser {

    @Autowired
    protected EbayHttpService httpService;

}
