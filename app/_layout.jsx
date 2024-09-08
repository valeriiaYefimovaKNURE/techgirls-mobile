import { StyleSheet, Text, View } from 'react-native';
import {SplashScreen, Stack} from "expo-router"
import {useFonts} from "expo-font"
import { useEffect } from 'react';
import GlobalProvider from '../context/GlobalProvider';

SplashScreen.preventAutoHideAsync();

const RootLayout=()=>{
  const [fontsLoaded, error] =useFonts({
    "Ubuntu-Medium":require("../assets/fonts/Ubuntu-Medium.ttf"),
    "Ubuntu-Bold":require("../assets/fonts/Ubuntu-Bold.ttf"),
    "Ubuntu-Regular":require("../assets/fonts/Ubuntu-Regular.ttf"),
    "Manrope-Bold":require("../assets/fonts/Manrope-Bold.ttf"),
    "Manrope-ExtraBold":require("../assets/fonts/Manrope-ExtraBold.ttf"),
    "Manrope-Light":require("../assets/fonts/Manrope-Light.ttf"),
    "Manrope-Medium":require("../assets/fonts/Manrope-Medium.ttf"),
    "Manrope-Regular":require("../assets/fonts/Manrope-Regular.ttf"),
    "Manrope-SemiBold":require("../assets/fonts/Manrope-SemiBold.ttf"),
    "Manrope-ExtraLight":require("../assets/fonts/Manrope-ExtraLight.ttf")
  });

  useEffect(()=>{
    if(error) throw error;

    if(fontsLoaded) SplashScreen.hideAsync();
  }, [fontsLoaded,error])

  if(!fontsLoaded && !error) return null;

  return(
    <GlobalProvider>
        <Stack>
            <Stack.Screen name="index" options={{headerShown:false}}/>
            <Stack.Screen name="(auth)" options={{headerShown:false}}/>
            <Stack.Screen name="(tabs)" options={{headerShown:false}}/>
            <Stack.Screen name="(news)" options={{headerShown:false}}/>
        </Stack>
    </GlobalProvider>
  )
}

export default RootLayout