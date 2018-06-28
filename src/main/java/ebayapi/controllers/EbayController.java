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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
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
            @RequestParam(value = "auction", required = false, defaultValue = "false") boolean auction,
            @RequestParam(value = "buynow", required = false, defaultValue = "false") boolean buyNow,
            @RequestParam(value = "sold", required = false, defaultValue = "false") boolean sold,
            @RequestParam(value = "location", required = false, defaultValue = "") String location,
            @RequestParam(value = "order", required = false, defaultValue = "") String order,
            @RequestParam(value = "limit", required = false, defaultValue = "25") int limit
    ) {
        return searchParser.getSearch(new EbaySearchRequest(search)
                .isAuctions(auction)
                .isBuyNow(buyNow)
                .isSold(sold)
                .preferLocation(location)
                .orderBy(order)
                .limit(limit)
        );
    }

    @RequestMapping("/item/{id}")
    public EbayDetailItem getItem(@PathVariable String id) {
        return detailParser.parseDetailItem(id);
    }

}
