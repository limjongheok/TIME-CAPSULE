import firebase from "firebase/app";

export async function getToken() {
  const messaging = firebase.messaging();
  const token = await messaging.getToken({
    vapidKey: process.env.NEXT_PUBLIC_FIREBASE_MESSAGING_VAPID_KEY,
  });

  return token;
}