# react-native-google-analytics-native [![npm version](https://img.shields.io/npm/v/react-native-google-analytics-native.svg?style=flat-square)](https://www.npmjs.com/package/react-native-google-analytics-native)

- [ ] iOS
- [x] Android

Native Google Analytics for React Native. Using the Universal Analytics SDK, you can get native-level details (that are not accessible by JavaScript) while receiving analytics data, e.g.: device brand, screen resolution, OS version and more.


## Installation

### Android

- Run `npm install react-native-google-analytics-native --save` to install using npm.

- Add the following two lines to `android/settings.gradle`:

```gradle
include ':react-native-google-analytics-native'
project(':react-native-google-analytics-native').projectDir = new File(settingsDir, '../node_modules/react-native-google-analytics-native/android')
```

- Edit `android/app/build.gradle` and add the annoated lines as below:

```gradle
...

dependencies {
    compile fileTree(dir: "libs", include: ["*.jar"])
    compile "com.android.support:appcompat-v7:23.0.1"
    compile "com.facebook.react:react-native:0.15.+"
    compile project(':react-native-google-analytics-native')  // <- Add this line
}
```

- Edit `MainActivity.java` (usually at `android/app/src/main/java/com/<project-name>/MainActivity.java`) and add the annoated lines as below:

```java
import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

import com.reactnativega.GoogleAnalyticsPackage;                   // <- Add this line

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {

...

        mReactRootView = new ReactRootView(this);

        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())
                .addPackage(new GoogleAnalyticsPackage(this))      // <- Add this line
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();

...
```


## Usage

```js
import GoogleAnalytics from 'react-native-google-analytics-native';

// Start the tracker before tracking anything!
var enableExceptionReporting = true;
GoogleAnalytics.startTracker('UA-60031864-15', enableExceptionReporting);

// Track a Screen (PageView):
GoogleAnalytics.trackView('App Name', 'View Title');

// Track an Event:
var value = 100;
GoogleAnalytics.trackEvent('Category', 'Action', 'Label', value);

// Track an Exception:
var isFatal = false;
GoogleAnalytics.trackException('Some Bad Exception', isFatal);

// Track User Timing (App Speed):
var timeInMilliseconde = 1000
GoogleAnalytics.trackTiming('Category', timeInMilliseconde, 'Name', 'Label');

// Set User-ID
GoogleAnalytics.setUserID('user-id');
```

### Checking Availability

If the user does not have Google Play Services on their Android device (normally happens on a rooted-device, development or rare customized device), the native Google Analytics might not work properly. But no worries! You can detect the availability of Google Play Services on the device and fallback to use any web-based Google Analytics as you need.

```js
GoogleAnalytics.isGooglePlayServicesAvailable().then((availability) => {
  if (availability) {
    // Google Play Service and the native Google Analytics are Available!
  } else {
    // Do something else...
  }
});
```

### Callbacks

All functions of this module supports to different types of callback, the traditional passed-in callback function, or promises. This means that you can add two optional arguments: the success callback function and the error callback function, to each function you call:

```js
GoogleAnalytics.startTracker('UA-00000000-0', true, function(message) { console.log(message); }, function(error) { console.error(error); });
```

or, you can simply use the promise-thenable syntax:

```js
GoogleAnalytics.startTracker('UA-00000000-0', true).then((message) => {
  console.log(message);
}).catch((error) => {
  console.error(error);
});
```
