import { createContext, useContext, useState } from "react";
import { api } from "./ApiClient";
import {executeJwtAuthenticationService, registerApiService} from "./AuthenticationApiService";

const authContext = createContext(undefined)
export const useAuth = () => useContext(authContext)


function AuthProvider({children}){

    const[isAuthenticated, setAuthenticated] = useState(false)
    const[username, setUsername] = useState(null)
    const[token, setToken] = useState(null)

    async function registerUser(email, password, firstName, lastName, telephone, image){
        const {status} = await registerApiService(email, password, firstName, lastName, telephone, image)
        if(status===201){
            return true
        }else{
            logout()
            return false
        }
    }

    async function login(username, password){

        try{
            const {status, data:{token: jwtToken}} = await executeJwtAuthenticationService(username, password)

            if(status===200){
                setAuthenticated(true)

                const newToken = 'Bearer ' + jwtToken;
                setToken(newToken)

                api.interceptors.request.use(
                    (config) => {
                        config.headers.Authorization=newToken
                        return config
                    }
                )
                setUsername(username)

                return true
            }

            else{
                logout()
                return false
            }

        }catch(error){
            logout()
            return false
        }
    }

    function logout(){
        setToken(null)
        setAuthenticated(false)
        setUsername(null)
    }

    return (
        <authContext.Provider value={{isAuthenticated, login, logout, registerUser, username, token}}>
            {children}
        </authContext.Provider>
    )
}

export default AuthProvider