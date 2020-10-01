package com.passninja.net;

import java.net.Proxy;
import java.util.Objects;

import com.passninja.Passninja;

public class RequestOptions {
    public static RequestOptions getDefault() {
        return new RequestOptions(Passninja.accountId, Passninja.apiKey, null);
    }

    private final String accountId;
    private final String apiKey;
    private final Proxy proxy;

    private RequestOptions(String accountId, String apiKey, Proxy proxy) {
        this.accountId = accountId;
        this.apiKey = apiKey;
        this.proxy = proxy;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public Proxy getProxy() {
        return proxy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof RequestOptions) {
            RequestOptions that = (RequestOptions) o;
            
            if (!Objects.equals(this.accountId, that.getAccountId())) {
                return false;
            }
            
            if (!Objects.equals(this.apiKey, that.getApiKey())) {
                return false;
            }
    
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(accountId, apiKey);
    }    

    public static final class Builder {
        private String accountId;
        private String apiKey;
        private Proxy proxy;

        public Builder() {
            this.accountId = Passninja.accountId;
            this.apiKey = Passninja.apiKey;
        }

        public String getAccountId() {
            return this.accountId;
        }

        public Builder setAccountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public String getApiKey() {
            return this.apiKey;
        }

        public Builder setApiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder setProxy(Proxy proxy) {
            this.proxy = proxy;
            return this;
        }
        
        public Proxy getProxy() {
            return this.proxy;
        }

        public RequestOptions build() {
            return new RequestOptions(this.accountId, this.apiKey,
                  this.proxy);
        }
    }

}
