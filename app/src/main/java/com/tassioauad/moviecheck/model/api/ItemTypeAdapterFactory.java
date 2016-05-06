package com.tassioauad.moviecheck.model.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemTypeAdapterFactory implements TypeAdapterFactory {

    private List<String> rootNameList = Arrays.asList("results");

    public ItemTypeAdapterFactory() {
    }

    public ItemTypeAdapterFactory(String rootName) {
        rootNameList = new ArrayList<>();
        rootNameList.add(rootName);
    }

    public ItemTypeAdapterFactory(List<String> rootNameList) {
        this.rootNameList = rootNameList;
    }

    public <T> TypeAdapter<T> create(Gson gson, final TypeToken<T> type) {

        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

        return new TypeAdapter<T>() {

            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            public T read(JsonReader in) throws IOException {

                JsonElement jsonElement = elementAdapter.read(in);
                JsonElement newJsonElement = null;
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    for (int i = 0; i < rootNameList.size(); i++) {
                        if (jsonObject.has(rootNameList.get(i))) {
                            if(newJsonElement == null) {
                                newJsonElement = jsonObject.get(rootNameList.get(i));
                            } else {
                                ((JsonArray) newJsonElement).addAll(jsonObject.getAsJsonArray(rootNameList.get(i)));
                            }
                        }
                    }
                }

                return delegate.fromJsonTree(newJsonElement != null ? newJsonElement : jsonElement );
            }
        }.nullSafe();
    }
}
