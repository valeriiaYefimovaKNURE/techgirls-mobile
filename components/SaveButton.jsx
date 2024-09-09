import { Text, View, ActivityIndicator, TouchableOpacity} from 'react-native'

const SaveButton=({
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
            className={`bg-primary-default rounded-3xl min-h-[48px] flex flex-row justify-center items-center shadow-md shadow-slate-950  
                ${containerStyles} 
                ${isLoading ? "opacity-50":""}`
            }
            disabled={isLoading}
        >
            <Text className={`text-white font-mbold text-sm ${textStyles}`}>
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

export default SaveButton;