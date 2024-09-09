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
      <TouchableOpacity className="min-h-[180px] flex-row items-start rounded-lg border border-primary-400 p-2 mb-4 bg-white"
        onPress={handlePress}
      >
        <View className="w-[140px] h-[180px] justify-center items-center p-2 mr-3">
          <Image
            source={{ uri: imageUri }}
            className="w-full h-full "
            resizeMode="cover"
          />
        </View>
        <View className="min-h-[118px] flex-1 flex-col justify-between max-h-30 ">
          <View className="flex-col mb-2 overflow-hidden">
            <Text className="text-sm mt-1 font-mbold text-black">{title}</Text>
            <Text className="text-xs font-mmedium text-gray-600 mt-1">{subtitle}</Text>
          </View>

          <View className="flex-row items-end justify-between mt-1 space-x-1">
            <Text className="text-xs font-mmedium text-primary-600">{theme}</Text>
            <Text className="text-sm font-mmedium text-gray-800">•</Text>
            <Text className="text-xs font-mmedium text-gray-800 ml-2">{date}</Text>
            <TouchableOpacity>
            <Image
              source={icons.dots_horizontal}
              className="w-[28px] h-[15px] mb-[2px]"
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