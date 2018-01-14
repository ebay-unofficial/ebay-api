package ebayapi.utils;

public enum EbayOrderType {

    ENDING_SOONEST(1),
    FEWEST_BIDS(4),
    MOST_BIDS(5),
    NEAREST_DISTANCE(7),
    NEWLY_LISTED(10),
    BEST_MATCH(12),
    LOWEST_PRICE_FIRST(15),
    HIGHEST_PRICE_FIRST(16);

    Integer id;

    EbayOrderType(Integer i) {
        id = i;
    }
}
