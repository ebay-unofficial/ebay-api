package ebayapi.utils;

import ebayapi.models.EbaySearchResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class EbaySearchRequest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private String term;

    private Map<String, String> params = new LinkedHashMap<>();

    private String seller;

    public EbaySearchRequest(String term) {
        this.term = term;
    }

    public EbaySearchRequest isComplete(boolean complete) {
        params.put("LH_Complete", complete ? "1" : "0");
        return this;
    }

    public EbaySearchRequest isSold(boolean sold) {
        params.put("LH_Sold", sold ? "1" : "0");
        return isComplete(sold);
    }

    public EbaySearchRequest isAuctions(boolean auction) {
        params.put("LH_Auction", auction ? "1" : "0");
        return this;
    }

    public EbaySearchRequest isBuyNow(boolean buyNow) {
        params.put("LH_BIN", buyNow ? "1" : "0");
        return this;
    }

    public EbaySearchRequest orderBy(String order) {
        switch (order) {
            case "soonest":
                return this.orderBy(EbayOrderType.ENDING_SOONEST);
            case "fewest":
                return this.orderBy(EbayOrderType.FEWEST_BIDS);
            case "most":
                return this.orderBy(EbayOrderType.MOST_BIDS);
            case "nearest":
                return this.orderBy(EbayOrderType.NEAREST_DISTANCE);
            case "newly":
                return this.orderBy(EbayOrderType.NEWLY_LISTED);
            case "best":
                return this.orderBy(EbayOrderType.BEST_MATCH);
            case "lowest":
                return this.orderBy(EbayOrderType.LOWEST_PRICE_FIRST);
            case "highest":
                return this.orderBy(EbayOrderType.HIGHEST_PRICE_FIRST);
            default:
                return this;
        }
    }

    public EbaySearchRequest orderBy(EbayOrderType orderType) {
        params.put("_sop", orderType.id.toString());
        return this;
    }

    public EbaySearchRequest preferLocation(String location) {
        switch (location) {
            case "ebay":
                return this.preferLocation(EbayLocationType.EBAY);
            case "region":
                return this.preferLocation(EbayLocationType.REGION);
            case "continental":
                return this.preferLocation(EbayLocationType.CONTINENTAL);
            case "global":
                return this.preferLocation(EbayLocationType.GLOBAL);
            default:
                return this;
        }
    }

    public EbaySearchRequest preferLocation(EbayLocationType locationType) {
        params.put("LH_PrefLoc", locationType.id.toString());
        return this;
    }

    public EbaySearchRequest withZip(String zip) {
        if (StringUtils.isNotEmpty(zip)) {
            params.put("_stpos", zip);
            params.put("_localstpos", zip);
        }
        return this;
    }

    public EbaySearchRequest searchInDescription(boolean include) {
        params.put("LH_TitleDes", include ? "1" : "0");
        return this;
    }

    public EbaySearchRequest setSeller(String seller) {
        this.seller = seller;
        return this;
    }

    public EbaySearchRequest limit(int limit) {
        params.put("_ipg", Integer.toString(limit));
        return this;
    }

    public EbaySearchRequest page(int page) {
        params.put("_pgn", Integer.toString(page));
        return this;
    }

    @Override
    public String toString() {
        StringBuilder paramsBuilder = new StringBuilder();
        params.forEach((key, value) -> {
            paramsBuilder.append("&" + key + "=" + value);
        });
        try {
            String type = "i";
            if (!seller.isEmpty()) {
                type = seller + "/m";
            }
            return  "/sch/" + type + ".html?_nkw=" + URLEncoder.encode(term, "UTF-8") + paramsBuilder;
        } catch (UnsupportedEncodingException e) {
            log.error("Failed to encode params: {}", term, e);
        }
        return null;
    }
}
