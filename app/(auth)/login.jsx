import { View, Text, SafeAreaView, Image,Alert } from 'react-native'
import React, { useState } from 'react'
import BackButton from '../../components/BackButton'
import CustomButton from '../../components/CustomButton'
import FormField from '../../components/FormField'
import GoogleButton from '../../components/GoogleButoon'
import { Redirect, router } from "expo-router"
import { useGlobalContext } from '../../context/GlobalProvider.js'
import { getCurrentUser, loginUser } from '../../lib/FirebaseAuth'

const LoginLayout = () => {
  const {setUser,setIsLogged}=useGlobalContext();
  const [isSubmitting, setSubmitting] = useState(false);

  const [form, setForm]=useState({
    email:'',
    password:''
  })


  const submit=async()=>{
    if(form.email==="" || form.password===""){
      Alert.alert("Error","Please fill in all fields");
    }
    else{
      setSubmitting(true);

      try{
        const {userData}=await loginUser(form.email,form.password)
        const result=getCurrentUser();
        setUser(result);
        setIsLogged(true);

        router.replace("/home")
      }catch(error){
        Alert.alert("Error"+error.message)
      }finally{
        setSubmitting(false)
      }
    }

  }

  return (
    <SafeAreaView className="h-full bg-white">
      <View className="justify-start p-4 mt-8">
        <BackButton
          handlePress={()=>router.back()}
        />
      </View>
      <View className="min-h-[85vh]">
        <View className=" justify-center items-center py-10">
          <Text className="text-xl font-mbold">Раді тебе бачити!</Text>
          <Text className="text-sm font-mmedium">Вибери спосіб входу</Text>
        </View>

        <View className="justify-end items-center pb-8 pt-10">
          <GoogleButton
             title="Увійти з Google"
             handlePress={() => {}}
             containerStyles="w-auto mb-4"
          />
          <Text className="text-base font-mregular">Або</Text>
        </View>

        <View className="justify-end items-center ">
          <FormField
            title="Пошта"
            value={form.email}
            handleChangeText={(e)=>setForm({...form, email:e })}
            otherStyles="mt-7"
            keyboartType="email-address"
          />
          <FormField
             title="Пароль"
             value={form.password}
             handleChangeText={(e)=>setForm({...form,password:e})}
             otherStyles="mt-7 mb-[50px]"
          />
          <CustomButton
             title="Увійти"
             handlePress={submit}
             containerStyles="w-[250px] "
             isLoading={isSubmitting}
          />
        </View>
      </View>

    </SafeAreaView>
  )
}

export default LoginLayout