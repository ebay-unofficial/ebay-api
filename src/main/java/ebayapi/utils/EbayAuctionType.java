package ebayapi.utils;

public enum EbayAuctionType {

    AUCTION("Auktion"),
    BUY_NOW("Sofort-Kaufen");


    public String name;


    EbayAuctionType(String name) {
        this.name = name;
    }
}
