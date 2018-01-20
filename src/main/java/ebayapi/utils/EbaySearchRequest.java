package ebayapi.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EbaySearchRequest {

    private String term;
    private String params;
    private boolean isCompleted = false;
    private boolean isSold = false;

    public EbaySearchRequest(String term) {
        this.term = term;
        params = "";
    }

    public EbaySearchRequest inComplete() {
        if (!isCompleted) {
            params += "&LH_Complete=1";
            isCompleted = true;
        }
        return this;
    }

    public EbaySearchRequest inSold() {
        if (!isSold) {
            params += "&LH_Sold=1";
            isSold = true;
        }
        return inComplete();
    }

    public EbaySearchRequest inAuctions() {
        params += "&LH_Auction=1";
        return this;
    }

    public EbaySearchRequest inBuyNow() {
        params += "&LH_BIN=1";
        return this;
    }

    public EbaySearchRequest orderBy(String order) {
        switch (order) {
            case "soonest": return this.orderBy(EbayOrderType.ENDING_SOONEST);
            case "fewest": return this.orderBy(EbayOrderType.FEWEST_BIDS);
            case "most": return this.orderBy(EbayOrderType.MOST_BIDS);
            case "nearest": return this.orderBy(EbayOrderType.NEAREST_DISTANCE);
            case "newly": return this.orderBy(EbayOrderType.NEWLY_LISTED);
            case "best": return this.orderBy(EbayOrderType.BEST_MATCH);
            case "lowest": return this.orderBy(EbayOrderType.LOWEST_PRICE_FIRST);
            case "highest": return this.orderBy(EbayOrderType.HIGHEST_PRICE_FIRST);
            default: return this;
        }
    }

    public EbaySearchRequest orderBy(EbayOrderType orderType) {
        params += "_sop=" + orderType.id;
        return this;
    }

    public EbaySearchRequest preferLocation(String location) {
        switch (location) {
            case "ebay": return this.preferLocation(EbayLocationType.EBAY);
            case "region": return this.preferLocation(EbayLocationType.REGION);
            case "continental": return this.preferLocation(EbayLocationType.CONTINENTAL);
            case "global": return this.preferLocation(EbayLocationType.GLOBAL);
            default: return this;
        }
    }

    public EbaySearchRequest preferLocation(EbayLocationType locationType) {
        params += "LH_PrefLoc=" + locationType.id;
        return this;
    }

    public EbaySearchRequest limit(int limit) {
        params += "_ipg=" + limit;
        return this;
    }

    @Override
    public String toString() {
        try {
            return "/sch/i.html?_nkw=" + URLEncoder.encode(term, "UTF-8") + params;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
