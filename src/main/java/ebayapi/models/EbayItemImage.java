package ebayapi.models;

public class EbayItemImage {

    public static final String ebayimg = "https://i.ebayimg.com";

    public String id;

    public int size = 1600;

    public EbayItemImage(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return ebayimg + "/images/" + id + "/s-l" + size + ".jpg";
    }

}
