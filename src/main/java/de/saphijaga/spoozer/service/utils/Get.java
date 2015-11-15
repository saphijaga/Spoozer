package de.saphijaga.spoozer.service.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static de.saphijaga.spoozer.service.utils.HttpUtils.buildConnection;
import static de.saphijaga.spoozer.service.utils.HttpUtils.streamToObject;
import static de.saphijaga.spoozer.service.utils.HttpUtils.streamToString;

/**
 * Created by samuel on 13.11.15.
 */
public class Get {
    public static <T> T forObject(String url, Class<T> responseObjectClass) throws IOException {
        return forObject(url, new HashMap<>(), responseObjectClass);
    }
    public static <T> T forObject(String url, Map<String, String> header, Class<T> responseObjectClass) throws IOException {
        return streamToObject(forStream(url, header), responseObjectClass);
    }

    public static String forString(String url) throws IOException {
        return forString(url, new HashMap<>());
    }

    public static String forString(String url, Map<String, String> header) throws IOException {
        return streamToString(forStream(url, header));
    }

    public static InputStream forStream(String url) throws IOException {
        return forStream(url, new HashMap<>());
    }

    public static InputStream forStream(String url, Map<String, String> header) throws IOException {
        return buildConnection(url, "GET", header).getInputStream();
    }
}