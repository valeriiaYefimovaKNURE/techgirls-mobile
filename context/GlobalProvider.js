import React, { createContext, useContext, useEffect, useState } from "react"
import { getCurrentUser } from "../lib/FirebaseAuth"

const GlobalContext=createContext()

export const useGlobalContext=()=> useContext(GlobalContext);

export const GlobalProvider=({children})=>{
    const [user, setUser]=useState(null);
    const[loading,setLoading]=useState(true);
    const [isLogged, setIsLogged]=useState(false);

    useEffect(()=>{
        getCurrentUser().then((res)=>{
            if(res){
                setIsLogged(true);
                setUser(res);
            }else{
                setIsLogged(false);
                setUser(null);
            }
        })
        .catch((error)=>{
            console.log(error);
        })
        .finally(()=>{
            setLoading(false);
        })
    })

    return(
        <GlobalContext.Provider
            value={{
                isLogged,
                setIsLogged,
                user,
                setUser,
                loading
            }}
        >
            {children}
        </GlobalContext.Provider>
    )
}

export default GlobalProvider;