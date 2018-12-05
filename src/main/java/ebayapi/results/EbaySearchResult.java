package ebayapi.results;

import ebayapi.models.EbaySearchItem;
import ebayapi.utils.EbayItemCondition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EbaySearchResult {

    private String id;

    private List<String> urls = new ArrayList<>();

    private List<EbaySearchItem> items = new ArrayList<>();

    private List<EbaySearchItem> ads = new ArrayList<>();

    private String zip;

    private int total;

    Map<EbayItemCondition, Integer> conditionCount = new HashMap<>();

    public EbaySearchResult() {
        this("");
    }

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

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public void addUrl(String url) {
        this.urls.add(url);
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

    public EbaySearchResult crop(int size, int shift) {
        items = items.subList(shift, shift + size);
        return this;
    }

    public EbaySearchResult append(EbaySearchResult other) {
        this.items.addAll(other.items);
        this.ads.addAll(other.ads);
        this.urls.addAll(other.urls);
        return this;
    }

    public EbaySearchResult prepend(EbaySearchResult other) {
        other.items.addAll(this.items);
        other.ads.addAll(this.ads);
        other.urls.addAll(this.urls);
        this.items = other.items;
        this.ads = other.ads;
        this.urls = other.urls;
        return this;
    }
}
