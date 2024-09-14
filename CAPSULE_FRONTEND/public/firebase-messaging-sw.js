// Give the service worker access to Firebase Messaging.
// Note that you can only use Firebase Messaging here. Other Firebase libraries
// are not available in the service worker.
importScripts("https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js");
importScripts(
  "https://www.gstatic.com/firebasejs/8.10.0/firebase-messaging.js"
);

// Initialize the Firebase app in the service worker by passing in
// your app's Firebase config object.
// https://firebase.google.com/docs/web/setup#config-object
firebase.initializeApp({
  apiKey: "AIzaSyAq7NYmtgnD4viYVvFmrhszkL_o2SIImtA",
  authDomain: "talktalk-a7e28.firebaseapp.com",
  projectId: "talktalk-a7e28",
  storageBucket: "talktalk-a7e28.appspot.com",
  messagingSenderId: "532620406105",
  appId: "1:532620406105:web:775e23e14e8aadd901f048",
  measurementId: "G-RY5BXMHN7F",
});

// Retrieve an instance of Firebase Messaging so that it can handle background
// messages.
const messaging = firebase.messaging();
messaging.onBackgroundMessage((payload) => {
  console.log(
    "[firebase-messaging-sw.js] Received background message ",
    payload
  );
  // Customize notification here
  const notificationTitle = "Background Message Title";
  const notificationOptions = {
    body: "문자가 왔어요",
    icon: "/firebase-logo.png",
  };

  self.registration.showNotification(notificationTitle, notificationOptions);
});
