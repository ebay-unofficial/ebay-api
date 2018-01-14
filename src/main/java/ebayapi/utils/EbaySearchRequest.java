package ebayapi.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EbaySearchRequest {

    private String term;
    private String params;

    public EbaySearchRequest(String term) {
        this.term = term;
        params = "";
    }

    public EbaySearchRequest inAuctions() {
        params += "&LH_Auction=1";
        return this;
    }

    public EbaySearchRequest inBuyNow() {
        params += "&LH_BIN=1";
        return this;
    }

    public EbaySearchRequest orderBy(EbayOrderType orderType) {
        params += "_sop=" + orderType.id;
        return this;
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
