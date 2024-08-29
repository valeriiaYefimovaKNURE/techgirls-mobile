import { Alert } from "react-native";
import { useEffect, useState } from "react";

export const useFirebase=(fn)=>{
    const [data,setData]=useState([]);
    const [isLoading, setLoading]=useState(true);

    
    const fetchData=async()=>{
        setLoading(true)

        try{
        const response=await fn()
        
        setData(response)
        }catch(error){
            Alert.alert('Error', error.message)
        }finally{
            setLoading(false)
        }
    }

    useEffect(()=>{
        fetchData()
    },[]);

    const refetch=()=>fetchData();

    return {data,isLoading,refetch};

}