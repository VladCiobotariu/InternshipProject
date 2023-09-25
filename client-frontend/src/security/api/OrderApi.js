import {api} from "../ApiClient";

export function submitOrder(shippingAddress, products, email){
    return api.post('/order', {
        shippingAddress: shippingAddress,
        products: products,
        email: email
    })
}