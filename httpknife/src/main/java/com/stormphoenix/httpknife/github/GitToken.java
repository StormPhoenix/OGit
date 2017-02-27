package com.stormphoenix.httpknife.github;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by StormPhoenix on 17-2-26.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitToken {
    private int id;
    private String url;
    private List<String> scopes;
    private String token;
    @SerializedName("hashed_token")
    private String hashedToken;
    @SerializedName("token_last_eight")
    private String tokenLastEight;
    private String note;
    @SerializedName("note_url")
    private String noteUrl;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    private String fingerprint;
    @SerializedName("client_secret")
    private String clientSecret;
    private App app;

    public String getNoteUrl() {
        return noteUrl;
    }

    public void setNoteUrl(String noteUrl) {
        this.noteUrl = noteUrl;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getHashedToken() {
        return hashedToken;
    }

    public void setHashedToken(String hashedToken) {
        this.hashedToken = hashedToken;
    }

    public String getTokenLastEight() {
        return tokenLastEight;
    }

    public void setTokenLastEight(String tokenLastEight) {
        this.tokenLastEight = tokenLastEight;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    private static class App {
        private String name;
        private String url;
        @SerializedName("client_id")
        private String clientId;

        public App() {

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "app [name=" + name + ", url=" + url + ", client_id="
                    + clientId + "]";
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scropes) {
        this.scopes = scropes;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatedAtTime() {
        return createdAt;
    }

    public String getUpdatedAtTime() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAtTime) {
        this.updatedAt = updatedAtTime;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    @Override
    public String toString() {
        return "Token [id=" + id + ", url=" + url + ", scopes=" + scopes
                + ", token=" + token + ", hashed_token=" + hashedToken
                + ", token_last_eight=" + tokenLastEight + ", note=" + note
                + ", note_url=" + noteUrl + ", created_at=" + createdAt
                + ", updated_at=" + updatedAt + ", fingerprint=" + fingerprint
                + ", app=" + app + "]";
    }
}
