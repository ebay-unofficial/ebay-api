package ebayapi.models;

import ebayapi.utils.EbayItemCondition;

import java.util.ArrayList;
import java.util.List;

public class EbaySearchItem {

    protected String id;

    protected String title;

    protected double price;

    protected String currency;

    protected double shipping;

    protected EbayItemCondition condition;

    protected boolean auction;

    protected boolean buyNow;

    protected boolean suggestPrice;

    protected boolean priceRange;

    protected boolean ebayPlus;

    protected boolean newly;

    protected List<EbayItemImage> images;

    public EbaySearchItem() {
        this.images = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public double getTotalPrice() {
        return price + shipping;
    }

    public EbayItemCondition getCondition() {
        return condition;
    }

    public void setCondition(EbayItemCondition condition) {
        this.condition = condition;
    }

    public boolean isAuction() {
        return auction;
    }

    public void setAuction(boolean auction) {
        this.auction = auction;
    }

    public boolean isBuyNow() {
        return buyNow;
    }

    public void setBuyNow(boolean buyNow) {
        this.buyNow = buyNow;
    }

    public boolean isSuggestPrice() {
        return suggestPrice;
    }

    public void setSuggestPrice(boolean suggestPrice) {
        this.suggestPrice = suggestPrice;
    }

    public boolean isPriceRange() {
        return priceRange;
    }

    public void setPriceRange(boolean priceRange) {
        this.priceRange = priceRange;
    }

    public boolean isEbayPlus() {
        return ebayPlus;
    }

    public void setEbayPlus(boolean ebayPlus) {
        this.ebayPlus = ebayPlus;
    }

    public boolean isNewly() {
        return newly;
    }

    public void setNewly(boolean newly) {
        this.newly = newly;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<EbayItemImage> getImages() {
        return images;
    }

    public void setImages(List<EbayItemImage> images) {
        this.images = images;
    }

    public void addImage(EbayItemImage image) {
        if (!this.images.contains(image)) {
            this.images.add(image);
        }
    }

    public String getUrl() {
        return "https://www.ebay.de/itm/" + this.id;
    }
}
