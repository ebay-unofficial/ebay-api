package ebayapi.models;

import ebayapi.utils.EbayAuctionType;
import ebayapi.utils.EbayItemCondition;

public class EbaySearchItem {

    private String id;

    private String title;

    private float price;

    private float shipping;

    private EbayItemCondition condition;

    private EbayAuctionType auctionType;

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

    public EbayAuctionType getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(EbayAuctionType auctionType) {
        this.auctionType = auctionType;
    }
}
