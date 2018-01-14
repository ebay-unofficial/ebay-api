package ebayapi.services;

import ebayapi.models.EbayDetailItem;
import ebayapi.models.EbaySeller;
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

    public EbayDetailItem parseDetailItem(String id) {
        Document document = Jsoup.parse(httpService.httpGet("/itm/" + id));

        EbayDetailItem item = new EbayDetailItem(id);

        item.setSeller(parseSeller(document));

        return item;
    }

    public EbaySeller parseSeller(Document document) {
        EbaySeller seller = new EbaySeller();

        seller.setName(document.getElementsByClass("mbg-nw").first().text());
        String feedback = document.getElementById("si-fb").text();
        Matcher m = Pattern.compile("(\\d+([.,]\\d+)?)%.*").matcher(feedback);
        seller.setFeedback(m.find() ? Double.parseDouble(m.group(1).replace(',', '.')) : -1);
        seller.setStars(Integer.valueOf(document.getElementsByClass("mbg-l").first().child(0).text()));

        return seller;
    }

}
