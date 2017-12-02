package org.apache.cordova.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ActivityNotFoundException;
import android.content.Intent;


/**
 * * This class provides methods for TTK STB.
 * */
public class TTK extends CordovaPlugin {

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (action.equals("returnToMainApp")) {
			String message = args.getString(0);
			this.returnToMainApp(message, callbackContext);
			return true;
		} else if (action.equals("refreshToken")) {
			String message = args.getString(0);
			this.refreshToken(message, callbackContext);
			return true;
		}
		return false;
	}

	private void returnToMainApp(String action, CallbackContext callbackContext) {
		Intent intent = new Intent();
		intent.setAction(action);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
			this.cordova.getActivity().startActivity(intent);
			callbackContext.success("");
		} catch (ActivityNotFoundException e) {
			callbackContext.error("Failed to returnToMainApp");
		}
	}

	private void refreshToken(String action, CallbackContext callbackContext) {
		try {
			if (getIntent() != null) {
				String token = getIntent().getStringExtra("tv.lfstrm.ttktv.extra_token");
				callbackContext.success(token);
			}
		} catch(ActivityNotFoundException e) {
			callbackContext.error("Failed to refreshToken");
		}
	}

}
