import {useLocation} from "react-router-dom";
import {useAuth} from "./AuthContext";
import {useEffect} from "react";

const parseJwt = (token) => {
    try {
        return JSON.parse(atob(token.split(".")[1]));
    } catch (e) {
        return null;
    }
};

export const AuthVerify = () => {

    let location = useLocation();
    const {token, logout} = useAuth()

    useEffect(() => {
        if (token) {
            const decodedJwt = parseJwt(token);

            if (decodedJwt.exp * 1000 < Date.now()) {
                console.log('error')
                logout()
            }
        }
    }, [location, token]);
}