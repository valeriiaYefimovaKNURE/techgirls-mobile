import React, { Component } from 'react'
import {Tabs, Redirect, Stack} from 'expo-router'

export class TabsLayout extends Component {
  render() {
    return (
      <>
        <Tabs>
            <Tabs.Screen 
                name="home"
                options={{
                  headerShown:false
                }}
            />
            <Tabs.Screen 
                name="create"
                options={{
                  headerShown:false
                }}
            />
            <Tabs.Screen 
                name="map"
                options={{
                  headerShown:false
                }}
            />
            <Tabs.Screen 
                name="settings"
                options={{
                  headerShown:false
                }}
            />
        </Tabs>
      </>
    )
  }
}

export default TabsLayout