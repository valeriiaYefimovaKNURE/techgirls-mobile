import { View, Text, FlatList, TouchableOpacity} from 'react-native'
import React, { useState } from 'react'

const ThemeButtons = ({ themes }) => {
  const updatedThemes = ["Всі", ...themes];
  
  const [selectedTheme, setSelectedTheme] = useState("Всі");

  const handleThemeSelect = (theme) => {
    setSelectedTheme(theme);
  };

  return (
    <FlatList
      data={updatedThemes}
      keyExtractor={(item, index) => index.toString()}
      renderItem={({ item }) => (
        <TouchableOpacity 
          onPress={() => handleThemeSelect(item)} 
          className="py-2 pr-2"
        >
          <View 
            className={`rounded-full border border-primary-400 px-6 py-3 ${
              selectedTheme === item ? "bg-primary-default" : "bg-primary-light"
            }`}
          >
            <Text 
              className={`font-mmedium ${selectedTheme === item ? "text-white" : "text-black"}`}
            >
              {item}
            </Text>
          </View>
        </TouchableOpacity>
      )}
      horizontal
      showsHorizontalScrollIndicator={false}
    />
  );
};

export default ThemeButtons;