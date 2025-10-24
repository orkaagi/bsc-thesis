/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./register.html",
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        poppins: ['Poppins', 'sans-serif'],
        merriweather: ['Merriweather', 'serif']
      }
    },
  },
  plugins: [],
}

