import { router } from "expo-router";
import { View, Text, Image } from "react-native";

import { images } from "../constants";
import CustomButton from "./CustomButton";

const EmptyState = ({ title, subtitle }) => {
    return (
      <View className="flex justify-center items-center px-4">
        
        <Text className="text-xl font-msemibold text-black">{title}</Text>
        <Text className="text-sm text-center font-mmedium text-black mt-2">
          {subtitle}
        </Text>
  
        <CustomButton
          title="Back to Explore"
          handlePress={() => router.push("/home")}
          containerStyles="w-auto p-4 my-5"
        />
      </View>
    );
  };
  
  export default EmptyState;