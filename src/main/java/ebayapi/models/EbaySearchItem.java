package ebayapi.models;

import ebayapi.utils.EbayAuctionType;
import ebayapi.utils.EbayItemCondition;

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
