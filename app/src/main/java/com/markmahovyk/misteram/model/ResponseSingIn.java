package com.markmahovyk.misteram.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseSingIn{
        @SerializedName("accountToken")
        @Expose
        private String accountToken;
        @SerializedName("authToken")
        @Expose
        private String authToken;

        public String getAccountToken() {
                return accountToken;
        }

        public void setAccountToken(String accountToken) {
                this.accountToken = accountToken;
        }

        public String getAuthToken() {
                return authToken;
        }

        public void setAuthToken(String authToken) {
                this.authToken = authToken;
        }
}
