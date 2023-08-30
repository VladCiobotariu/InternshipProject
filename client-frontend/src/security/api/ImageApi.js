import { api } from '../ApiClient'

export const getImageForCategoryApi = (imageURL) => {
    return api.get(`${imageURL}`, { responseType: 'arraybuffer' })
}