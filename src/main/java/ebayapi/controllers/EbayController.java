package ebayapi.controllers;

import ebayapi.models.SearchResult;
import ebayapi.services.EbayHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbayController {

    @Autowired
    EbayHttpService httpService;

    @RequestMapping("/")
    public SearchResult hello() {
        return new SearchResult("1");
    }

    @RequestMapping("/getEbay")
    public String helloworld() {
        return httpService.httpGet("/itm/302276015487");
    }

}
