/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    screens: require('./src/tailwind.screens.js'),
    fontSize: {
      'xs': '0.9rem',
      'xxs': '0.58rem',
      'md': '1rem',
      'lg': '1.1rem',
      'xl': '1.2rem',
      '2xl': '1.563rem',
      '3xl': '1.953rem',
      '4xl': '2.441rem',
      '5xl': '3.052rem',
      '6xl': '3.507rem',
    }
  },

  plugins: [
    require('@tailwindcss/forms'),
    require('@tailwindcss/aspect-ratio'),
  ],
}

