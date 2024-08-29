import { Text, View, ActivityIndicator, TouchableOpacity, Image} from 'react-native'
import icons from "../constants/icons"
import { router } from 'expo-router';

const BackButton=({
    handlePress,
    isLoading
})=>{
    return(
        <TouchableOpacity
            onPress={handlePress}
            activeOpacity={0.7}
            className={`bg-primary-100 rounded-3xl min-h-[42px] w-[42px] flex flex-row justify-center items-center ${
                isLoading ? "opacity-50":""
            }`}
            disabled={isLoading}
        >
            <Image
                source={icons.back_arrow} 
                className="w-[13px] h-[21px]"
                resizeMode="contain"
            />
            {isLoading &&(
                <ActivityIndicator
                    animating={isLoading}
                    color="#f1249"
                    size="small"
                    className="ml-2"
                />
            )}

        </TouchableOpacity>
    )
}

export default BackButton;