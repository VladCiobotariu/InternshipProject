import { api } from '../auth/ApiClient'

export const getAllCategoriesApi = () => {
    return api.get("/categories")
}

export const getAllCategoriesByItemsPerPageAndPage = (page, itemsPerPage) => {
    return api.get("/categories", {params: { page: page, itemsPerPage: itemsPerPage }})
}

export const getAllCategoryNames = () => {
    return api.get("/categories/categoryNames")
}