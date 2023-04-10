package com.alurkerja.core.util;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class HttpUtil {
    private static final Logger LOGGER = Logger.getLogger(HttpUtil.class.getName());

    private static void prepareHeaders(HttpUriRequest request, Map<String, String> headers) throws Exception {
        if (headers != null) {
            for (String header : headers.keySet()) {
                request.addHeader(header, headers.get(header));
            }
        }
    }

    private static byte[] execute(HttpUriRequest request) throws Exception {

        try (CloseableHttpClient client = HttpClients.createDefault(); CloseableHttpResponse response = client.execute(request)) {
            HttpEntity responseBody = response.getEntity();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            responseBody.writeTo(baos);
            EntityUtils.consume(responseBody);
            byte[] responseByte = baos.toByteArray();
            baos.close();

            if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201) {
                throw new Exception(response.getStatusLine().getStatusCode() + " | " + response.getStatusLine().getReasonPhrase() + " | " + new String(responseByte));
            }

            return responseByte;
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            throw e;
        }
    }

    public static byte[] get(String url, Map<String, String> headers) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(5 * 60 * 1000)
                .setConnectionRequestTimeout(5 * 60 * 1000)
                .setSocketTimeout(5 * 60 * 1000).build();
        httpGet.setConfig(config);
        // header
        prepareHeaders(httpGet, headers);
        return execute(httpGet);
    }

    public static byte[] postForm(String url, Map<String, String> headers, Map<String, String> formData, Map<String, InputStream> streams) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(5 * 60 * 1000)
                .setConnectionRequestTimeout(5 * 60 * 1000)
                .setSocketTimeout(5 * 60 * 1000).build();
        httpPost.setConfig(config);
        // header
        prepareHeaders(httpPost, headers);
        // params
        if (streams != null && !streams.isEmpty()) {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            for (String paramName : streams.keySet()) {
                InputStream stream = streams.get(paramName);
                builder.addBinaryBody(paramName, stream);
            }
            if (formData != null) {
                for (String paramName : formData.keySet()) {
                    builder.addTextBody(paramName, formData.get(paramName));
                }
            }
            HttpEntity multipart = builder.build();
            httpPost.setEntity(multipart);
        } else if (formData != null) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (String paramName : formData.keySet()) {
                String paramValue = formData.get(paramName);
                params.add(new BasicNameValuePair(paramName, paramValue));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params));
        }

        return execute(httpPost);
    }

    public static byte[] postBody(String url, Map<String, String> headers, String bodyText) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(5 * 60 * 1000)
                .setConnectionRequestTimeout(5 * 60 * 1000)
                .setSocketTimeout(5 * 60 * 1000).build();
        httpPost.setConfig(config);
        // header
        prepareHeaders(httpPost, headers);
        // body
        HttpEntity body = new StringEntity(bodyText);
        httpPost.setEntity(body);

        return execute(httpPost);
    }
}
