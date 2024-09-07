/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js}"],
  theme: {
    extend: {
      colors: {
        'coffee': {
          200: '#eee1c4',
          500: '#dcb386',
          700: '#9e826c' //hover
        }
      },
      fontFamily: {
        'serif': "'Abril Fatface', serif"
      }
    },
  },
  plugins: [],
}

