import { View, Text, Alert, Image, TouchableOpacity, ScrollView } from 'react-native'
import React, {useState} from 'react'
import { router } from 'expo-router'
import CustomButton from '../../components/CustomButton'
import { images } from '../../constants'
import NewsFormField from '../../components/NewsFormField'
import ThemeField from '../../components/ThemeField'
import SaveButton from '../../components/SaveButton'

const CreateLayout = () => {
  const [isSubmitting, setSubmitting] = useState(false);

  const [form, setForm]=useState({
    title:'',
    subtitle:'',
    uriImage:'',
    text:'',
    theme:'',
    link:'',
  })

  const submit=async()=>{

  }

  return (
    <ScrollView className="h-full bg-white ">
      <View className="p-4 mt-4 px-4 ">
        <Text className="text-lg text-mbold text-black mt-2">Створити допис</Text>
        
      </View>
      <View className="justify-center items-center">
        <TouchableOpacity>
          <Image 
            source={images.addImage}
            className="w-[320px] h-[210px]"
            resizeMode='contain'
          />
        </TouchableOpacity>
        <NewsFormField
            title="Назва"
            value={form.title}
            handleChangeText={(e)=>setForm({...form, title:e })}
            otherStyles="mt-2"
          />
        <NewsFormField
            title="Опис"
            value={form.title}
            handleChangeText={(e)=>setForm({...form, title:e })}
            otherStyles="mt-3"
        />
        <NewsFormField
            title="Текст"
            value={form.title}
            handleChangeText={(e)=>setForm({...form, title:e })}
            otherStyles="mt-3"
          />
          <ThemeField
            value={form.theme}
            handleChangeText={(e)=>setForm({...form, theme:e})}
            otherStyles="mt-3"
          />
          <NewsFormField
            title="Посилання"
            value={form.title}
            handleChangeText={(e)=>setForm({...form, title:e })}
            otherStyles="mt-3"
          />
      </View>
      <View className="py-5 items-end mr-9">
        <SaveButton
          title="Опублікувати"
          handlePress={submit}
          containerStyles="w-[120px] "
          isLoading={isSubmitting}
        />
      </View>
    </ScrollView>
  )
}

export default CreateLayout