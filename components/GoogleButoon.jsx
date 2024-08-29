import { Text, View, ActivityIndicator, TouchableOpacity, Image} from 'react-native'
import icons from '../constants/icons';

const GoogleButton=({
    title,
    handlePress,
    containerStyles,
    textStyles,
    isLoading
})=>{
    return(
        <TouchableOpacity
            onPress={handlePress}
            activeOpacity={0.7}
            className={`bg-primary-500 rounded-3xl min-h-[42px] flex flex-row justify-center items-center shadow-md shadow-slate-950  ${containerStyles} ${
                isLoading ? "opacity-50":""
            }`}
            disabled={isLoading}
        >
            <Image
                source={icons.google_icon}
                className="justify-start w-[24px] h-[24px] px-5 ml-3"
                resizeMode='contain'
            />
            <Text className={`text-primary-600 font-mbold text-sm mr-5 ${textStyles}`}>
                {title}
            </Text>
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

export default GoogleButton;