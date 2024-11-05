Vue.createApp({
    data() {
        return {
            email: "",
            password: "",
            firstName: "",
            lastName: "",
            errorToats: null,
            errorMsg: "",
            showSignUp: false,
        }
    },
    methods: {
        signIn: function (event) {
            event.preventDefault();
            let config = {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            }
            axios.post('/api/login', `email=${this.email}&password=${this.password}`, config)
                .then(response => window.location.href = "/web/accounts.html")
                .catch((error) => {
                    if (error.response && error.response.status === 401) {
                        alert("Error al iniciar sesión. Verifica la información e intenta nuevamente.");
                    } else {
                        alert("Error al iniciar sesión. Verifica la información e intenta nuevamente.");
                    }
                    this.errorMsg = "Sign in failed, check the information";
                    this.errorToats.show();
                    setTimeout(() => {
                        window.location.href = "/web/index.html";
                    }, 1000); // Retraso de 1 segundo para mostrar el alert
                });
        },
        signUp: function (event) {
            event.preventDefault();
            let config = {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            }
            axios.post('/api/clients', `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`, config)
                .then(() => { this.signIn(event) })
                .catch((error) => {
                    console.log(error); // Agregar esta línea para manejar los errores
                    this.errorMsg = "Sign up failed, check the information";
                    this.errorToats.show();
                });
        },
        showSignUpToogle: function () {
            this.showSignUp = !this.showSignUp;
        },
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        }
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
    }
}).mount('#app');
