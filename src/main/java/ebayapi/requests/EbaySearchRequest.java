package ebayapi.requests;

import ebayapi.utils.EbayLocationType;
import ebayapi.utils.EbayOrderType;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EbaySearchRequest extends EbayRequest {

    private String term;

    private String type;

    private String seller;

    public EbaySearchRequest(String term) {
        this.term = term;
        try {
            params.put("_nkw", URLEncoder.encode(term, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        return this.setType(seller.isEmpty() ? "i" : seller + "/m");
    }

    public EbaySearchRequest setType(String type) {
        this.type = type;
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
    protected String baseUrl() {
        return "https://www.ebay.de";
    }

    @Override
    protected String path() {
        return "/sch/" + type + ".html";
    }
}
