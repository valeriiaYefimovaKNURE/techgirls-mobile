import { View, Text, Alert } from 'react-native'
import React from 'react'
import { router } from 'expo-router'
import CustomButton from '../../components/CustomButton'
import { logOut } from '../../lib/FirebaseAuth'
import { useGlobalContext } from '../../context/GlobalProvider.js'

const SettingsLayout = () => {
  const {setUser,setIsLogged}=useGlobalContext();
  const logoutSubmit=async()=>{
    try{
      logOut()
      setUser(null)
      setIsLogged(false)
      router.replace('/')
      console.log("User successfully logged out.");
    }
    catch(error){
      Alert.alert("Error"+error.message)
    }
  }

  return (
    <View className="h-full bg-white ">
      <View className="p-4 mt-4">
        <Text className="text-lg text-mbold text-black">Налаштування</Text>
        <View className="h-1/9 justify-end items-center">
          <CustomButton
            title="Вийти"
            handlePress={logoutSubmit}
            containerStyles="w-[250px] mb-4"
          />
        </View>
      </View>
    </View>
  )
}

export default SettingsLayout