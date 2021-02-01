package com.njeport.track.common.bean;

import com.njeport.track.common.enums.ContentType;
import lombok.Getter;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.springframework.http.HttpMethod;

import java.util.List;

/**
 * @author kongming
 * @date 2020/04/04
 */
@Getter
public class HttpParams {
    private final String url;
    private final List<NameValuePair> params;
    private final HttpMethod method;
    private final ContentType contentType;
    private final String body;
    private final List<Header> headers;

    private HttpParams(Builder builder) {
        this.url = builder.url;
        this.params = builder.params;
        this.method = builder.method;
        this.contentType = builder.contentType;
        this.body = builder.body;
        this.headers = builder.headers;
    }

    public static class Builder {
        private String url;
        private List<NameValuePair> params;
        private HttpMethod method;
        private ContentType contentType;
        private String body;
        private List<Header> headers;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder params(List<NameValuePair> params) {
            this.params = params;
            return this;
        }

        public Builder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public Builder contentType(ContentType contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder headers(List<Header> headers) {
            this.headers = headers;
            return this;
        }

        public HttpParams build() {
            return new HttpParams(this);
        }
    }
}
