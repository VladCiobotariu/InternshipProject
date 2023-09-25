import {api} from "../auth/ApiClient";

export function getCartItems(){
    return api.get(`/my-cart`)
}

export function removeCartItem(productId){
    return api.delete('/my-cart',{
        params:{
            productId
        }
    })
}

export function addOrUpdateCartItem(productId, quantity){
    return api.put('/my-cart',{}, {
        params:{
            productId,
            quantity
        }
    })
}