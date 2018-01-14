package ebayapi.models;

public class EbayDetailItem extends EbaySearchItem {

    private EbaySeller seller;

    public EbayDetailItem(String id) {
        this.id = id;
    }

    public EbaySeller getSeller() {
        return seller;
    }

    public void setSeller(EbaySeller seller) {
        this.seller = seller;
    }

}
