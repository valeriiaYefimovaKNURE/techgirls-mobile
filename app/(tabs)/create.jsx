import { View, Text, Alert } from 'react-native'
import React from 'react'
import { router } from 'expo-router'
import CustomButton from '../../components/CustomButton'

const CreateLayout = () => {
  return (
    <View className="h-full bg-white ">
      <View className="p-4 mt-4">
        <Text className="text-lg text-mbold text-black">Створити допис</Text>
        
      </View>
    </View>
  )
}

export default CreateLayout