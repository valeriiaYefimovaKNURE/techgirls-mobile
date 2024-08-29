import { Text, View, Image, Button } from 'react-native'
import { Redirect, router } from "expo-router";
import { StatusBar } from 'expo-status-bar'
import { SafeAreaView } from 'react-native-safe-area-context'
import images from '../constants/images'
import CustomButton from '../components/CustomButton'
import { useGlobalContext } from '../context/GlobalProvider';

export default function App(){
    const {loading,isLogged}=useGlobalContext();

    if(!loading && isLogged) return <Redirect href="/home"/>

    return(
        <SafeAreaView className="flex-1 bg-white">
            <View className="flex-1 justify-between items-center px-4">
                <View className="flex-1 justify-center items-center mb-[150px]">
                    <Image 
                        source={images.me_logo} 
                        className="w-[120px] h-[74px]"
                        resizeMode="contain"
                    />
                    <Text className="text-2xl font-msemibold">Вітаємо у mePower</Text>
                    <Text className="text-sm font-mregular">Почувай себе комфортно</Text>
                </View>
                <View className="w-full px-2 mb-[50px] justify-between items-center">
                    <CustomButton 
                        title="Увійти"
                        handlePress={() => router.push('/login')}
                        containerStyles="w-[250px] mb-4"
                    />
                    <CustomButton 
                        title="Зареєструватись"
                        handlePress={() => router.push('/sign_up')}
                        containerStyles="w-[250px]"
                    />
                </View>
            </View>
        </SafeAreaView>
    );
}