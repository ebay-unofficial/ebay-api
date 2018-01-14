package ebayapi.services;

import ebayapi.models.EbayDetailItem;
import ebayapi.models.EbaySeller;
import ebayapi.utils.EbayAuctionType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
        Document document = Jsoup.parse(httpService.httpGet("/itm/" + id));

        EbayDetailItem item = new EbayDetailItem(id);

        item.setSeller(parseSeller(document));
        item.setTitle(document.getElementById("itemTitle").text());
        // item.setCondition(document.getElementById("vi-itm-cond").text());

        if (document.getElementById("bidBtn_btn") != null) {
            item.setAuctionType(EbayAuctionType.AUCTION);
            item.setPrice(Double.parseDouble(document.getElementById("prcIsum_bidPrice").attr("content")));
        }
        if (document.getElementById("binBtn_btn") != null) {
            item.setAuctionType(EbayAuctionType.BUY_NOW);
            item.setPrice(Double.parseDouble(document.getElementById("prcIsum").attr("content")));
        }

        item.setCurrency(document.getElementsByAttributeValue("itemprop", "priceCurrency").first().attr("content"));

        Matcher m = Pattern.compile(DECIMAL_PATTERN).matcher(document.getElementById("fshippingCost").text());
        item.setShipping(m.find() ? Double.parseDouble(m.group(1).replace(',', '.')) : 0);

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
