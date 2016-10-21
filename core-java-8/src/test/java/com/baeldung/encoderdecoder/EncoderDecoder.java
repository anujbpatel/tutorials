package com.baeldung.encoderdecoder;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EncoderDecoder {

    public static final String URL = "http://www.baeldung.com?key1=value+1&key2=value%40%21%242" +
            "&key3=value%253";

    @Test
    public void givenURL_whenAnalyze_thenCorrect() throws Exception {

        URL aURL = new URL(URL);

        System.out.println("protocol = " + aURL.getProtocol());
        System.out.println("authority = " + aURL.getAuthority());
        System.out.println("host = " + aURL.getHost());
        System.out.println("port = " + aURL.getPort());
        System.out.println("path = " + aURL.getPath());
        System.out.println("query = " + aURL.getQuery());
        System.out.println("filename = " + aURL.getFile());
        System.out.println("ref = " + aURL.getRef());
    }

    @Test
    public void givenRequestParam_whenUTF8Scheme_thenEncode() throws Exception {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("key1", "value 1");
        requestParams.put("key2", "value@!$2");
        requestParams.put("key3", "value%3");

        String encodedQuery = requestParams.keySet().stream()
          .map(key -> key + "=" + encodeValue(requestParams.get(key)))
          .collect(Collectors.joining("&"));
        String encodedURL = "http://www.baeldung.com?" +
                encodedQuery;

        Assert.assertThat(
                URL, CoreMatchers.is(encodedURL));
    }

    private String encodeValue(String value) {
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encoded;
    }

    @Test
    public void givenRequestParam_whenUTF8Scheme_thenDecodeRequestParams() throws UnsupportedEncodingException {
        String encodedURL = "http://www.baeldung.com?key1=value+1" +
                "&key2=value%40%21%242&key3=value%253";
        StringBuilder requestParamsString =
                new StringBuilder("http://www.baeldung.com?");

        String encodedRequestParamsString =
                encodedURL.substring(encodedURL.indexOf("?") + 1);
        String keyValuePairs[] = encodedRequestParamsString.split("&");
        for (String keyValuePair : keyValuePairs) {
            String keyAndValue[] = keyValuePair.split("=");
            requestParamsString
                    .append(keyAndValue[0])
                    .append("=")
                    .append(URLDecoder.decode(keyAndValue[1],
                            StandardCharsets.UTF_8.toString()))
                    .append("&");
        }
        Assert.assertEquals("http://www.baeldung.com?key1=value 1" +
                        "&key2=value@!$2&key3=value%3",
                requestParamsString.substring(0,
                        requestParamsString.length() - 1));
    }

    @Test
    public void givenEncodedURL_whenWrongScheme_thenDecode() throws UnsupportedEncodingException {
        String encodedURL = "http://www.baeldung.com?key1=value+1" +
                "&key2=value%40%21%242&key3=value%253";
        StringBuilder requestParamsString =
                new StringBuilder("http://www.baeldung.com?");

        String encodedRequestParamsString =
                encodedURL.substring(encodedURL.indexOf("?") + 1);
        String keyValuePairs[] = encodedRequestParamsString.split("&");
        for (String keyValuePair : keyValuePairs) {
            String keyAndValue[] = keyValuePair.split("=");
            requestParamsString
                    .append(keyAndValue[0])
                    .append("=")
                    .append(URLDecoder.decode(keyAndValue[1],
                            StandardCharsets.UTF_16.toString()))
                    .append("&");
        }
        Assert.assertFalse(("http://www.baeldung.com?key1=value 1" +
                "&key2=value@!$2&key3=value%3")
                .equals(requestParamsString));
    }
}
