package com.noah.apache.httpclient;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.ConnectException;

@Slf4j
public class NoahHttpUtils {

    public static final Integer TIME_OUT = 1000;

    public static void main(String[] args) {
        String result = NoahHttpUtils.retryPost("http://localhost:8080/apache/retry");
        log.info(result);
    }

    /**
     * 支持重试的post请求
     *
     * @param uri
     * @return
     */
    @SneakyThrows
    public static String retryPost(String uri) {

        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {

            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                System.out.println("开始第" + executionCount + "次重试！");
                if (executionCount > 3) {
                    System.out.println("重试次数大于3次，不再重试");
                    return false;
                }
                if (exception instanceof ConnectTimeoutException || exception instanceof ConnectException) {
                    System.out.println("连接超时，准备进行重新请求....");
                    return true;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();

                //只有get请求才支持呢
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    return true;
                }
                return false;
            }
        };

        HttpPost post = new HttpPost(uri);

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(TIME_OUT)
                .setConnectionRequestTimeout(TIME_OUT)
                .setSocketTimeout(TIME_OUT).build();

        post.setConfig(config);

        String resultContent = null;
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;

        try {

            client = HttpClients.custom().setRetryHandler(httpRequestRetryHandler).build();
            response = client.execute(post, HttpClientContext.create());

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                resultContent = EntityUtils.toString(response.getEntity(), Consts.UTF_8.name());
            }

        } catch (IOException e) {
            log.error("retryPost error", e);
        } finally {
            if (response != null) {
                response.close();
            }
            if (client != null) {
                client.close();
            }

        }
        return resultContent;
    }
}
