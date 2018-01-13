package ebayapi.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EbaySearchRequest {

    private String term;

    public EbaySearchRequest(String term) {
        this.term = term;
    }

    @Override
    public String toString() {
        // isRefine=true&LH_BIN=1&_sop=15&_nkw=zelda+link+to+the+past&LH_PrefLoc=2&_mwBanner=1
        try {
            return "/sch/i.html?_nkw=" + URLEncoder.encode(term, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
