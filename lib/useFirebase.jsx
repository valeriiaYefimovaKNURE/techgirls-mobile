import { Alert } from "react-native";
import { useEffect, useState } from "react";

export const useFirebase=(fn,fn2, fn3)=>{
    const [data,setData]=useState([]);
    const [themes,setThemes]=useState([]);
    const [actual, setActual]=useState([]);
    const [isLoading, setLoading]=useState(true);

    
    const fetchData=async()=>{
        setLoading(true)

        try{
        const response=await fn()
        const themes=await fn2()
        const actualPosts=await fn3()
        
        setData(response)
        setThemes(themes)
        setActual(actualPosts)
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

    return {data,themes,actual, isLoading,refetch};

}