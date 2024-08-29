import { View, Text, TextInput, TouchableOpacity, Image } from 'react-native'
import React, { useState } from 'react'
import icons from '../constants/icons'

const FormField = ({
    title,
    value,
    placeholder,
    handleChangeText,
    otherStyles,
    ...props
}) => {
    const [showPassword, setShowPassword]=useState(false)

  return (
    <View className={`space-y-2 ${otherStyles}`}>
      <Text className="text-sm text-grey-default font-mmedium">
        {title}
      </Text>
      <View className="border-2 border-gray-400 w-[250px] h-11 px-4 bg-white rounded-xl focus:border-primary-default items-center flex-row">
        <TextInput 
            className="flex-1 text-black font-mmedium text-base"
            value={value}
            placeholder={placeholder}
            placeholderTextColor='border-grey-default'
            onChangeText={handleChangeText}
            secureTextEntry={title==='Пароль' && !showPassword}
        />

        {title==='Пароль' &&(
            <TouchableOpacity onPress={()=> setShowPassword(!showPassword)}>
                <Image
                    source={!showPassword ? icons.eye : icons.eye_hide}
                    className="w-[20px] h-[20px]"
                    resizeMode='contain'
                />

            </TouchableOpacity>
        )}

      </View>
    </View>
  )
}

export default FormField