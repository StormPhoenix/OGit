package com.stormphoenix.ogit.cache;

import android.support.annotation.NonNull;

import com.stormphoenix.ogit.OGitApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import static com.stormphoenix.ogit.cache.FileCache.CacheType.ORG_EVENTS;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.ORG_LIST;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.ORG_MEMBERS;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.REPO_CONTRIBUTORS;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.REPO_TREE;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.TREND_DEVELOPER;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.TREND_REPOS_All_Lang;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.TREND_REPOS_Css;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.TREND_REPOS_Go;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.TREND_REPOS_Html;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.TREND_REPOS_Java;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.TREND_REPOS_JavaScript;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.TREND_REPOS_Objective_C;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.TREND_REPOS_Python;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.TREND_REPOS_Swift;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.USER_COMMITS;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.USER_FOLLERINGS;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.USER_FOLLERS;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.USER_PERFORMED_EVENT;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.USER_RECEIVED_EVENT;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.USER_REPOSITORIES;
import static com.stormphoenix.ogit.cache.FileCache.CacheType.USER_STARED_REPOS;

/**
 * Created by StormPhoenix on 17-4-17.
 * StormPhoenix is a intelligent Android developer.
 */

public class FileCache {

    public enum CacheType {
        USER_PERFORMED_EVENT,
        USER_RECEIVED_EVENT,
        USER_REPOSITORIES,
        USER_STARED_REPOS,
        USER_FOLLERS,
        USER_FOLLERINGS,
        USER_COMMITS,
        ORG_EVENTS,
        ORG_MEMBERS,
        REPO_CONTRIBUTORS,
        ORG_LIST,
        REPO_TREE,
        TREND_REPOS_All_Lang,
        TREND_REPOS_JavaScript,
        TREND_REPOS_Java,
        TREND_REPOS_Go,
        TREND_REPOS_Css,
        TREND_REPOS_Objective_C,
        TREND_REPOS_Python,
        TREND_REPOS_Swift,
        TREND_REPOS_Html,
        TREND_DEVELOPER
    }

    private static final HashMap<CacheType, String> typeNameMap = new HashMap<>();

    static {
        typeNameMap.put(USER_PERFORMED_EVENT, "user_performed_event.json");
        typeNameMap.put(ORG_LIST, "org_list.json");
        typeNameMap.put(REPO_TREE, "repo_tree.json");
        typeNameMap.put(TREND_REPOS_All_Lang, "trend_repos_all_lang.json");
        typeNameMap.put(TREND_REPOS_JavaScript, "trend_repos_javascript.json");
        typeNameMap.put(TREND_REPOS_Java, "trend_repos_java.json");
        typeNameMap.put(TREND_REPOS_Go, "trend_repos_go.json");
        typeNameMap.put(TREND_REPOS_Css, "trend_repos_css.json");
        typeNameMap.put(TREND_REPOS_Objective_C, "trend_repos_objective_c.json");
        typeNameMap.put(TREND_REPOS_Python, "trend_repos_python.json");
        typeNameMap.put(TREND_REPOS_Swift, "trend_repos_swift.json");
        typeNameMap.put(TREND_REPOS_Html, "trend_repos_html.json");
        typeNameMap.put(TREND_DEVELOPER, "trend_developer.json");
        typeNameMap.put(USER_RECEIVED_EVENT, "user_received_event.json");
        typeNameMap.put(USER_REPOSITORIES, "user_repos.json");
        typeNameMap.put(USER_STARED_REPOS, "user_stared_repos.json");
        typeNameMap.put(USER_FOLLERS, "user_follers.json");
        typeNameMap.put(USER_FOLLERINGS, "user_follering.json");
        typeNameMap.put(USER_COMMITS, "user_commits.json");
        typeNameMap.put(REPO_CONTRIBUTORS, "repo_contributors.json");
        typeNameMap.put(ORG_EVENTS, "org_events.json");
        typeNameMap.put(ORG_MEMBERS, "org_members.json");
    }

    public static String getCachedFile(CacheType cacheType) {
        String filename = getFileName(cacheType);
        File cachedFile = new File(OGitApplication.instance.getCacheDir(), filename);
        if (!cachedFile.exists()) {
            return null;
        } else {
            return getCharFormatFileContent(cachedFile);
        }
    }

    @NonNull
    private static String getCharFormatFileContent(File cachedFile) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(cachedFile), "utf-8"));
            String temp;
            while ((temp = reader.readLine()) != null) {
                builder.append(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return builder.toString();
        }
    }

    public static void saveCacheFile(CacheType cacheType, String content) {
        if (cacheType == null) {
            return;
        }
        String filename = getFileName(cacheType);
        File cachedFile = new File(OGitApplication.instance.getCacheDir(), filename);
        saveCharFormatFile(content, cachedFile);
    }

    private static String getFileName(CacheType cacheType) {
        return typeNameMap.get(cacheType);
    }

    private static void saveCharFormatFile(String charsContent, File cachedFile) {
        try {
            if (!cachedFile.exists()) {
                cachedFile.getParentFile().mkdir();
                cachedFile.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cachedFile), "utf-8"));
            writer.write(charsContent);
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
