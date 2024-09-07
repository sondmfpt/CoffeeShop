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
            },
            backgroundImage: {
                'login-bg': "url('../static/img/pexels-jonathanborba-2878712.jpg')",
            }
        },
    },
    plugins: [],
}

