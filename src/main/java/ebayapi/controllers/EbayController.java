package ebayapi.controllers;

import ebayapi.models.EbayDetailItem;
import ebayapi.models.EbaySearchResult;
import ebayapi.services.EbayDetailParser;
import ebayapi.services.EbayHttpService;
import ebayapi.utils.EbayLocationType;
import ebayapi.utils.EbayOrderType;
import ebayapi.utils.EbaySearchRequest;
import ebayapi.services.EbaySearchParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EbayController {

    @Autowired
    EbayHttpService httpService;

    @Autowired
    EbaySearchParser searchParser;

    @Autowired
    EbayDetailParser detailParser;

    @RequestMapping("/search")
    public EbaySearchResult search(@RequestParam("s") String search) {
        return searchParser.getSearch(new EbaySearchRequest(search));
    }

    @RequestMapping("/search/{order}")
    public EbaySearchResult search(@RequestParam("s") String search, @PathVariable("order") String order) {
        return searchParser.getSearch(new EbaySearchRequest(search).orderBy(order));
    }

    @RequestMapping("/search/{location}/{order}")
    public EbaySearchResult search(@RequestParam("s") String search, @PathVariable("location") String location, @PathVariable("order") String order) {
        return searchParser.getSearch(new EbaySearchRequest(search).preferLocation(location).orderBy(order));
    }

    @RequestMapping("/auction")
    public EbaySearchResult searchAuction(@RequestParam("s") String search) {
        return searchParser.getSearch(new EbaySearchRequest(search).inAuctions());
    }

    @RequestMapping("/auction/{order}")
    public EbaySearchResult searchAuction(@RequestParam("s") String search, @PathVariable("order") String order) {
        return searchParser.getSearch(new EbaySearchRequest(search).inAuctions().orderBy(order));
    }

    @RequestMapping("/auction/{location}/{order}")
    public EbaySearchResult searchAuction(@RequestParam("s") String search, @PathVariable("location") String location, @PathVariable("order") String order) {
        return searchParser.getSearch(new EbaySearchRequest(search).inAuctions().preferLocation(location).orderBy(order));
    }

    @RequestMapping("/buynow")
    public EbaySearchResult searchBuynow(@RequestParam("s") String search) {
        return searchParser.getSearch(new EbaySearchRequest(search).inBuyNow());
    }

    @RequestMapping("/buynow/{order}")
    public EbaySearchResult searchBuynow(@RequestParam("s") String search, @PathVariable("order") String order) {
        return searchParser.getSearch(new EbaySearchRequest(search).inBuyNow().orderBy(order));
    }

    @RequestMapping("/buynow/{location}/{order}")
    public EbaySearchResult searchBuynow(@RequestParam("s") String search, @PathVariable("location") String location, @PathVariable("order") String order) {
        return searchParser.getSearch(new EbaySearchRequest(search).inBuyNow().preferLocation(location).orderBy(order));
    }

    @RequestMapping("/item/{id}")
    public EbayDetailItem getItem(@PathVariable String id) {
        return detailParser.parseDetailItem(id);
    }

}
