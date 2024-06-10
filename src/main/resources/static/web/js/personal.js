const { createApp } = Vue;

createApp({
    data() {
        return {
            clientInfo: {
                firstName: '',
                lastName: '',
                email: '',
                Password: '',
            },
            editing: false,
            editForm: {
                firstName: '',
                lastName: '',
                email: '',
                password: '',
            },
            showPassword: false,
            errorMsg: '',
        };
    },
    mounted() {
        this.getData();
    },
    methods: {
        getData() {
            axios.get('/api/clients/current')
                .then((response) => {
                    this.clientInfo = response.data;
                    this.editForm = {
                        firstName: this.clientInfo.firstName,
                        lastName: this.clientInfo.lastName,
                        email: this.clientInfo.email,
                        password: this.clientInfo.password,
                    };
                })
                .catch((error) => {
                    console.error('Error getting data:', error);
                    this.showToast('Error getting client data');
                });
        },
         signOut: function () {
                    axios.post('/api/logout')
                        .then(response => window.location.href = "/web/index.html")
                        .catch(() => {
                            this.errorMsg = "Sign out failed"
                            this.errorToats.show();
                        })
         },

        editData() {
            this.editing = true;
        },
        updateData() {
            axios.put('/api/clients/current', this.editForm)
                .then((response) => {
                    this.clientInfo = response.data;
                    this.editing = false;
                    this.showToast('Data updated successfully');
                })
                .catch((error) => {
                    console.error('Error updating data:', error);
                    this.showToast('Error updating data');
                });
        },
        cancelEdit() {
            this.editing = false;
            this.editForm = {
                firstName: this.clientInfo.firstName,
                lastName: this.clientInfo.lastName,
                email: this.clientInfo.email,
                password: this.clientInfo.plainPassword,
            };
        },
        togglePassword() {
            this.showPassword = !this.showPassword;
        },
        showToast(message) {
            this.errorMsg = message;
            const toastEl = document.getElementById('danger-toast');
            const toast = new bootstrap.Toast(toastEl);
            toast.show();
        },
    },
}).mount('#app');
