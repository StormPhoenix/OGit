package com.stormphoenix.httpknife.github;

/**
 * Created by StormPhoenix on 17-3-2.
 * StormPhoenix is a intelligent Android developer.
 *
 * GitTreeItem 封装了一个Repository中不同文件或者文件夹的种类
 * 使用时，要根据Type和Mode类型的不同来确认GitTreeItem代表的是
 * 何种类型文件。
 */

public class GitTreeItem {
    /**
     * mode 类型：
     * 100644 file (blob)
     * 100755 executable (blob)
     * 040000 subdirectory (tree)
     * 160000 submodule (commit)
     * 100755 file (blob)
     * 120000 a blob that specifies the path of a symlink
     * <p>
     * type 类型：
     * blob
     * tree
     * commit
     */
    public static final String TYPE_BLOB = "blob";
    public static final String TYPE_TREE = "tree";
    public static final String TYPE_COMMIT = "commit";

    public static final String MODE_BLOB = "100644";
    public static final String MODE_EXECUTABLE = "100755";
    public static final String MODE_SUBDIRECTORY = "040000";
    public static final String MODE_SUBMODULE = "160000";
    public static final String MODE_SYMLINK = "120000";

    private String path;
    private String mode;
    private String type;
    private String sha;
    private long size;
    private String url;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
