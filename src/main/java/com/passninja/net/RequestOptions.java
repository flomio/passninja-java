package com.passninja.net;

import java.net.Proxy;
import java.util.Objects;

import com.passninja.Passninja;

public class RequestOptions {
    public static RequestOptions getDefault() {
        return new RequestOptions(Passninja.accountId, Passninja.apiKey,
              null, null);
    }

    private final String accountId;
    private final String apiKey;
    private final String idempotencyKey;
    private final Proxy proxy;

    private RequestOptions(String accountId, String apiKey, String idempotencyKey, Proxy proxy) {
        this.accountId = accountId;
        this.apiKey = apiKey;
        this.idempotencyKey = idempotencyKey;
        this.proxy = proxy;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
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
    
            if (!Objects.equals(this.idempotencyKey, that.getIdempotencyKey())) {
                return false;
            }       
            
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(accountId, apiKey, idempotencyKey);
    }    

    public static final class Builder {
        private String accountId;
        private String apiKey;
        private String idempotencyKey;
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

        public Builder setIdempotencyKey(String idempotencyKey) {
            this.idempotencyKey = idempotencyKey;
            return this;
        }

        public String getIdempotencyKey() {
            return this.idempotencyKey;
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
                  this.idempotencyKey, this.proxy);
        }
    }

}
