import { View, Text } from 'react-native'
import {Stack} from 'expo-router'
import { useState } from 'react'

const AuthLayout = () => {

  return (
    <>
      <Stack>
        <Stack.Screen
          name='login'
          options={{
            headerShown:false
          }}
        />
        <Stack.Screen
          name='sign_up'
          options={{
            headerShown:false
          }}
        />
      </Stack>
    </>
  )
}

export default AuthLayout