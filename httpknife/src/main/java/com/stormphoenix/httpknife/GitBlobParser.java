package com.stormphoenix.httpknife;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.stream.JsonReader;
import com.stormphoenix.httpknife.github.GitBlob;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by StormPhoenix on 17-3-4.
 * StormPhoenix is a intelligent Android developer.
 */

public class GitBlobParser implements JsonDeserializer<GitBlob> {
    public static final String TAG = GitBlobParser.class.getSimpleName();
    private Gson gson = null;

    public GitBlobParser() {
        this.gson = new GsonBuilder().create();
    }

    @Override
    public GitBlob deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Log.e(TAG, "deserialize: ");
        Log.e(TAG, "deserialize: toString " + json.toString());
        JsonReader reader = new JsonTreeReader(json);
        try {
            while (reader.hasNext()) {
                System.out.println(reader.nextString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.fromJson(json, GitBlob.class);
    }
}
