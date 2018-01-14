package ebayapi.utils;

public enum EbayItemCondition {

    NEW("Brandneu"),
    NEW_OTHER("Neu (Sonstige)"),
    RENEWED_BY_MANUFACTURER("Generalüberholt"),
    RENEWED_BY_SELLER("Generalüberholt"),
    USED("Gebraucht"),
    DEFECTIVE("Nur Ersatzteile"),
    UNKNOWN("");

    public String name;


    EbayItemCondition(String name) {
        this.name = name;
    }
}
