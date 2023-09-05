import {createContext, useContext, useEffect} from "react";
import { api } from "./ApiClient";
import {executeJwtAuthenticationService, registerApiService} from "./AuthenticationApiService";
import {useSessionStorage} from "../hooks/useSessionStorage";

const authContext = createContext(undefined)
export const useAuth = () => useContext(authContext)


function AuthProvider({children}){

    const[isAuthenticated, setAuthenticated] = useSessionStorage("isAuthenticated", false)
    const[username, setUsername] = useSessionStorage("username", "")
    const[token, setToken] = useSessionStorage("token", "")

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
        window.location.reload()
    }

    return (
        <authContext.Provider value={{isAuthenticated, login, logout, registerUser, username, token}}>
            {children}
        </authContext.Provider>
    )
}

export default AuthProvider