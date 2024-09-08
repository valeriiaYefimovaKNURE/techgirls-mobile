import { View, Text, SafeAreaView, KeyboardAvoidingView, Platform, Alert } from 'react-native'
import React, { useState } from 'react'
import BackButton from '../../components/BackButton'
import CustomButton from '../../components/CustomButton'
import FormField from '../../components/FormField'
import GoogleButton from '../../components/GoogleButoon'
import { Redirect, router } from "expo-router"
import { useGlobalContext } from '../../context/GlobalProvider'
import { registerUser } from '../../lib/FirebaseAuth'
import GenderField from '../../components/GenderField'

const SignUp = () => {
  const {setUser,setIsLogged}=useGlobalContext();
  const [isSubmitting, setSubmitting] = useState(false);

  const [form, setForm]=useState({
    name:'',
    gender:'',
    birthday:'',
    login:'',
    email:'',
    password:''
  })

  const submit=async()=>{
    const { name, gender, birthday, login, email, password } = form;

    if(email==="" || password==="" || birthday==="" || name==="" || gender==="" || login===""){
      Alert.alert("Error","Please fill in all fields");
    }
    else{
      setSubmitting(true)

      try{
        await registerUser(name, gender,birthday,login, email, password);
        setUser({
          name,
          gender,
          birthday,
          login,
          email,
          password,
          role: "USER",
          country:"Україна"
        });

        setIsLogged(true)
  
        router.replace("/home")
      }catch (error) {
        Alert.alert("Error", error.message);
      } finally {
        setSubmitting(false);
      }
    }

  };
  
  return (
    <SafeAreaView className="bg-white h-full">
      <View className="justify-start pt-4 pl-4 mt-8">
        <BackButton 
            handlePress={()=> router.back()}
        />
      </View>

      <View className="min-h-[85vh]">

      <View className=" justify-center items-center">
        <Text className="text-xl font-mbold">Реєстрація</Text>
        <Text className="text-sm font-mmedium">Приєднуйся до безпечного ком’юніті</Text>
      </View>
      
      <View className="justify-center items-center mt-5">
        <FormField 
            title="Ім'я"
            value={form.name}
            handleChangeText={(e)=>setForm({...form,name:e})}
            otherStyles="mb-3"
        />
        <GenderField
          value={form.gender}
          handleChangeText={(e)=>setForm({...form, gender:e})}
          otherStyles="mb-3"
        />
        <FormField 
          title="Дата народження"
          value={form.birthday}
          handleChangeText={(e)=>setForm({...form, birthday:e})}
          otherStyles="mb-3"
          keyboartType="data"
        />
        <FormField 
          title="Логін"
          value={form.login}
          handleChangeText={(e)=>setForm({...form, login:e})}
          otherStyles="mb-3"
        />
      <FormField 
          title="Пошта"
          value={form.email}
          handleChangeText={(e)=>setForm({...form, email:e })}
          otherStyles="mb-3"
          keyboartType="email-address"
        />
        <FormField 
          title="Пароль"
          value={form.password}
          handleChangeText={(e)=>setForm({...form, password:e })}
          otherStyles="mb-5"
        />
        <CustomButton
          title="Зареєструватись"
          handlePress={submit}
          containerStyles="w-[250px] mb-3 "
          isLoading={isSubmitting}
        />

        <GoogleButton
          title="Реєстрація з Google"
          handlePress={() => {}}
          containerStyles="w-auto"
        />
      </View>
      </View>
    </SafeAreaView>
  )
}

export default SignUp