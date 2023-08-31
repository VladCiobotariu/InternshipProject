import {api} from "../security/ApiClient";

export async function retrieveSingleUserByEmail(email){
    return api.get(`/users/${email}`)
}