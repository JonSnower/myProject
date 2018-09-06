package com.hey.java.other;

import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class JsonUtils {
	public final static boolean isJSONValid(String json) {
		if (StringUtils.isBlank(json)) {
			return false;
		}
		try {
			JSONObject.parseObject(json); 
		} catch (JSONException ex) {
			try {
				JSONObject.parseArray(json);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}
}
