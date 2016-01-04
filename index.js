'use strict';

var React = require('react-native');
var GoogleAnalyticsModule = require('react-native').NativeModules.GoogleAnalyticsModule;

// Warp the native module so we can do some pre/post processing to have a cleaner API.
var GoogleAnalytics = {
  startTracker: function(id, enableExceptionReporting = true, callback, errorCallback) {
    return new Promise(function(resolve, reject) {
      if (!id || id.length <= 0) {
        console.error('GoogleAnalytics: startTracker: Error: Tracking ID must not be blank.');
        if (errorCallback) errorCallback();
        reject();
        return;
      }

      GoogleAnalyticsModule.startTracker(id, enableExceptionReporting, (successMessage) => {
        if (callback) callback(successMessage);
        resolve(successMessage);
      }, (errorMessage) => {
        if (errorCallback) errorCallback(errorMessage);
        reject(errorMessage);
      });
    });
  },

  trackView: function(appName = '', screenName = 'default', callback, errorCallback) {
    return new Promise(function(resolve, reject) {
      GoogleAnalyticsModule.trackView(appName, screenName, (successMessage) => {
        if (callback) callback(successMessage);
        resolve(successMessage);
      }, (errorMessage) => {
        if (errorCallback) errorCallback(errorMessage);
        reject(errorMessage);
      });
    });
  },

  trackEvent: function(category = 'default', action = 'default', label = 'default', value = 0, callback, errorCallback) {
    return new Promise(function(resolve, reject) {
      GoogleAnalyticsModule.trackEvent(category, action, label, value, (successMessage) => {
        if (callback) callback(successMessage);
        resolve(successMessage);
      }, (errorMessage) => {
        if (errorCallback) errorCallback(errorMessage);
        reject(errorMessage);
      });
    });
  },

  trackException: function(description = 'default', fatal = false, callback, errorCallback) {
    return new Promise(function(resolve, reject) {
      GoogleAnalyticsModule.trackException(description, fatal, (successMessage) => {
        if (callback) callback(successMessage);
        resolve(successMessage);
      }, (errorMessage) => {
        if (errorCallback) errorCallback(errorMessage);
        reject(errorMessage);
      });
    });
  },

  trackTiming: function(category = 'default', intervalInMillis = 0, name = 'default', label = 'default', callback, errorCallback) {
    return new Promise(function(resolve, reject) {
      GoogleAnalyticsModule.trackTiming(category, intervalInMillis, name, label, (successMessage) => {
        if (callback) callback(successMessage);
        resolve(successMessage);
      }, (errorMessage) => {
        if (errorCallback) errorCallback(errorMessage);
        reject(errorMessage);
      });
    });
  },

  setUserID: function(userID = 'default', callback, errorCallback) {
    return new Promise(function(resolve, reject) {
      GoogleAnalyticsModule.setUserID(userID, (successMessage) => {
        if (callback) callback(successMessage);
        resolve(successMessage);
      }, (errorMessage) => {
        if (errorCallback) errorCallback(errorMessage);
        reject(errorMessage);
      });
    });
  },

  isGooglePlayServicesAvailable: function(callback) {
    return new Promise(function(resolve, reject) {
      GoogleAnalyticsModule.isGooglePlayServicesAvailable((availability) => {
        if (callback) callback(availability);
        resolve(availability);
      });
    });
  },

  nativeModule: GoogleAnalyticsModule
}

module.exports = GoogleAnalytics;
