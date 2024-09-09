import { View, Text, FlatList, TouchableOpacity, ImageBackground } from 'react-native'
import * as Animatable from "react-native-animatable";
import React, { useState } from 'react'

const zoomIn = {
  0: {
    scale: 0.9,
  },
  1: {
    scale: 1,
  },
};

const zoomOut = {
  0: {
    scale: 1,
  },
  1: {
    scale: 0.9,
  },
};
const ActualItem=({activeItem, item})=>{
  return(
    <Animatable.View
      className="mr-5 mt-2"
      animation={activeItem === item.$id ? zoomIn : zoomOut}
      duration={500}
    >
      <TouchableOpacity className="rounded-lg overflow-hidden relative flex justify-center items-center">
          <ImageBackground
            source={{ uri: item.imageUri }}
            style={{ width: 310, height: 144, justifyContent: 'flex-end' }} 
            imageStyle={{ borderRadius: 10 }} 
          >
            <View style={{ backgroundColor: 'rgba(0, 0, 0, 0.5)', padding: 10 }}>
              <Text className="text-sm font-mbold text-white">{item.title}</Text>
              <Text className="text-xs text-white mt-1">{item.subtitle}</Text>
            </View>
          </ImageBackground>
        </TouchableOpacity>

    </Animatable.View>
  )
}
const Actual = ({ posts }) => {
  const [activeItem, setActiveItem] = useState(posts[0]);
  const viewableItemsChanged = ({ viewableItems }) => {
    if (viewableItems.length > 0) {
      setActiveItem(viewableItems[0].key);
    }
  };

  return (
    <FlatList
      data={posts}
      keyExtractor={(item) => item.$id}
      renderItem={({ item }) => (
        <ActualItem
          activeItem={activeItem}
          item={item}
        />
      )}
      horizontal
      showsHorizontalScrollIndicator={false}
      onViewableItemsChanged={viewableItemsChanged}
      viewabilityConfig={{
        itemVisiblePercentThreshold: 70,
      }}
      contentOffset={{ x: 170 }}
    />
  );
}

export default Actual