package com.reactnativega;

import android.content.Context;
import android.app.Activity;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class GoogleAnalyticsModule extends ReactContextBaseJavaModule {
  public Activity mActivity = null;
  public Context mContext = null;
  public Tracker tracker = null;

  @Override
  public String getName() {
    return "GoogleAnalyticsModule";
  }

  public GoogleAnalyticsModule(ReactApplicationContext reactContext, Activity activity) {
    super(reactContext);
    this.mContext = reactContext;
    this.mActivity = activity;
  }

  @ReactMethod
  public void startTracker(String id, Boolean enableExceptionReporting, Callback callback, Callback errorCallback) {
    if (id != null && id.length() > 0) {
        tracker = GoogleAnalytics.getInstance(mActivity).newTracker(id);
        GoogleAnalytics.getInstance(mActivity).setLocalDispatchPeriod(15);
        tracker.enableExceptionReporting(enableExceptionReporting);
        callback.invoke();
    } else {
        errorCallback.invoke();
    }
  }

  @ReactMethod
  public void trackView(String appName, String screenName, Callback callback, Callback errorCallback) {
    if (tracker == null) {
      errorCallback.invoke("Tracker has not been started yet. Run startTracker('UA-XXXXXXX') first!");
      return;
    }

    if (screenName != null && screenName.length() > 0) {
      tracker.setScreenName(screenName);

      if (appName != null && appName.length() > 0) {
        tracker.setAppName(appName);
      }

      HitBuilders.AppViewBuilder hitBuilder = new HitBuilders.AppViewBuilder();

      tracker.send(hitBuilder.build());
      callback.invoke("Screen Tracked: " + screenName);
    } else {
      errorCallback.invoke("The screen name must not be blank.");
    }
  }

  @ReactMethod
  public void trackEvent(String category, String action, String label, int value, Callback callback, Callback errorCallback) {
    if (tracker == null) {
      errorCallback.invoke("Tracker has not been started yet. Run startTracker('UA-XXXXXXX') first!");
      return;
    }

    if (category != null && category.length() > 0) {
      HitBuilders.EventBuilder hitBuilder = new HitBuilders.EventBuilder();

      hitBuilder
        .setCategory(category)
        .setAction(action)
        .setLabel(label)
        .setValue(value);

      tracker.send(hitBuilder.build());
      callback.invoke("Event Tracked: " + category);
    } else {
      errorCallback.invoke("The category must not be blank.");
    }
  }

  @ReactMethod
  public void trackException(String description, Boolean fatal, Callback callback, Callback errorCallback) {
    if (tracker == null) {
      errorCallback.invoke("Tracker has not been started yet. Run startTracker('UA-XXXXXXX') first!");
      return;
    }

    if (description != null && description.length() > 0) {
      HitBuilders.ExceptionBuilder hitBuilder = new HitBuilders.ExceptionBuilder();

      hitBuilder
        .setDescription(description)
        .setFatal(fatal);

      tracker.send(hitBuilder.build());
      callback.invoke("Exception Tracked: " + description);
    } else {
      errorCallback.invoke("The description must not be blank.");
    }
  }

  @ReactMethod
  public void trackTiming(String category, int intervalInMillis, String name, String label, Callback callback, Callback errorCallback) {
    if (tracker == null) {
      errorCallback.invoke("Tracker has not been started yet. Run startTracker('UA-XXXXXXX') first!");
      return;
    }

    if (category != null && category.length() > 0) {
      HitBuilders.TimingBuilder hitBuilder = new HitBuilders.TimingBuilder();

      hitBuilder
        .setCategory(category)
        .setValue(intervalInMillis)
        .setVariable(name)
        .setLabel(label);

      tracker.send(hitBuilder.build());
      callback.invoke("Timing Tracked: " + category);
    } else {
      errorCallback.invoke("The category must not be blank.");
    }
  }

  @ReactMethod
  public void setUserID(String userID, Callback callback, Callback errorCallback) {
    if (tracker == null) {
      errorCallback.invoke("Tracker has not been started yet. Run startTracker('UA-XXXXXXX') first!");
      return;
    }

    tracker.set("&uid", userID);
    callback.invoke("User-ID Set: " + userID);
  }

  @ReactMethod
  public void isGooglePlayServicesAvailable(Callback callback) {
    GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
    int availability = googleAPI.isGooglePlayServicesAvailable(mContext);

    if (availability == ConnectionResult.SUCCESS) {
      callback.invoke(true);
    } else if (availability == ConnectionResult.SIGN_IN_REQUIRED) {
      callback.invoke(true);
    } else if (availability == ConnectionResult.SIGN_IN_FAILED) {
      callback.invoke(true);
    } else if (availability == ConnectionResult.  SERVICE_VERSION_UPDATE_REQUIRED) {
      callback.invoke(true);
    } else if (availability == ConnectionResult.INVALID_ACCOUNT) {
      callback.invoke(true);
    } else {
      callback.invoke(false);
    }
  }
}
