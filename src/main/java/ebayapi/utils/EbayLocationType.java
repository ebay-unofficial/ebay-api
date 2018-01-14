package ebayapi.utils;

public enum EbayLocationType {

    EBAY(0),
    REGION(1),
    CONTINENTAL(2),
    GLOBAL(3);

    Integer id;

    EbayLocationType(Integer i) {
        id = i;
    }
}
