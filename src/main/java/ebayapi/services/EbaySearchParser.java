package ebayapi.services;

import ebayapi.models.EbaySearchItem;
import ebayapi.models.EbaySearchResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EbaySearchParser {

    public EbaySearchResult getSearch(String html) {
        Document parsedHtml = parse(html);
        List<EbaySearchItem> searchItems = getSearchItems(parsedHtml);
        EbaySearchResult result = new EbaySearchResult("");
        result.setItems(searchItems);
        return result;
    }

    private List<EbaySearchItem> getSearchItems(Document html) {
        ArrayList<EbaySearchItem> items = new ArrayList<>();
        Element listViewInner = html.getElementById("ListViewInner");
        Elements children = listViewInner.select("li[listingid]");
        children.forEach(e -> {
            EbaySearchItem item = getListItem(e);
            items.add(item);
        });
        return items;
    }

    private EbaySearchItem getListItem(Element e) {

        Elements iid = e.getElementsByAttribute("iid");
        String itemId = iid.attr("iid");
        EbaySearchItem item = new EbaySearchItem();
        item.setId(itemId);
        return item;
    }

    private Document parse(String html) {
        return Jsoup.parse(html);
    }
}
