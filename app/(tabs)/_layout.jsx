import React from 'react';
import { Tabs, Redirect } from 'expo-router';
import { Image, Text, View } from "react-native";
import { useGlobalContext } from '../../context/GlobalProvider';

import { icons } from '../../constants';

const TabIcon=({icon, color, focused})=>{
  return(
    <View className="flex items-center justify-center gap-2">
      <Image
        source={icon}
        resizeMode="contain"
        tintColor={color}
        className="w-6 h-6"
      />
    </View>
  )
};

const TabsLayout = () => {
  const {user,loading}=useGlobalContext();

  if (loading) {
    return null;
  }

  return (
    <>
    <Tabs 
      screenOptions={{
        tabBarShowLabel:false,
        tabBarActiveTintColor:'#FFFFFF',
        tabBarInactiveTintColor:'#000000',
        tabBarStyle:{
          backgroundColor:'#a883b9',
          borderTopWidth:1,
          borderTopColor:'#d8c0f4',
          height:62
        }
      }}
    >
      <Tabs.Screen 
        key="home"
        name="home"
        options={{ 
          title:'Home',
          headerShown: false,
          tabBarIcon:({color, focused})=>(
            <TabIcon
              icon={icons.navHome}
              color={color}
              focused={focused}
            />
          )
         }}
      />
      <Tabs.Screen 
        key="create"
        name="create"
        options={{ 
          title:'Create',
          headerShown: false,
          tabBarIcon:({color, focused})=>(
            <TabIcon
              icon={icons.navCreate}
              color={color}
              focused={focused}
            />
          )
         }}
      />
      {user?.role === 'ADMIN' && (
        <Tabs.Screen 
          key="map"
          name="map"
          options={{ 
            title:'Map',
            headerShown: false,
            tabBarIcon:({color, focused})=>(
              <TabIcon
                icon={icons.navMap}
                color={color}
                focused={focused}
              />
            )
           }}
        />
      )}
      <Tabs.Screen 
        key="settings"
        name="settings"
        options={{ 
          title:'Settings',
          headerShown: false,
          tabBarIcon:({color, focused})=>(
            <TabIcon
              icon={icons.navSettings}
              color={color}
              focused={focused}
            />
          )
         }}
      />
    </Tabs>
    </>
  );
};

export default TabsLayout;