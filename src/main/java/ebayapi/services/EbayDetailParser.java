package ebayapi.services;

import ebayapi.models.EbayDetailItem;
import ebayapi.models.EbayItemImage;
import ebayapi.models.EbaySeller;
import ebayapi.utils.EbayAuctionType;
import ebayapi.utils.EbayItemCondition;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
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

    public EbayDetailItem parseDetailItem(String id) {
        Document document = Jsoup.parse(httpService.httpGet("/itm/" + id + "?orig_cvip=true"));

        EbayDetailItem item = new EbayDetailItem(id);

        item.setSeller(parseSeller(document));
        item.setTitle(parseTitle(document));
        item.setCondition(parseCondition(document));
        item.setCurrency(parseCurrency(document));
        item.setShipping(parseShipping(document));
        item.setAuction(isAuction(document));
        item.setBuyNow(isBuyNow(document));
        item.setPrice(parsePrice(document));
        item.setPaymentMethods(parsePaymentMethods(document));
        if (isPayPal(document)) {
            item.addPaymentMethod("Pay-Pal");
        }
        item.setImages(parseImages(document));

        return item;
    }

    private EbaySeller parseSeller(Document document) {
        EbaySeller seller = new EbaySeller();

        seller.setName(document.getElementsByClass("mbg-nw").first().text());
        String feedback = document.getElementById("si-fb").text();
        Matcher m = Pattern.compile(DECIMAL_PATTERN).matcher(feedback);
        seller.setFeedback(m.find() ? Double.parseDouble(m.group(1).replace(',', '.')) : -1);
        seller.setStars(Integer.valueOf(document.getElementsByClass("mbg-l").first().child(0).text()));

        return seller;
    }

    private String parseTitle(Document document) {
        document.getElementById("itemTitle").children().remove();
        return document.getElementById("itemTitle").text();
    }

    private EbayItemCondition parseCondition(Document document) {
        return EbayItemCondition.parse(document.getElementById("vi-itm-cond").text());
    }

    private String parseCurrency(Document document) {
        return document.getElementsByAttributeValue("itemprop", "priceCurrency").first().attr("content");
    }

    private double parsePrice(Document document) {
        if (isBuyNow(document)) {
            return Double.parseDouble(document.getElementById("prcIsum").attr("content"));
        } else if (isAuction(document)){
            return Double.parseDouble(document.getElementById("prcIsum_bidPrice").attr("content"));
        }
        return -1;
    }

    private double parseShipping(Document document) {
        Matcher m = Pattern.compile(DECIMAL_PATTERN).matcher(document.getElementById("fshippingCost").text());
        return m.find() ? Double.parseDouble(m.group(1).replace(',', '.')) : 0;
    }

    private List<String> parsePaymentMethods(Document document) {
        List<String> paymentMethods = new ArrayList<>();
        document.getElementsByClass("hideGspPymt").forEach(element -> {
            element.children().remove();
            if (!element.text().equals("")) {
                paymentMethods.add(element.text());
            }
        });
        return paymentMethods;
    }

    private List<EbayItemImage> parseImages(Document document) {
        List<EbayItemImage> images = new ArrayList<>();
        Element jsdf = document.select("#JSDF").first();
        Matcher matcher = Pattern.compile(IMAGE_PATTERN).matcher(jsdf.html());
        while (matcher.find()) {
            EbayItemImage ebayItemImage = new EbayItemImage(matcher.group(3));
            ebayItemImage.setType(matcher.group(2));
            ebayItemImage.setHeight(Integer.valueOf(matcher.group(4)));
            ebayItemImage.setWidth(Integer.valueOf(matcher.group(5)));
            images.add(ebayItemImage);
        }
        return images;
    }

    private boolean isPayPal(Document document) {
        return document.getElementsByClass("pd-img") != null;
    }

    private boolean isAuction(Document document) {
        return document.getElementById("prcIsum_bidPrice") != null;
    }

    private boolean isBuyNow(Document document) {
        return document.getElementById("prcIsum") != null;
    }

}

