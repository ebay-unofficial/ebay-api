package ebayapi.controllers;

import ebayapi.models.SearchResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbayController {

    @RequestMapping("/")
    public SearchResult hello() {
        return new SearchResult("1");
    }

}
