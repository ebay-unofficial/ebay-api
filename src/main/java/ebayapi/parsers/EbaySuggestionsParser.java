package ebayapi.parsers;

import ebayapi.requests.EbaySuggestionRequest;
import ebayapi.results.EbaySuggestionResult;
import org.springframework.stereotype.Service;


@Service
public class EbaySuggestionsParser extends EbayParser {

    public EbaySuggestionResult parse(EbaySuggestionRequest request) {
        String result = httpService.httpGet(request);
        System.out.println(result);
        return new EbaySuggestionResult();
    }
}
