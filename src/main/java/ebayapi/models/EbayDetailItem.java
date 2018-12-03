package ebayapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

public class EbayDetailItem extends EbaySearchItem {

    private EbaySeller seller;

    private List<String> paymentMethods;

    private int sold;

    private double soldPercentage = -1;

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

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public double getSoldPercentage() {
        if (soldPercentage == 0 && available > 0) {
            return Math.round(1000 * (1 - ((double) available / sold))) / 10.0;
        }
        return soldPercentage;
    }

    public void setSoldPercentage(double soldPercentage) {
        this.soldPercentage = soldPercentage;
    }

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int getSoldLastDay() {
        return soldLastDay;
    }

    public void setSoldLastDay(int soldLastDay) {
        this.soldLastDay = soldLastDay;
    }

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
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
