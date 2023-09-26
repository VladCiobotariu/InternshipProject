import { api } from '../auth/ApiClient'

export const getImageForCategoryApi = (imageURL) => {
    return api.get(`${imageURL}`, { responseType: 'arraybuffer' })
}