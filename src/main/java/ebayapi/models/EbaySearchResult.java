package ebayapi.models;

import java.util.ArrayList;
import java.util.List;

public class EbaySearchResult {

    private String id;

    private String url;

    private List<EbaySearchItem> items = new ArrayList<>();

    private List<EbaySearchItem> ads = new ArrayList<>();

    public EbaySearchResult() { this(""); }

    public EbaySearchResult(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<EbaySearchItem> getItems() {
        return items;
    }

    public void setItems(List<EbaySearchItem> items) {
        this.items = items;
    }

    public List<EbaySearchItem> getAds() {
        return ads;
    }

    public void setAds(List<EbaySearchItem> ads) {
        this.ads = ads;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSize() {
        return items.size();
    }
}
