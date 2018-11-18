package ebayapi.models;

import ebayapi.utils.EbayItemCondition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EbaySearchResult {

    private String id;

    private String url;

    private List<EbaySearchItem> items = new ArrayList<>();

    private List<EbaySearchItem> ads = new ArrayList<>();

    private String zip;

    private int total;

    Map<EbayItemCondition, Integer> conditionCount = new HashMap<>();

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Map<EbayItemCondition, Integer> getConditionCount() {
        return conditionCount;
    }

    public void setConditionCount(Map<EbayItemCondition, Integer> conditionCount) {
        this.conditionCount = conditionCount;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public int getSize() {
        return items.size();
    }

    public int getAdSize() {
        return ads.size();
    }

    public EbaySearchResult crop(int limit) {
        items = items.subList(0, limit);
        return this;
    }

    public EbaySearchResult merge(EbaySearchResult other) {
        this.items.addAll(other.items);
        this.ads.addAll(other.ads);
        return this;
    }
}
