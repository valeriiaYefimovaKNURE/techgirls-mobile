import { View, Text, SafeAreaView, FlatList,Image, RefreshControl, ImageBackground, Alert } from 'react-native'
import React, { useEffect, useState } from 'react'
import { images } from '../../constants'
import ThemeButtons from '../../components/ThemeButtons'
import Actual from '../../components/Actual'
import EmptyState from '../../components/EmptyState'
import { getActualPosts, getAllPosts, getAllThemes } from '../../lib/FirebaseNews'
import { useFirebase } from '../../lib/useFirebase'
import NewsCard from '../../components/NewsCard'


const Home = () => {
  const [refreshing, setRefreshing]=useState(false)
  const {data:posts,themes:theme, actual:actualPosts, refetch}=useFirebase(getAllPosts, getAllThemes, getActualPosts);

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

            {/* Верхняя секция с изображением и логотипом */}
            <View className="bg-primary-default relative">
              <View className="px-4 w-full justify-between items-start flex-row pt-10 pb-7">
                <Image
                  source={images.logo_mp_white}
                  className="w-12 h-11"
                  resizeMode="contain"
                />
                <View>
                  <Text className="text-white font-bold">Привіт, Лера!</Text>
                </View>
              </View>

              {/* Wave Image */}
              <Image
                source={images.subtract}
                resizeMode="stretch"
                className="w-full h-12 absolute bottom-0"
                style={{
                  transform: [{ scaleX: 1.15 }, { translateX: -10 }, { translateY: 9 }],
                }}
              />
            </View>

            {/* Актуальне Section */}
            <View 
              className="pl-3"
            >
              <Text className="text-base font-msemibold">Актуальне</Text>
              <Actual posts={actualPosts} />
            </View>

            {/* Категории */}
            <View className="px-3">
              <Text className="text-base font-msemibold">Категорії</Text>
              <ThemeButtons themes={theme} />
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