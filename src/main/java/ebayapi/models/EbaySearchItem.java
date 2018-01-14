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

    protected EbayAuctionType auctionType;

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

    public EbayAuctionType getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(EbayAuctionType auctionType) {
        this.auctionType = auctionType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
