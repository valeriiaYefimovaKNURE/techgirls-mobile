import { View, Text, TextInput, TouchableOpacity, Image } from 'react-native'
import React, { useState } from 'react'
import icons from '../constants/icons'

const NewsFormField = ({
    title,
    value,
    placeholder,
    handleChangeText,
    otherStyles,
    ...props
}) => {
    const [inputHeight, setInputHeight] = useState(40); // Начальная высота текстового поля

    const handleContentSizeChange = (contentWidth, contentHeight) => {
      setInputHeight(contentHeight); // Обновляем высоту в зависимости от содержимого
    };
  return (
    <View className={`space-y-2 ${otherStyles}`}>
      <Text className="text-sm text-grey-default font-mmedium">
        {title}
      </Text>
      <View className="border-2 border-gray-400 w-[320px] h-auto px-4 bg-white rounded-xl focus:border-primary-default items-center flex-row"
        style={{ minHeight: 40, height: inputHeight }}
      >
        <TextInput
          className="flex-1 text-black font-mmedium text-base px-1 py-2"
          value={value}
          placeholder={placeholder}
          placeholderTextColor='border-grey-default'
          onChangeText={handleChangeText}
          multiline
          onContentSizeChange={handleContentSizeChange}
          style={{ height: inputHeight }} 
        />
      </View>
    </View>
  )
}

export default NewsFormField