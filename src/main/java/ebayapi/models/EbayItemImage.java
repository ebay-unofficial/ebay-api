package ebayapi.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

public class EbayItemImage {

    public static final String ebayimg = "https://i.ebayimg.com";

    public String id;

    private int size = 1600;

    private String type;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int width;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int height;

    public EbayItemImage(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return ebayimg + "/images/" + type + "/" + id + "/s-l" + size + ".jpg";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EbayItemImage that = (EbayItemImage) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
