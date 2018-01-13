package ebayapi.services;

import ebayapi.models.EbaySearchItem;
import ebayapi.models.EbaySearchResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

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
        return null;
    }

    private EbaySearchItem getListItem(Document html) {
        return null;
    }

    private Document parse(String html) {
        return Jsoup.parse(html);
    }
}
