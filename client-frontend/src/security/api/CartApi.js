import { api } from '../ApiClient'

export const addOrUpdateCartItem = (productId, quantity) => {

    return api.put(`/my-cart`, {}, {
        params: {
            productId: productId,
            quantity: quantity
        }
    });

}