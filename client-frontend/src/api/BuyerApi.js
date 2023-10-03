import {api} from "../auth/ApiClient";

export function getFavorites(){
    return api.get(`/my-favorites`)
}

export function getBuyerAddresses(){
    return api.get("/my-buyer-addresses")
}

export function addFavorite(productId) {
    return api.put('/my-favorites', {},{
        params: {
            productId
        }
    })
}

export function removeFavorite(productId){
    return api.delete('/my-favorites',{
        params:{
            productId
        }
    })
}

export function updateShippingAddress(shippingAddress){
    return api.put('/my-buyer-addresses', shippingAddress)
}
