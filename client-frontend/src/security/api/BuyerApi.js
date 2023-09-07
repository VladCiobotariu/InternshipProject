import {api} from "../ApiClient";

export function getCartItems(){
    return api.get(`/my-cart`)
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