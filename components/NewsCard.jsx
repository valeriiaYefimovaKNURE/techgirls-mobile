import { View, Text, Image, TouchableOpacity } from 'react-native'
import React from 'react'
import { useNavigation } from '@react-navigation/native'
import { icons } from '../constants'

const NewsCard = ({
    title, 
    subtitle, 
    date, 
    imageUri, 
    theme,
    creator,
    newsId 
}) => {
  const navigation = useNavigation();
  const handlePress=()=>{
    navigation.navigate('(news)/view', {newsId})
  }
  return (
    
    <View className="px-3 py-1 shadow-xl shadow-slate-950 ">
      <TouchableOpacity className="flex-row items-start rounded-sm border border-primary-400 p-2 mb-4 bg-white"
        onPress={handlePress}
      >
        <View className="w-[140px] h-[140px] justify-center items-center p-2 mr-3">
          <Image
            source={{ uri: imageUri }}
            className="w-full h-full rounded-lg"
            resizeMode="cover"
          />
        </View>
        <View className="flex-1 flex-col justify-between">
          <View className="flex-col mb-2">
            <Text className="text-lg font-msemibold text-black">{title}</Text>
            <Text className="text-sm font-mmedium text-gray-600 mt-1">{subtitle}</Text>
          </View>

          <View className="flex-row items-center justify-between mt-2">
            <Text className="text-sm font-mmedium text-primary-600">{theme}</Text>
            <Text className="text-lg font-mmedium text-gray-800">•</Text>
            <Text className="text-sm font-mmedium text-gray-800 ml-2">{date}</Text>
            <TouchableOpacity>
            <Image
              source={icons.dots_horizontal}
              className="w-[28px] h-[15px]"
              resizeMode='contain'
            />
            </TouchableOpacity>
          </View>

        </View>
      </TouchableOpacity>
    </View>
  )
}

export default NewsCard