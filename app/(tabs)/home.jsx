import { View, Text, SafeAreaView, FlatList,Image, RefreshControl, ImageBackground, Alert } from 'react-native'
import React, { useEffect, useState } from 'react'
import { images } from '../../constants'
import ThemeButtons from '../../components/ThemeButtons'
import Actual from '../../components/Actual'
import EmptyState from '../../components/EmptyState'
import { getAllPosts } from '../../lib/FirebaseNews'
import { useFirebase } from '../../lib/useFirebase'
import NewsCard from '../../components/NewsCard'

const Home = () => {
  const [refreshing, setRefreshing]=useState(false)
  const {data:posts, refetch}=useFirebase(getAllPosts);

  const onRefresh=async()=>{
    setRefreshing(true);
    await refetch();
    setRefreshing(false);
  }

  return (
    <SafeAreaView className="h-full bg-white">
      <FlatList
        data={Object.keys(posts)} 
        keyExtractor={(item) => item.$id} 
        renderItem={({ item }) => (
          <NewsCard
            newsId={posts[item].$id}
            title={posts[item].title}
            subtitle={posts[item].subtitle}
            imageUri={posts[item].imageUri}
            theme={posts[item].theme}
            date={posts[item].date}
          />
        )}
        ListHeaderComponent={() => (
          <View className="space-y-6 w-full">

            <View className="bg-primary-default relative" >
            <View className="px-4 w-full justify-between items-start flex-row mt-6">
                <Image
                  source={images.logo_mp_white}
                  className="w-[49px] h-[44px]"
                  resizeMode="contain"
                />
              <View>
                <Text className="font-mbold text-white">Привіт, Лера!</Text>
              </View>
            </View>
            <View className="justify-end items-center">
            <Image
                source={images.subtract}
                resizeMode='contain'
                className="w-full "
              />
            </View>
            </View>
            
            <View className="p-3">
              <Text className="text-lg font-msemibold">Актуальне</Text>
              <Actual 
                posts={[{ id: "actual1" }, { id: "actual2" }, { id: "actual3" }]??[]}
              />
            </View>
            
            <View className="p-3">
              <Text className="text-lg font-msemibold">Категорії</Text>
              <ThemeButtons 
                themes={[{ id: "news" }, { id: "gaw" }, { id: "posts" }]??[]} 
              />
            </View>
            
          </View>
        )}
        refreshControl={
          <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
        }
        ListEmptyComponent={()=>(
          <EmptyState
            title="Новин немає"
            subtitle="Будь першим(-ою) хто опублікує новину"
          />
        )}
      />
    </SafeAreaView>
  );
}

export default Home