import { View, Text } from 'react-native'
import React from 'react'
import { Dropdown } from 'react-native-element-dropdown'
import { genderItems } from '../constants/data'

const GenderField = (
    value,
    handleChangeText,
    otherStyles
) => {
  return (
    <View className={`space-y-2 ${otherStyles}`}>
      <Text className="text-sm text-grey-default font-mmedium">
        Стать
      </Text>
    
      <View className="border-2 border-gray-400 w-[250px] h-11 px-4 bg-white rounded-xl focus:border-primary-default items-center flex-row">
      <Dropdown
        className="flex-1 text-black font-mmedium text-base"
        placeholder="Стать"
        placeholderTextColor='border-grey-default'
        onChange={(item) => handleChangeText(item.value)}
        data={genderItems}
        value={value}
        labelField="label"
        valueField="value"
      />
      </View>
    </View>
  )
}

export default GenderField