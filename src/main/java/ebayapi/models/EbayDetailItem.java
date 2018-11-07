package ebayapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class EbayDetailItem extends EbaySearchItem {

    private EbaySeller seller;

    private List<String> paymentMethods;

    private int sold;

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

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    @Override
    @JsonIgnore
    public boolean isNewly() {
        return super.isNewly();
    }
}
