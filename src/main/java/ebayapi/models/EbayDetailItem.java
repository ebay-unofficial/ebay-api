package ebayapi.models;

import java.util.ArrayList;
import java.util.List;

public class EbayDetailItem extends EbaySearchItem {

    private EbaySeller seller;
    private List<String> paymentMethods;

    public EbayDetailItem(String id) {
        this.id = id;
        this.paymentMethods = new ArrayList<>();
    }

    public EbaySeller getSeller() {
        return seller;
    }

    public void setSeller(EbaySeller seller) {
        this.seller = seller;
    }

    public List<String> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<String> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public void addPaymentMethod(String paymentMethod) {
        if (!paymentMethod.isEmpty()) {
            this.paymentMethods.add(paymentMethod);
        }
    }
}
