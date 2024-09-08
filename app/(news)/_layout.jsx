import { View, Text } from 'react-native'
import React from 'react'
import { Stack } from 'expo-router'

const NewsLayout = () => {
  return (
    <>
    <Stack>
        <Stack.Screen
            name='view'
            options={{
            headerShown:false
            }}
        />

    </Stack>
    </>
  )
}

export default NewsLayout