package ebayapi.requests;

public class EbaySuggestionRequest extends EbayRequest {

    private String keyword;

    public EbaySuggestionRequest(String keyword) {
        this.params.put("sId", "77");
        setKeyword(keyword);
    }

    public String getKeyword() {
        return keyword;
    }

    public EbaySuggestionRequest setKeyword(String keyword) {
        this.keyword = keyword;
        this.params.put("kwd", keyword);
        return this;
    }

    @Override
    protected String baseUrl() {
        return "https://autosug.ebaystatic.com";
    }

    @Override
    protected String path() {
        return "/autosug";
    }
}
