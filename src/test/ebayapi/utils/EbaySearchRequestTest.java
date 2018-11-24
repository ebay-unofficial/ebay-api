package ebayapi.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EbaySearchRequestTest {

  @Test
  public void testMapParams() {
    EbaySearchRequest request = new EbaySearchRequest("Pokémon")
        .isAuctions(false)
        .isBuyNow(true)
        .isSold(false)
        .preferLocation("ebay")
        .withZip("12345")
        .searchInDescription(false)
        .orderBy("newly")
        .limit(20)
        .page(2);
    assertEquals(
        "/sch/i.html?_nkw=Pok%C3%A9mon&LH_Auction=0&LH_BIN=1&LH_Sold=0&LH_Complete=0&LH_PrefLoc=0"
            + "&_stpos=12345&_localstpos=12345&LH_TitleDes=0&_sop=10&_ipg=20&_pgn=2",
        request.toString());
  }

  @Test
  public void testDuplicateParams() {
    EbaySearchRequest request = new EbaySearchRequest("hello")
        .limit(25)
        .limit(50);
    assertEquals("/sch/i.html?_nkw=hello&_ipg=50", request.toString());
  }
}
