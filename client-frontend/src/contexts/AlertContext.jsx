import {createContext, useContext, useState} from "react";
import BaseAlert from "../components/atoms/alerts/BaseAlert";

const AlertContext = createContext(undefined)
export const useAlert = () => useContext(AlertContext)

const AlertProvider = ({children}) => {

    const [alert, setAlert] = useState(null)
    const [timeoutId, setTimeoutId] = useState(0)

    const pushAlert = (newAlert) => {
        if(!!timeoutId){
            clearTimeout(timeoutId);
        }
        setAlert(newAlert)
        if(newAlert.type==='success'){
            setTimeoutId(setTimeout(()=>setAlert(null), 2000));
        }
    }

    function clearAlert(){
        setAlert(null)
    }

    return(
        <AlertContext.Provider value={{pushAlert, clearAlert}}>
            {!!alert && <BaseAlert type={alert.type} onCloseButton={clearAlert} title={alert.title} paragraph={alert.paragraph}/>}
            {children}
        </AlertContext.Provider>
    )
}

export default AlertProvider