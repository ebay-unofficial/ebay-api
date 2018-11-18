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
            @RequestParam(value = "auction", required = false, defaultValue = "false") boolean auction,
            @RequestParam(value = "buynow", required = false, defaultValue = "false") boolean buyNow,
            @RequestParam(value = "sold", required = false, defaultValue = "false") boolean sold,
            @RequestParam(value = "location", required = false, defaultValue = "") String location,
            @RequestParam(value = "order", required = false, defaultValue = "") String order,
            @RequestParam(value = "zip", required = false, defaultValue = "") String zip,
            @RequestParam(value = "description", required = false, defaultValue = "false") boolean description,
            @RequestParam(value = "limit", required = false, defaultValue = "25") int limit,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page
    ) {
            EbaySearchRequest ebaySearchRequest = new EbaySearchRequest(search)
                    .isAuctions(auction)
                    .isBuyNow(buyNow)
                    .isSold(sold)
                    .preferLocation(location)
                    .withZip(zip)
                    .searchInDescription(description)
                    .orderBy(order);

        int ebayPage, ebayLimit;

        // Crawl end
        int restEnd = (limit * page) % 200;
        if (restEnd <= 25) {
            ebayPage = (int) Math.ceil((limit * page) / 25.0);
            ebayLimit = 25;
        } else if (restEnd <= 50) {
            ebayPage = (int) Math.ceil((limit * page) / 50.0);
            ebayLimit = 50;
        } else if (restEnd <= 100) {
            ebayPage = (int) Math.ceil((limit * page) / 100.0);
            ebayLimit = 100;
        } else {
            ebayPage = (int) Math.ceil((limit * page) / 200.0);
            ebayLimit = 200;
        }
        EbaySearchResult ebaySearchResult = searchParser.getSearch(ebaySearchRequest.limit(ebayLimit).page(ebayPage));

        // Crawl middle
        for (int i = ((limit * (page-1)) / 200) + 1; i < (limit * page) / 200; i++) {
            ebaySearchResult.prepend(searchParser.getSearch(ebaySearchRequest.limit(200).page(i+1)));
        }

        // Crawl start
        if ((limit * (page-1)+1)/200 != (limit * page)/200) {
            int restStart = 200 - ((limit * (page - 1)) % 200);
            if (restStart <= 25) {
                ebayPage = (limit * (page - 1)) / 25 + 1;
                ebayLimit = 25;
            } else if (restStart <= 50) {
                ebayPage = (limit * (page - 1)) / 50 + 1;
                ebayLimit = 50;
            } else if (restStart <= 100) {
                ebayPage = (limit * (page - 1)) / 100 + 1;
                ebayLimit = 100;
            } else {
                ebayPage = (limit * (page - 1)) / 200 + 1;
                ebayLimit = 200;
            }

            ebaySearchResult.prepend(searchParser.getSearch(ebaySearchRequest.limit(ebayLimit).page(ebayPage)));
        }

        return ebaySearchResult.crop(limit, limit * (page-1) - ebayLimit * (ebayPage - 1));
    }

    @RequestMapping("/item/{id}")
    public EbayDetailItem getItem(@PathVariable String id) {
        return detailParser.parseDetailItem(id);
    }

}
