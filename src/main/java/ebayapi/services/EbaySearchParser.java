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

    private static final String IMAGE_PATTERN = "/((\\w)/(.*))/";

    @Autowired
    EbayHttpService httpService;

    public EbaySearchResult getSearch(EbaySearchRequest request) {
        return getSearch(httpService.httpGet(request));
    }

    public EbaySearchResult getSearch(String html) {
        EbaySearchResult result = new EbaySearchResult();

        result.setUrl(httpService.getLastRequest());
        result.setItems(parseSearchItems(Jsoup.parse(html)));
        result.setAds(parseSearchAds(Jsoup.parse(html)));

        return result;
    }

    private List<EbaySearchItem> parseSearchItems(Document html) {
        ArrayList<EbaySearchItem> items = new ArrayList<>();

        Elements elements = html.getElementById("ListViewInner").select("li[listingid]");
        items.add(parseListItem(elements.first()));
        elements.forEach(element -> {
            if (!element.attr("r").equals("1")) {
                items.add(parseListItem(element));
            }
        });

        return items;
    }

    private List<EbaySearchItem> parseSearchAds(Document html) {
        ArrayList<EbaySearchItem> items = new ArrayList<>();

        final boolean[] skipFirst = { true };
        html.getElementById("ListViewInner").select("li[r=1]").forEach(element -> {
            if (skipFirst[0]) {
                skipFirst[0] = false;
            } else {
                items.add(parseListItem(element));
            }
        });

        return items;
    }

    private EbaySearchItem parseListItem(Element element) {
        EbaySearchItem item = new EbaySearchItem();

        item.setId(parseId(element));
        item.setTitle(parseTitle(element));
        item.setCondition(parseItemCondition(element));

        item.setAuction(isAuction(element));
        item.setBuyNow(isBuyNow(element));
        item.setSuggestPrice(isSuggestPrice(element));
        item.setPriceRange(isPriceRange(element));
        item.setEbayPlus(isEbayPlus(element));

        item.setPrice(parsePrice(element));
        item.setShipping(parseShipping(element));
        item.setCurrency(parseCurrency(element));

        item.setImages(parseImages(element));

        return item;
    }

    private String parseId(Element element) {
        return element.getElementsByAttribute("iid").attr("iid");
    }

    private String parseTitle(Element element) {
        return element.select("h3 > a").text();
    }

    private EbayItemCondition parseItemCondition(Element element) {
        Element lvsubtitle = element.getElementsByClass("lvsubtitle").last();
        String conditionText = lvsubtitle != null ? lvsubtitle.text().toLowerCase() : "";

        for (EbayItemCondition itemCondition : EbayItemCondition.values()) {
            if (conditionText.contains(itemCondition.name.toLowerCase())) {
                return itemCondition;
            }
        }
        return EbayItemCondition.UNKNOWN;
    }

    private double parsePrice(Element element) {
        Matcher matcher = Pattern.compile(DECIMAL_PATTERN).matcher(element.getElementsByClass("lvprice").text());
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1).replace(",", "."));
        }
        return -1;
    }

    private double parseShipping(Element element) {
        Matcher matcher = Pattern.compile(DECIMAL_PATTERN).matcher(element.getElementsByClass("fee").text());
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1).replace(",", "."));
        }
        return 0;
    }

    private String parseCurrency(Element element) {
        return element.select("li.lvprice > span b").first().text();
    }

    private List<EbayItemImage> parseImages(Element element) {
        List<EbayItemImage> images = new ArrayList<>();

        Element imageSrcElement = element.select("img[src*=thumbs]").first();
        Element imageUrlElement = element.select("img[imgurl*=thumbs]").first();
        if (imageSrcElement != null) {
            images.add(parseImage(imageSrcElement));
        } else if (imageUrlElement != null) {
            images.add(parseImage(imageUrlElement));
        }

        return images;
    }

    private EbayItemImage parseImage(Element element) {
        String url = element.attr("imgurl").equals("") ? element.attr("src") : element.attr("imgurl");
        Matcher matcher = Pattern.compile(IMAGE_PATTERN).matcher(url);
        if (matcher.find()) {
            EbayItemImage ebayItemImage = new EbayItemImage(matcher.group(3));
            ebayItemImage.setType(matcher.group(2));
            return ebayItemImage;
        }
        return null;
    }

    private boolean isAuction(Element element) {
        String format = element.getElementsByClass("lvformat").text().toLowerCase();
        return format.contains("gebot");
    }

    private boolean isBuyNow(Element element) {
        String format = element.getElementsByClass("lvformat").text().toLowerCase();
        return format.contains("sofort-kauf") || format.contains("preisvorschlag");
    }

    private boolean isSuggestPrice(Element element) {
        String format = element.getElementsByClass("lvformat").text().toLowerCase();
        return format.contains("preisvorschlag");
    }

    private boolean isPriceRange(Element element) {
        return parseImages(element).get(0).getType().equals("m");
    }

    private boolean isEbayPlus(Element element) {
        return !element.getElementsByClass("eplus-icon").isEmpty();
    }

}
