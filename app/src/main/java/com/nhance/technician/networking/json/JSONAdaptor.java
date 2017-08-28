package com.nhance.technician.networking.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.nhance.technician.app.NhanceApplication;
import com.nhance.technician.exception.NhanceException;
import com.nhance.technician.logger.LOG;
import com.nhance.technician.networking.sec.EncryptionProvider;

import org.bouncycastle.util.encoders.Base64;

import java.lang.reflect.Type;
import java.util.Date;

public class JSONAdaptor {
	private static Gson gson;
	private static EncryptionProvider mEncryptionProvider;

	public static void register(EncryptionProvider encryptionProvider) {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gson = gsonBuilder.create();
		mEncryptionProvider = encryptionProvider;
	}
	public static void register() {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gson = gsonBuilder.create();
	}
	public static <T> String toJSON(T t) throws NhanceException {
		if (mEncryptionProvider != null) {

			String json = gson.toJson(t, t.getClass());
			LOG.d("JSONAdaptor"," toJSON : JSON Data"+json);
			byte[] encPaylod = mEncryptionProvider.encryptPayLoad(gson.toJson(t, t.getClass()).getBytes());
			if (NhanceApplication.getApplication().isEncryptionRequired()) {

				String base64Encoded = new String(Base64.encode(encPaylod));
				return base64Encoded;

			} else {

				return new String(encPaylod);
			}

		}
		return gson.toJson(t, t.getClass());
	}

	public static <T> T fromJSON(String jsonStr, Class<T> c) throws JsonSyntaxException, NhanceException {
		if (mEncryptionProvider != null) {
			byte[] decPaylod = null;
			if (NhanceApplication.getApplication().isEncryptionRequired()) {
				decPaylod = mEncryptionProvider.decryptPayLoad(Base64.decode(jsonStr));
			} else {
				decPaylod = mEncryptionProvider.decryptPayLoad(jsonStr.getBytes());
			}

			return gson.fromJson(new String(decPaylod), c);
		}
		return gson.fromJson(jsonStr, c);
	}

	private static class DateSerializer implements JsonSerializer<Date> {
		@Override
		public JsonElement serialize(Date date, Type type, JsonSerializationContext context) {
			return new JsonPrimitive(date.getTime());
		}
	}

	private static class DateDeserializer implements JsonDeserializer<Date> {

		@Override
		public Date deserialize(JsonElement ele, Type type, JsonDeserializationContext context) throws JsonParseException {
			String dateStr = ele.getAsString();
			if (dateStr == null || dateStr.trim().length() == 0) {
				return null;
			}
			return new Date(Long.parseLong(dateStr));
		}
	}
}
