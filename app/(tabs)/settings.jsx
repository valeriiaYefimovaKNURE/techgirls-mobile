import { View, Text } from 'react-native'
import React from 'react'
import CustomButton from '../../components/CustomButton'
import { logOut } from '../../lib/FirebaseAuth'

const SettingsLayout = () => {


  return (
    <View className="h-full bg-white ">
      <View className="p-4 mt-4">
        <Text className="text-lg text-mbold text-black">Налаштування</Text>
        <View className="h-1/9 justify-end items-center">
          <CustomButton
            title="Вийти"
            handlePress={() => logOut()}
            containerStyles="w-[250px] mb-4"
          />
        </View>
      </View>
    </View>
  )
}

export default SettingsLayout