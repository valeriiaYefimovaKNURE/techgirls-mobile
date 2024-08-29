/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./app/**/*.{js,jsx,ts,tsx}", "./components/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      colors:{
        primary:{
          default:'#a883b9',
          100:'#decfe8',
          200:'#d8c0f4',
          300:'#ecd3ed',
          400:'#ad88c6',
          500:'#ECE6F0',
          600:'#65558F'
        },
        bistre:'#34211b',
        grey:{
          default:'#5E6368',
          100:'#c0c0c0',
          200:'#CDCDE0'
        }
      },
      fontFamily:{
        uregular:["Ubuntu-Regular", "sans-serif"],
        ubold:["Ubuntu-Bold","sans-serif"],
        umedium:["Ubuntu-Medium","sans-serif"],
        mbold:["Manrope-Bold","sans-serif"],
        mregular:["Manrope-Regular", "sans-serif"],
        mmedium:["Manrope-Medium","sans-serif"],
        mextrabold:["Manrope-ExtraBold","sans-serif"],
        msemibold:["Manrope-SemiBold","sans-serif"],
        mlight:["Manrope-Light","sans-serif"],
        mextralight:["Manrope-ExtraLight","sans-serif"]
      },
      height: {
        '1/2': '50%',
        '1/3': '33%',
        '2/3': '67%',
        '1/4': '25%',
        '3/4': '75%',
        '1/9':'90%',
        'full': '100%',
      }
    },
  },
  plugins: [],
}

