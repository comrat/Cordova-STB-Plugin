package org.apache.cordova.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * * This class provides methods for STB STB.
 * */
public class STB extends CordovaPlugin {

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (action.equals("refreshToken")) {
			String message = args.getString(0);
			this.refreshToken(message, callbackContext);
			return true;
		} else if (action.equals("checkActionAndGetToken")) {
			String actionName = args.getString(0);
			String token = args.getString(1);
			this.checkActionAndGetToken(actionName, token, callbackContext);
			return true;
		}
		return false;
	}

	private void checkActionAndGetToken(String action, String tokenName, CallbackContext callbackContext) {
		Intent intent = this.cordova.getActivity().getIntent();
		Log.d("CordovaSTBPlugin", "checkActiion: " + action + ", tokenName: " + tokenName);
		try {
			String token = intent.getStringExtra(tokenName);
			Log.d("CordovaSTBPlugin", "Get token:" + token);
			if (action.equals(intent.getAction())) {
				Log.d("CordovaSTBPlugin", "action is equal, return token" + token);
				callbackContext.success(token);
			} else {
				Log.d("CordovaSTBPlugin", "action is not equal");
				callbackContext.success("");
			}
		} catch(ActivityNotFoundException e) {
			callbackContext.error("Failed to get action");
		}
	}

	private void refreshToken(String tokenName, CallbackContext callbackContext) {
		Intent intent = this.cordova.getActivity().getIntent();
		Log.d("CordovaSTBPlugin", "refreshToken " + tokenName);
		try {
			String token = intent.getStringExtra(tokenName);
			Log.d("CordovaSTBPlugin", "Got token:" + token);
			callbackContext.success(token);
		} catch(ActivityNotFoundException e) {
			callbackContext.error("Failed to refreshToken");
		}
	}

}
