import React from 'react';
import { View, Text, Image, ScrollView } from 'react-native';
import { useRoute } from '@react-navigation/native';

const ViewLayout = () => {

    const route = useRoute();
    const { newsId } = route.params;
  return (
    <View>
      <Text>ViewLayout</Text>
    </View>
  )
}

export default ViewLayout