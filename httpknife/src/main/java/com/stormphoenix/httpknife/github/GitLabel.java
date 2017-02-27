package com.stormphoenix.httpknife.github;

import java.io.Serializable;

/**
 * Created by Quinn on 10/4/15.
 */
public class GitLabel implements Serializable {
    private static final long serialVersionUID = 105838111015760693L;

    private String color;
    private String name;
    private String url;

    public GitLabel() {
    }

    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        } else if(!(obj instanceof GitLabel)) {
            return false;
        } else {
            String name = this.name;
            return name != null && name.equals(((GitLabel)obj).name);
        }
    }

    public int hashCode() {
        String name = this.name;
        return name != null?name.hashCode():super.hashCode();
    }

    public String toString() {
        String name = this.name;
        return name != null?name:super.toString();
    }

    public String getColor() {
        return this.color;
    }

    public GitLabel setColor(String color) {
        this.color = color;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public GitLabel setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public GitLabel setUrl(String url) {
        this.url = url;
        return this;
    }
}
