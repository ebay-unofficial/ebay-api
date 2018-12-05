package ebayapi.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


public abstract class EbayRequest {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    protected Map<String, String> params = new LinkedHashMap<>();

    protected abstract String baseUrl();

    protected abstract String path();

    @Override
    public String toString() {
        String query = params.entrySet().stream().map(Object::toString).collect(Collectors.joining("&"));
        return baseUrl() + path() + "?" + query;
    }

}
