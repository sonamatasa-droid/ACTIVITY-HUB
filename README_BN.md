# ACTIVITY HUB — প্রাথমিক Android Project

Package: `com.activityhub.monitor`  
প্রাথমিক লক্ষ্য: Android 14+ compatible starter  
ভাষা: বাংলা + English  
Cloud পরিকল্পনা: Firebase Email/Password Auth + Firestore

## এই সংস্করণে যা আছে
- Jetpack Compose dashboard shell
- Android Usage Access permission flow
- আজকের অ্যাপ-ভিত্তিক foreground usage summary
- বাংলা/ইংরেজি string resources
- Firebase dependency placeholders

## Android Studio-তে খোলা
1. ZIP extract করুন।
2. Android Studio-তে `ACTIVITY_HUB` folder Open করুন।
3. Gradle sync সম্পন্ন করুন।
4. `app/google-services.json` ফাইলটি Firebase Console থেকে ডাউনলোড করে রাখুন।
5. Firebase Console-এ Android app যোগ করুন: `com.activityhub.monitor`।
6. Authentication > Sign-in method থেকে Email/Password চালু করুন।
7. Firestore Database তৈরি করুন এবং production-এ নিরাপদ Security Rules দিন।
8. ফোনে ইনস্টল করে Settings > Apps > Special app access > Usage access থেকে ACTIVITY HUB অনুমতি দিন।

## গুরুত্বপূর্ণ
- `google-services.json` এই ZIP-এ নেই; এটি আপনার Firebase project-specific secret/config file।
- Firebase Auth UI ও Firestore upload worker পরবর্তী ধাপে যুক্ত করতে হবে। বর্তমান UI-তে Cloud Setup placeholder।
- Usage Access Android-এর aggregate usage statistics দেয়; সব launch/exit event বা app-এর ভিতরের search/chat নয়।
- App install/remove event-এর নির্ভরযোগ্য audit log এই starter-এ এখনো implement করা হয়নি।
- Website domain logging-এর জন্য DNS provider-এর নিজস্ব account/config এবং user-visible setup প্রয়োজন।
- পূর্বে মুছে ফেলা ইতিহাস উদ্ধার করা হয় না।
- Password, private messages বা অন্য অ্যাপের গোপন কনটেন্ট সংগ্রহ করা হয় না।

## Build
Android Studio-তে Gradle sync-এর পর Build > Build Bundle(s) / APK(s) > Build APK(s)।
প্রস্তাবিত JDK: 17।
