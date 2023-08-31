import { api } from '../ApiClient'

export const getAllCategoriesApi = () => {
    return api.get("/categories")
}