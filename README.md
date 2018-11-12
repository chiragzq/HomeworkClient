# HomeworkClient

An android mobile app for accessing PCR, the homework management system utilized by The Harker School. Supports features such as using multiple accounts, calendar view, list view, marking done assignments, and more.

## Installation
When I made this app, I did not have a Google developer account, so I did not upload it to the Google Play Store. Despite this, installation is relatively simple.

1. Install [Android Studio](https://developer.android.com/studio/)
2. Clone the repository and import the `build.gradle` file
3. Install the Android SDK and any other files needed. The project uses Android SDK version 25.
4. Deploy the app onto your phone. Developer mode needs to be enabled on your phone, you can learn to do that [here](https://developer.android.com/training/basics/firstapp/running-app#RealDevice)

### Note about security
Since you have to login every time the app is restarted, your credentials are not stored locally. Also, because it is a mobile app, it is able to communicate directly with PCR. You can review the source code and the logs to see that I am going this. I can ensure that I would not violate the honor code and try to steal your password.

### Todo
* Saving assignments offline
* Week view
* An improved calendar view, which allows for better visualization of the range of assigments
* Custom assignments
* Notifications for incompleted assignments
* Pinning important assignments
* Persistent logins
* Light/Dark theme

### Not Todo
* iOS port
