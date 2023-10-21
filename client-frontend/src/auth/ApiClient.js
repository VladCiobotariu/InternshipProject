import axios from 'axios'
import qs from "qs";

// export const baseURL = 'http://localhost:8080'
// export const baseURL = 'http://piazza.eu-central-1.elasticbeanstalk.com/'
export const baseURL = process.env.REACT_APP_BACKEND_URL

export const api = axios.create(
    {
        baseURL: baseURL,
        paramsSerializer: {
            serialize: (params) => {
               return qs.stringify(params, { arrayFormat: 'brackets' })
            }
        }
    },
)

api.interceptors.request.use(
    (config) => {
        const item = sessionStorage.getItem('token');
        const newItem = JSON.parse(item)

        if(newItem){
            config.headers.Authorization= newItem
        }
        return config
    }
)