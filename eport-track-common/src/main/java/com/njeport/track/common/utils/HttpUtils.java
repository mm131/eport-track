package com.njeport.track.common.utils;

import com.google.common.collect.Lists;
import com.njeport.track.common.bean.HttpParams;
import com.njeport.track.common.constants.StringConstants;
import com.njeport.track.common.enums.ContentType;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kongming
 * @date 2020/01/04
 */
@SuppressWarnings("all")
public class HttpUtils {

    public static final String USER_AGENT_BROWSER = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36";
    public static final String USER_AGENT_ANDROID = "Mozilla/5.0 (Linux; Android 5.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.94 Mobile Safari/537.36";

    /**
     * 构建 Http 头部
     *
     * @param contentType content type
     * @return http headers
     */
    private static List<Header> getHeaders(ContentType contentType) {
        List<Header> headers = Lists.newArrayList();
        headers.add(new BasicHeader("User-Agent", USER_AGENT_BROWSER));
        headers.add(new BasicHeader("Content-Type", contentType.getValue()));
        headers.add(new BasicHeader("Connection", "keep-alive"));
        headers.add(new BasicHeader("Cache-Control", "no-cache"));
        headers.add(new BasicHeader("Accept-Encoding", "gzip, deflate, sdch"));
        headers.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,fr;q=0.4,de;q=0.2"));
        headers.add(new BasicHeader("Pragma", "no-cache"));
        return headers;
    }

    @Getter
    @Setter
    public static class HttpResponse {
        private String result;
        private Integer statusCode;
        private Header[] headers;
        private byte[] binary;
    }

    /**
     * 发送 Http 请求
     *
     * @param httpParams Http请求参数
     * @return response content
     */
    public static HttpResponse sendHttpRequest(HttpParams httpParams) throws Exception {
        CloseableHttpClient httpclient = buildSSLCloseableHttpClient();
        CloseableHttpResponse response = null;
        HttpResponse retVal = new HttpResponse();
        String result = null;
        byte[] binary = null;
        String url = httpParams.getUrl();

        if (!CollectionUtils.isEmpty(httpParams.getParams())) {
            url = url + StringConstants.QUESTION + URLEncodedUtils.format(httpParams.getParams(), StringConstants.UTF8);
        }

        List<Header> headers = getHeaders(httpParams.getContentType());
        if (httpParams.getHeaders() != null) {
            headers.addAll(httpParams.getHeaders());
        }
        Header[] headerArr = new Header[headers.size()];
        headers.toArray(headerArr);

        try {
            if (HttpMethod.POST == httpParams.getMethod()) {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setHeaders(headerArr);
                httpPost.setEntity(new StringEntity(StringUtils.getValidString(httpParams.getBody()), StandardCharsets.UTF_8));
                response = httpclient.execute(httpPost);
            } else {
                HttpGet httpGet = new HttpGet(url);
                httpGet.setHeaders(headerArr);
                response = httpclient.execute(httpGet);
            }
            retVal.setHeaders(response.getAllHeaders());
            retVal.setStatusCode(response.getStatusLine().getStatusCode());
            try {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK
                        || response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY) {
                    HttpEntity entity = response.getEntity();
                    if (ContentType.IMAGE_JPEG == httpParams.getContentType() || ContentType.IMAGE_PNG == httpParams.getContentType()) {
                        binary = readInputStream(entity.getContent());
                    } else {
                        result = EntityUtils.toString(entity);
                    }
                }
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        retVal.setResult(result);
        retVal.setBinary(binary);
        return retVal;
    }

    /**
     * 将输入流的内容写入到字节数组
     *
     * @param inStream 输入流
     */
    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 构建 CloseableHttpClient
     */
    private static CloseableHttpClient buildSSLCloseableHttpClient() throws Exception {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1"}, null,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

    
    public static String doPost(String url, Map<String, String> param, String cookie) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Cookie", cookie);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }
}
