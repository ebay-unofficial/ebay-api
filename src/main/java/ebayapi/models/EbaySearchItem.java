package ebayapi.models;

import ebayapi.utils.EbayAuctionType;
import ebayapi.utils.EbayItemCondition;

public class EbaySearchItem {

    private String id;

    private String title;

    private float price;

    private float shipping;

    private EbayItemCondition condition;

    private boolean auction;

    private boolean buyNow;

    private boolean suggestPrice;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getShipping() {
        return shipping;
    }

    public void setShipping(float shipping) {
        this.shipping = shipping;
    }

    public float getTotalPrice() {
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
}
