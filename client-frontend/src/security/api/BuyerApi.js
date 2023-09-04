import {api} from "../ApiClient";

export function getCartItemsByEmail(email){
    return api.get(`/users/${email}/cart`)
}