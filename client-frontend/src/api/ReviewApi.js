import {api} from '../auth/ApiClient'

export const getReviewsApi = (productId) => {
    return api.get(`/products/${productId}/reviews`)
}

export const getReviewByIdApi = (reviewId) => {
    return api.get(`/reviews/${reviewId}`)
}

export const updateReviewApi = (reviewId, description, rating) => {
    return api.put(`reviews/${reviewId}`, {
        description: description,
        rating: rating
    })
}