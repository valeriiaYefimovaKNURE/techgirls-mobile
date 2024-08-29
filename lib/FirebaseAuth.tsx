import { router } from "expo-router";
import { FIREBASE_AUTH, FIREBASE_DATABASE } from "./firebaseConfig"
import { createUserWithEmailAndPassword, signInWithEmailAndPassword, signOut } from 'firebase/auth';
import { ref, set } from 'firebase/database';

export const registerUser=async(name, gender, birthday, login, email, password)=>{
    try{
        const userCredential = await createUserWithEmailAndPassword(FIREBASE_AUTH,email,password);
        const user=userCredential.user

        await set(ref(FIREBASE_DATABASE,`/Users/${user.uid}`),{
            uid:user.uid,
            name: name,
            gender: gender,
            birthday: birthday,
            login: login,
            email: email,
            password:password,
            country: "Україна",
            role:"USER"
        });

        console.log("User registered and saved in DB");
    }catch (error) {
        if (error.code) {
          switch (error.code) {
            case 'auth/email-already-in-use':
              alert('That email address is already in use!');
              break;
            case 'auth/invalid-email':
              alert('That email address is invalid!');
              break;
            case 'auth/operation-not-allowed':
              alert('Email/password accounts are not enabled.');
              break;
            case 'auth/weak-password':
              alert('The password is too weak.');
              break;
            default:
              alert('An unknown error occurred:'+ error.message);
          }
        } else {
          alert('An error occurred:'+ error.message);
        }
    }
}

export const loginUser=async(email,password)=>{
    try{
        signInWithEmailAndPassword(FIREBASE_AUTH,email,password);
        console.log("Logged in");
    }catch (e) {
        if (e instanceof Error) {
            switch (e.message) {
                case 'auth/invalid-email':
                    console.error('Invalid email address.');
                    break;
                case 'auth/user-disabled':
                    console.error('User account disabled.');
                    break;
                case 'auth/user-not-found':
                    console.error('No user found with this email.');
                    break;
                case 'auth/wrong-password':
                    console.error('Incorrect password.');
                    break;
                default:
                    console.error('Login failed:', e.message);
            }
        } else {
            console.error('An unknown error occurred:', e);
        }
    }
}

export const getCurrentUser=async()=>{
    try{
        const user=FIREBASE_AUTH.currentUser;
        if(user){
            return (user);
        }else{
            return null;
        }
    }
    catch(e){
        alert('getCurrentUser error:'+e.message)
    }
}

export const logOut=async()=>{
    console.log("Attempting to log out...");

    try {
        await signOut(FIREBASE_AUTH);
        router.replace('/')
        console.log("User successfully logged out.");
    } catch (error) {
        console.error("Error logging out:", error);
    }
}