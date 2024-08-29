import { initializeApp } from 'firebase/app';
import { initializeAuth, getReactNativePersistence } from 'firebase/auth';
import ReactNativeAsyncStorage from '@react-native-async-storage/async-storage';
import { getFirestore } from 'firebase/firestore';
import { getDatabase, ref, set } from 'firebase/database';

const firebaseConfig = {
  apiKey: "AIzaSyACiW8WyIyKQa7QzwTBV2seiMhvPRoJ5-0",
  authDomain: "mepower-mewewomen.firebaseapp.com",
  databaseURL: "https://mepower-mewewomen-default-rtdb.firebaseio.com",
  projectId: "mepower-mewewomen",
  storageBucket: "mepower-mewewomen.appspot.com",
  messagingSenderId: "321132989492",
  appId: "1:321132989492:web:966316cc5f3a6daa440e61",
  measurementId: "G-CSQQ0W3BS0"
};

const app = initializeApp(firebaseConfig);

export const FIREBASE_AUTH = initializeAuth(app, {
    persistence: getReactNativePersistence(ReactNativeAsyncStorage)
  });
export const FIRESTORE_DB = getFirestore(app);
export const FIREBASE_DATABASE = getDatabase(app);