package ebayapi.services;

import ebayapi.models.EbaySearchItem;
import ebayapi.models.EbaySearchResult;
import ebayapi.utils.EbayItemCondition;
import ebayapi.utils.EbaySearchRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EbaySearchParser {

    @Autowired
    EbayHttpService httpService;

    public EbaySearchResult getSearch(EbaySearchRequest request) {
        return getSearch(httpService.httpGet(request));
    }

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
        EbaySearchItem item = new EbaySearchItem();

        Elements iid = e.getElementsByAttribute("iid");
        String itemId = iid.attr("iid");
        item.setId(itemId);

        Elements titleElement = e.select("h3 > a");
        String title = titleElement.text();
        item.setTitle(title);

        String format = e.getElementsByClass("lvformat").text();
        if (format.toLowerCase().contains("gebot")) {
            item.setAuction(true);
        }
        if (format.toLowerCase().contains("sofort-kauf") || format.toLowerCase().contains("preisvorschlag")) {
            item.setBuyNow(true);
        }
        if (format.toLowerCase().contains("preisvorschlag")) {
            item.setSuggestPrice(true);
        }

        Element lvsubtitle = e.getElementsByClass("lvsubtitle").last();
        String conditionText;
        if (lvsubtitle != null) {
            conditionText = lvsubtitle.text();
        } else {
            conditionText = "";
        }
        EbayItemCondition itemCondition = EbayItemCondition.UNKNOWN;
        for (EbayItemCondition condition : EbayItemCondition.values()) {
            if (conditionText.toLowerCase().contains(condition.name.toLowerCase())) {
                itemCondition = condition;
                break;
            }
        }
        item.setCondition(itemCondition);

        return item;
    }

    private Document parse(String html) {
        return Jsoup.parse(html);
    }
}
