package ebayapi.services;

import ebayapi.models.EbayDetailItem;
import ebayapi.models.EbayItemImage;
import ebayapi.models.EbaySeller;
import ebayapi.utils.EbayItemCondition;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EbayDetailParser {

    @Autowired
    private EbayHttpService httpService;

    private static final String DECIMAL_PATTERN = "(\\d+([.,]\\d+)?)";

    private static final String IMAGE_PATTERN = "maxImageUrl.*?\\\\u002F((\\w)\\\\u002F(.*?))\\\\u002F" +
            ".*?Height.*?(\\d+).*?Width.*?(\\d+)";

    private static final String SOLD_PATTERN = "((\\d+[.,])*\\d+)\\sverkauft";

    private static final String SOLD_PERCENTAGE_PATTERN = "(\\d+)%\\sverkauft";

    private static final String AVAILABLE_PATTERN = "((\\d+[.,])*\\d+)\\sverfügbar";

    private static final String AVAILABLE_LAST_PATTERN = "Letzter Artikel";

    private static final String CLICKS_PER_HOUR_PATTERN = "((\\d+[.,])*\\d+)\\sMal\\spro\\sStunde";

    public EbayDetailItem parseDetailItem(String id) {
        Document document = Jsoup.parse(httpService.httpGet("/itm/" + id + "?orig_cvip=true"));

        EbayDetailItem item = new EbayDetailItem(id);

        item.setSeller(parseSeller(document));
        item.setTitle(parseTitle(document));
        item.setCondition(parseCondition(document));

        item.setPrice(parsePrice(document));
        item.setShipping(parseShipping(document));
        item.setCurrency(parseCurrency(document));

        item.setAuction(isAuction(document));
        item.setBuyNow(isBuyNow(document));
        item.setPriceRange(isPriceRange(document));
        item.setEbayPlus(isEbayPlus(document));

        item.setPaymentMethods(parsePaymentMethods(document));
        item.setSold(parseSold(document));
        item.setSoldPercentage(parseSoldPercentage(document));
        item.setAvailable(parseAvailable(document));
        item.setSoldLastDay(parseSoldLastDay(document));
        item.setClicksPerHour(parseClicksPerHour(document));

        item.setImages(parseImages(document));

        return item;
    }

    private EbaySeller parseSeller(Element element) {
        EbaySeller seller = new EbaySeller();

        seller.setName(element.getElementsByClass("mbg-nw").first().text());
        String feedback = element.getElementById("si-fb").text();
        Matcher m = Pattern.compile(DECIMAL_PATTERN).matcher(feedback);
        seller.setFeedback(m.find() ? Double.parseDouble(m.group(1).replace(',', '.')) : -1);
        seller.setStars(Integer.valueOf(element.getElementsByClass("mbg-l").first().child(0).text()));

        return seller;
    }

    private String parseTitle(Element element) {
        element.getElementById("itemTitle").children().remove();
        return element.getElementById("itemTitle").text();
    }

    private EbayItemCondition parseCondition(Element element) {
        return EbayItemCondition.parse(element.getElementById("vi-itm-cond").text());
    }

    private String parseCurrency(Element element) {
        return element.getElementsByAttributeValue("itemprop", "priceCurrency").first().attr("content");
    }

    private double parsePrice(Element element) {
        if (isBuyNow(element)) {
            return Double.parseDouble(element.getElementById("prcIsum").attr("content"));
        } else if (isAuction(element)) {
            return Double.parseDouble(element.getElementById("prcIsum_bidPrice").attr("content"));
        }
        return -1;
    }

    private double parseShipping(Element element) {
        Element shippingElement = element.getElementById("fshippingCost");
        if (shippingElement != null) {
            Matcher m = Pattern.compile(DECIMAL_PATTERN).matcher(shippingElement.text());
            return m.find() ? Double.parseDouble(m.group(1).replace(',', '.')) : 0;
        }
        return 0;
    }

    private List<String> parsePaymentMethods(Element element) {
        List<String> paymentMethods = new ArrayList<>();
        element.getElementsByClass("hideGspPymt").forEach(payment -> {
            payment.children().remove();
            if (!payment.text().equals("")) {
                paymentMethods.add(payment.text());
            }
        });
        if (isPayPal(element)) {
            paymentMethods.add("Pay-Pal");
        }
        return paymentMethods;
    }

    private List<EbayItemImage> parseImages(Element element) {
        List<EbayItemImage> images = new ArrayList<>();
        Element jsdf = element.select("#JSDF").first();
        Matcher matcher = Pattern.compile(IMAGE_PATTERN).matcher(jsdf.html());
        while (matcher.find()) {
            EbayItemImage ebayItemImage = new EbayItemImage(matcher.group(3));
            ebayItemImage.setType(matcher.group(2));
            ebayItemImage.setHeight(Integer.valueOf(matcher.group(4)));
            ebayItemImage.setWidth(Integer.valueOf(matcher.group(5)));
            if (!images.contains(ebayItemImage)) {
                images.add(ebayItemImage);
            }
        }
        return images;
    }

    private int parseSold(Element element) {
        Element why2buyElement = element.getElementById("why2buy");
        if (why2buyElement != null) {
            Matcher matcher = Pattern.compile(SOLD_PATTERN).matcher(why2buyElement.text());
            if (matcher.find()) {
                return Integer.valueOf(matcher.group(1).replaceAll("[.,]", ""));
            }
        }
        return 0;
    }

    private double parseSoldPercentage(Element element) {
        Element why2buyElement = element.getElementById("why2buy");
        if (why2buyElement != null) {
            Matcher matcher = Pattern.compile(SOLD_PERCENTAGE_PATTERN).matcher(why2buyElement.text());
            if (matcher.find()) {
                return Double.valueOf(matcher.group(1).replaceAll("[.,]", ""));
            }
        }
        return 0;
    }

    private int parseSoldLastDay(Element element) {
        Element why2buyElement = element.getElementById("vi_notification_new");
        if (why2buyElement != null) {
            Matcher matcher = Pattern.compile(SOLD_PATTERN).matcher(why2buyElement.text());
            if (matcher.find()) {
                return Integer.valueOf(matcher.group(1).replaceAll("[.,]", ""));
            }
        }
        return 0;
    }

    private int parseClicksPerHour(Element element) {
        Element notificationElement = element.getElementById("vi_notification_new");
        if (notificationElement != null) {
            Matcher matcher = Pattern.compile(CLICKS_PER_HOUR_PATTERN).matcher(notificationElement.text());
            if (matcher.find()) {
                return Integer.valueOf(matcher.group(1).replaceAll("[.,]", ""));
            }
        }
        return 0;
    }

    private int parseAvailable(Element element) {
        Element availableElement = element.getElementById("qtySubTxt");
        if (availableElement != null) {
            Matcher matcher = Pattern.compile(AVAILABLE_PATTERN).matcher(availableElement.text());
            if (matcher.find()) {
                return Integer.valueOf(matcher.group(1).replaceAll("[.,]", ""));
            }
            matcher = Pattern.compile(AVAILABLE_LAST_PATTERN).matcher(availableElement.text());
            if (matcher.find()) {
                return 1;
            }
        }
        return 0;
    }

    private boolean isPayPal(Element element) {
        return !element.getElementsByClass("pd-img").isEmpty();
    }

    private boolean isAuction(Element element) {
        return element.getElementById("prcIsum_bidPrice") != null;
    }

    private boolean isBuyNow(Element element) {
        return element.getElementById("prcIsum") != null;
    }

    private boolean isPriceRange(Element element) {
        return element.getElementById("msku-sel-1") != null;
    }

    private boolean isEbayPlus(Element element) {
        return !element.getElementsByClass("sh-eplus-img").isEmpty();
    }

}

