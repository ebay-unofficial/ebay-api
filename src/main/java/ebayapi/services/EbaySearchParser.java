package ebayapi.services;

import ebayapi.models.EbayItemImage;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EbaySearchParser {

    private static final String DECIMAL_PATTERN = "(\\d+([.,]\\d+)?)";

    @Autowired
    EbayHttpService httpService;

    public EbaySearchResult getSearch(EbaySearchRequest request) {
        return getSearch(httpService.httpGet(request));
    }

    public EbaySearchResult getSearch(String html) {
        Document parsedHtml = parse(html);
        List<EbaySearchItem> searchItems = getSearchItems(parsedHtml);
        EbaySearchResult result = new EbaySearchResult();
        result.setUrl(httpService.getLastRequest());
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

        String priceText = e.getElementsByClass("lvprice").text();
        Matcher priceMatcher = Pattern.compile(DECIMAL_PATTERN).matcher(priceText);
        if (priceMatcher.find()) {
            String rawNumber = priceMatcher.group(1).replace(",", ".");
            double price = Double.parseDouble(rawNumber);
            item.setPrice(price);
        }

        String feeText = e.getElementsByClass("fee").text();
        Matcher feeMatcher = Pattern.compile(DECIMAL_PATTERN).matcher(feeText);
        if (feeMatcher.find()) {
            String rawNumber = feeMatcher.group(1).replace(",", ".");
            double fee = Double.parseDouble(rawNumber);
            item.setShipping(fee);
        }

        Element currencyElement = e.select("li.lvprice > span b").first();
        String currency = currencyElement.text();
        item.setCurrency(currency);

        Element imageSrcElement = e.select("img[src*=thumbs]").first();
        Element imageUrlElement = e.select("img[imgurl*=thumbs]").first();
        if (imageSrcElement != null) {
            Matcher matcher = Pattern.compile("/((g|m)/(.*))/").matcher(imageSrcElement.attr("src"));
            if (matcher.find()) {
                item.addImage(new EbayItemImage(matcher.group(1)));
            }
        } else if (imageUrlElement != null) {
            Matcher matcher = Pattern.compile("/((g|m)/(.*))/").matcher(imageUrlElement.attr("imgurl"));
            if (matcher.find()) {
                item.addImage(new EbayItemImage(matcher.group(1)));
            }
        }

        return item;
    }

    private Document parse(String html) {
        return Jsoup.parse(html);
    }
}
