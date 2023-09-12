import { api } from '../ApiClient'

export const getAllProducts = () => {
    return api.get("/products")
}

export const getAllProductsPageable = (page, itemsPerPage) => {
    return api.get(`/products`, {params: { page: page, itemsPerPage: itemsPerPage }})
}

// TODO - check that if you dont pass categoryName than all products would be retrieved,
// if yes, than rename this to getAllProductsPageable and delete the request above
export const getAllProductsByCategoryNamePageable = (categoryName, page, itemsPerPage) => {
    return api.get(`/products`, {params: { categoryName: categoryName, page: page, itemsPerPage: itemsPerPage }})
}