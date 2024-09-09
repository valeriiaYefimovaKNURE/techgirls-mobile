import { FIREBASE_DATABASE } from "./firebaseConfig";
import { ref, set, get,orderByChild, equalTo, query} from 'firebase/database';

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

export const getActualPosts=async()=>{
    try {
        const postsRef = ref(FIREBASE_DATABASE, 'News');
        
        const Query = query(postsRef, orderByChild('isActual'), equalTo(true));
        const snapshot = await get(Query);
    
        if (snapshot.exists()) {
          const data = snapshot.val();
    
          const postsArray = Object.keys(data).map(key => data[key]);
    
          return postsArray;
        } else {
          console.log("No actual news data available");
          return [];
        }
      } catch (error) {
        console.error("Error getting actual posts:", error);
        return [];
      }
}

export const getAllThemes= async()=>{
    try{
        const postsRef=ref(FIREBASE_DATABASE,'News')
        const snapshot=await get(postsRef)

        if(snapshot.exists()){
            const data=snapshot.val()
            const themes=new Set();

            Object.keys(data).forEach((key)=>{
                if(data[key].theme){
                    themes.add(data[key].theme)
                }
            });

            return Array.from(themes)
        }
        else{
            console.log("No News data available");
            return [];
        }
    }catch(error){
        console.error("Error getting themes:", error);
    }
}

