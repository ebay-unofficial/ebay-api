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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EbayDetailParser {

    @Autowired
    private EbayHttpService httpService;

    private static final String DECIMAL_PATTERN = "(\\d+([.,]\\d+)?)";

    public EbayDetailItem parseDetailItem(String id) {
        Document document = Jsoup.parse(httpService.httpGet("/itm/" + id + "?orig_cvip=true"));

        EbayDetailItem item = new EbayDetailItem(id);

        item.setSeller(parseSeller(document));

        document.getElementById("itemTitle").children().remove();
        item.setTitle(document.getElementById("itemTitle").text());

        item.setCondition(EbayItemCondition.parse(document.getElementById("vi-itm-cond").text()));

        if (document.getElementById("prcIsum_bidPrice") != null) {
            item.setAuction(true);
            item.setPrice(Double.parseDouble(document.getElementById("prcIsum_bidPrice").attr("content")));
        }
        if (document.getElementById("prcIsum") != null) {
            item.setBuyNow(true);
            item.setPrice(Double.parseDouble(document.getElementById("prcIsum").attr("content")));
        }

        item.setCurrency(document.getElementsByAttributeValue("itemprop", "priceCurrency").first().attr("content"));

        Matcher m = Pattern.compile(DECIMAL_PATTERN).matcher(document.getElementById("fshippingCost").text());
        item.setShipping(m.find() ? Double.parseDouble(m.group(1).replace(',', '.')) : 0);

        if (document.getElementsByClass("pd-img") != null) {
            item.addPaymentMethod("Pay-Pal");
        }
        document.getElementsByClass("hideGspPymt").forEach(element -> {
            element.children().remove();
            item.addPaymentMethod(element.text());
        });

        Element jsdf = document.select("#JSDF").first();
        if (jsdf != null) {
            String imagePattern = "maxImageUrl.*?\\\\u002F((\\w)\\\\u002F(.*?))\\\\u002F.*?Height.*?(\\d+).*?Width.*?(\\d+)";
            Matcher matcher = Pattern.compile(imagePattern).matcher(jsdf.html());
            while (matcher.find()) {
                EbayItemImage ebayItemImage = new EbayItemImage(matcher.group(3));
                ebayItemImage.setType(matcher.group(2));
                ebayItemImage.setHeight(Integer.valueOf(matcher.group(4)));
                ebayItemImage.setWidth(Integer.valueOf(matcher.group(5)));
                item.addImage(ebayItemImage);
            }
        }

        return item;
    }

    public EbaySeller parseSeller(Document document) {
        EbaySeller seller = new EbaySeller();

        seller.setName(document.getElementsByClass("mbg-nw").first().text());
        String feedback = document.getElementById("si-fb").text();
        Matcher m = Pattern.compile(DECIMAL_PATTERN).matcher(feedback);
        seller.setFeedback(m.find() ? Double.parseDouble(m.group(1).replace(',', '.')) : -1);
        seller.setStars(Integer.valueOf(document.getElementsByClass("mbg-l").first().child(0).text()));

        return seller;
    }

}
