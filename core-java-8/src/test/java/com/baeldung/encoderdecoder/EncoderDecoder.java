package com.baeldung.encoderdecoder;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class EncoderDecoder {

    @Test
    public void givenRequestParam_whenUTF8Scheme_thenEncode(){
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("key1", "value 1");
        requestParams.put("key2", "value@!$2");
        requestParams.put("key3", "value%3");

        StringBuilder requestParamsString = new StringBuilder();
        requestParams.forEach((key, value) -> {
            try {
                requestParamsString
                        .append(key)
                        .append("=")
                        .append(URLEncoder.encode(value,
                                StandardCharsets.UTF_8.toString()))
                        .append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        String encodedURL = "http://www.baeldung.com?" +
                requestParamsString
                        .substring(0,
                                requestParamsString.length()-1);

        Assert.assertThat(
                "http://www.baeldung.com?key1=value+1&key2=value%40%21%242" +
                        "&key3=value%253", CoreMatchers.is(encodedURL));
    }

    @Test
    public void givenRequestParam_whenUTF8Scheme_thenDecodeRequestParams() throws UnsupportedEncodingException {
        String encodedURL = "http://www.baeldung.com?key1=value+1"+
                "&key2=value%40%21%242&key3=value%253" ;
        StringBuilder requestParamsString =
                new StringBuilder("http://www.baeldung.com?");

        String encodedRequestParamsString =
                encodedURL.substring(encodedURL.indexOf("?") + 1);
        String keyValuePairs[] = encodedRequestParamsString.split("&");
        for(String keyValuePair: keyValuePairs) {
            String keyAndValue[] = keyValuePair.split("=");
            requestParamsString
                    .append(keyAndValue[0])
                    .append("=")
                    .append(URLDecoder.decode(keyAndValue[1],
                            StandardCharsets.UTF_8.toString()))
                    .append("&");
        }
        Assert.assertEquals("http://www.baeldung.com?key1=value 1"+
                        "&key2=value@!$2&key3=value%3",
                requestParamsString.substring(0,
                        requestParamsString.length()-1));
    }

    @Test
    public void givenEncodedURL_whenWrongScheme_thenDecode() throws UnsupportedEncodingException {
        String encodedURL = "http://www.baeldung.com?key1=value+1"+
                "&key2=value%40%21%242&key3=value%253" ;
        StringBuilder requestParamsString =
                new StringBuilder("http://www.baeldung.com?");

        String encodedRequestParamsString =
                encodedURL.substring(encodedURL.indexOf("?") + 1);
        String keyValuePairs[] = encodedRequestParamsString.split("&");
        for(String keyValuePair: keyValuePairs) {
            String keyAndValue[] = keyValuePair.split("=");
            requestParamsString
                    .append(keyAndValue[0])
                    .append("=")
                    .append(URLDecoder.decode(keyAndValue[1],
                            StandardCharsets.UTF_16.toString()))
                    .append("&");
        }
        Assert.assertFalse(("http://www.baeldung.com?key1=value 1"+
                "&key2=value@!$2&key3=value%3")
                        .equals(requestParamsString));
    }
}
