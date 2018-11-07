package ebayapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class EbayDetailItem extends EbaySearchItem {

    private EbaySeller seller;

    private List<String> paymentMethods;

    private int sold;

    private int soldLastDay;

    private int available;

    private int clicksPerHour;

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

    public int getSoldLastDay() {
        return soldLastDay;
    }

    public void setSoldLastDay(int soldLastDay) {
        this.soldLastDay = soldLastDay;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getClicksPerHour() {
        return clicksPerHour;
    }

    public void setClicksPerHour(int clicksPerHour) {
        this.clicksPerHour = clicksPerHour;
    }

    @Override
    @JsonIgnore
    public boolean isNewly() {
        return super.isNewly();
    }
}
