import axios from 'axios'

export const baseURL = 'http://localhost:8080'
export const api = axios.create(
    {
        baseURL: baseURL
    }
)

api.interceptors.request.use(
    (config) => {
        const item = sessionStorage.getItem('token');

        const newItem = JSON.parse(item)
        console.log(newItem)

        if(newItem){
            config.headers.Authorization= newItem
        }
        return config
    }
)