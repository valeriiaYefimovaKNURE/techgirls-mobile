import { FIREBASE_DATABASE } from "./firebaseConfig";
import { ref, set, get} from 'firebase/database';

export const getAllPosts=async()=>{
    try{
        const postsRef=ref(FIREBASE_DATABASE,'News')
        const snapshot=await get(postsRef)

        if(snapshot.exists()){
            const data=snapshot.val()
            return data;
        }
        else{
            console.log("No News data available");
            return [];
        }
    }catch(error){
        console.error("Error getting posts:", error);
    }
}