import { api } from '../auth/ApiClient'

export const getReviewsApi = (productId) => {
    return api.get(`/products/${productId}/reviews`)
}