import { api } from '../ApiClient'

export const getAllProducts = () => {
    return api.get("/products")
}

export const getAllProductsByCategoryNamePageable = (categoryName, page, itemsPerPage) => {
    return api.get(`/products`, {params: { categoryName: categoryName, page: page, itemsPerPage: itemsPerPage }})
}