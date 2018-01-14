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

    @Override
    public String toString() {
        // isRefine=true&LH_BIN=1&_sop=15&_nkw=zelda+link+to+the+past&LH_PrefLoc=2&_mwBanner=1
        try {
            return "/sch/i.html?_nkw=" + URLEncoder.encode(term, "UTF-8") + params;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
