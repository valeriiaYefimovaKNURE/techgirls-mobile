import { View, Text, FlatList } from 'react-native'
import React from 'react'

const ThemeButtons = (themes) => {
  return (
    <FlatList
      data={themes}
      keyExtractor={(item)=>item.$id}
      renderItem={({item})=>{
        
        <Text className="text-3xl text-black">{item.id}</Text>
      }}
    horizontal
    />
  )
}

export default ThemeButtons