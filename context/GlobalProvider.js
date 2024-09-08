import React, { createContext, useContext, useEffect, useState } from "react"
import { getCurrentUser } from "../lib/FirebaseAuth"

const GlobalContext=createContext()

export const useGlobalContext=()=> useContext(GlobalContext);

export const GlobalProvider=({children})=>{
    const [user, setUser]=useState(null);
    const[loading,setLoading]=useState(true);
    const [isLogged, setIsLogged]=useState(false);

    useEffect(()=>{
        const fetchUser = async () => {
            try {
                const currentUser = await getCurrentUser();
                if (currentUser) {
                    setIsLogged(true);
                    setUser(currentUser);
                } else {
                    setIsLogged(false);
                    setUser(null);
                }
            } catch (error) {
                console.error(error);
            } finally {
                setLoading(false);
            }
        };

        fetchUser();
    },[])

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