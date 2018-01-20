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
    public EbaySearchResult search(
            @RequestParam("s") String search,
            @RequestParam("auction") boolean auction,
            @RequestParam("buynow") boolean buyNow,
            @RequestParam("sold") boolean sold,
            @RequestParam("location") String location,
            @RequestParam("order") String order
    ) {
        return searchParser.getSearch(new EbaySearchRequest(search)
                .isAuctions(auction)
                .isBuyNow(buyNow)
                .isSold(sold)
                .preferLocation(location)
                .orderBy(order)
        );
    }

    @RequestMapping("/item/{id}")
    public EbayDetailItem getItem(@PathVariable String id) {
        return detailParser.parseDetailItem(id);
    }

}
