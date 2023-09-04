import { api } from '../ApiClient'

export const getAllCategoriesApi = () => {
    return api.get("/categories")
}

export const getAllCategoriesByItemsPerPageAndPage = (page, itemsPerPage) => {
    return api.get("/categories", {params: { page: page, itemsPerPage: itemsPerPage }})
}