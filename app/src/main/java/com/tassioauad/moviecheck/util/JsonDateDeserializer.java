package com.tassioauad.moviecheck.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JsonDateDeserializer implements JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.toString().equals("")) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy'-'MM'-'dd", Locale.getDefault());
            try {
                return simpleDateFormat.parse(json.toString().replace("\"", ""));
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }
}

