import {api} from "../auth/ApiClient";

export function submitOrder(shippingAddress, products, email){
    return api.post('/order', {
        shippingAddress: shippingAddress,
        products: products,
        email: email
    })
}