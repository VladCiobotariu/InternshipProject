import {api} from "../ApiClient";

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

export function modifyCartItemQuantity(productId, quantity){
    return api.put('/my-cart',{}, {
        params:{
            productId,
            quantity
        }
    })
}

export function getFavorites(){
    return api.get(`/my-favorites`)
}

export function removeFavorite(productId){
    return api.delete('/my-favorites',{
        params:{
            productId
        }
    })
}