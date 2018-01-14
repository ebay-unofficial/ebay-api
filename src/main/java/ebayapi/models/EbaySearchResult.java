package ebayapi.models;

import java.util.ArrayList;
import java.util.List;

public class EbaySearchResult {

    private String id;

    private List<EbaySearchItem> items = new ArrayList<>();

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

    public int getSize() {
        return items.size();
    }
}
