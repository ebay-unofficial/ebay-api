package ebayapi.controllers;

import ebayapi.models.EbayDetailItem;
import ebayapi.models.EbaySearchResult;
import ebayapi.services.EbayDetailParser;
import ebayapi.services.EbayHttpService;
import ebayapi.utils.EbayLocationType;
import ebayapi.utils.EbayOrderType;
import ebayapi.utils.EbaySearchRequest;
import ebayapi.services.EbaySearchParser;
import java.util.ArrayList;
import java.util.List;
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
            @RequestParam(value = "auction", required = false, defaultValue = "false")
                    boolean auction,
            @RequestParam(value = "buynow", required = false, defaultValue = "false")
                    boolean buyNow,
            @RequestParam(value = "sold", required = false, defaultValue = "false") boolean sold,
            @RequestParam(value = "location", required = false, defaultValue = "") String location,
            @RequestParam(value = "order", required = false, defaultValue = "") String order,
            @RequestParam(value = "zip", required = false, defaultValue = "") String zip,
            @RequestParam(value = "description", required = false, defaultValue = "false")
                    boolean description,
            @RequestParam(value = "limit", required = false, defaultValue = "25") int limit
    ) {
        EbaySearchRequest ebaySearchRequest = new EbaySearchRequest(search)
                .isAuctions(auction)
                .isBuyNow(buyNow)
                .isSold(sold)
                .preferLocation(location)
                .withZip(zip)
                .searchInDescription(description)
                .orderBy(order);

        int rest = limit % 200;
        int restPage;
        int requestLimit;
        if (rest <= 25) {
            restPage = limit / 25 + 1;
            requestLimit = 25;
        } else if (rest <= 50) {
            restPage = limit / 50 + 1;
            requestLimit = 50;
        } else if (rest <= 100) {
            restPage = limit / 100 + 1;
            requestLimit = 100;
        } else {
            restPage = limit / 200 + 1;
            requestLimit = 200;
        }
        EbaySearchResult ebaySearchResult =
                searchParser.getSearch(ebaySearchRequest.limit(requestLimit).page(restPage));

        for (int i = 0; i < limit / 200; i++) {
            ebaySearchResult
                    .merge(searchParser.getSearch(ebaySearchRequest.limit(200).page(i + 1)));
        }

        return ebaySearchResult.crop(limit);
    }

    @RequestMapping("/item/{id}")
    public EbayDetailItem getItem(@PathVariable String id) {
        return detailParser.parseDetailItem(id);
    }

}
