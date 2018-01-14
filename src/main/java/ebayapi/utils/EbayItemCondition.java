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

    public static EbayItemCondition parse(String term) {
        switch (term) {
            case "Neu":
            case "Brandneu":
                return NEW;
            case "Neuwertig":
            case "Neu (Sonstige)":
                return NEW_OTHER;
            case "Generalüberholt":
                return RENEWED_BY_MANUFACTURER;
            case "Sehr gut":
            case "Gut":
            case "Akzeptabel":
            case "Gebraucht":
                return USED;
            case "Nur Ersatzteile":
                return DEFECTIVE;
            default: return UNKNOWN;
        }
    }

}
