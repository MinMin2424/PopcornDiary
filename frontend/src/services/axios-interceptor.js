import axios from 'axios';
import AuthService from "./auth.service";

const api = axios.create({
    baseURL: '/api'
});

api.interceptors.request.use(
    (config) => {
        const user = JSON.parse(localStorage.getItem('user'));
        if (user && user.token) {
            config.headers.Authorization = `Bearer ${user.token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && error.response.status === 401) {
            AuthService.logout();
            window.location.reload();
        }
        return Promise.reject(error);
    }
);

export default api;