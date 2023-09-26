import {useEffect, useState} from 'react'
import { RadioGroup } from '@headlessui/react'
import {getBuyerAddresses} from "../../api/BuyerApi";
import {useAuth} from "../../auth/AuthContext";
import {useLocation} from "react-router-dom";
import AddressComponent from "./AddressComponent";


const ShippingAddressesComponent = ({onClick, className}) => {

    const [shippingAddresses, setShippingAddresses] = useState([])
    const [plan, setPlan] = useState(null)

    const {username} = useAuth()
    const location = useLocation()

    function handelSelect(value){
        setPlan(value)
        onClick(value)
    }

    function getShippingAddresses(){

        /**
         * @param{{
         *
         *  }} data
         */

        getBuyerAddresses()
            .then(
                (response) => {
                    setShippingAddresses(response.data)
                    setPlan(response.data[0])
                    onClick(response.data[0])
                }
            )
            .catch(
                (err) => console.log(err)
            )
    }

    useEffect(() => {
        if(username){
            getShippingAddresses()
        }
    }, [location, username]);

    return (
        <>
            <RadioGroup value={plan} onChange={handelSelect} className={className}>
                <RadioGroup.Label className="text-xl font-bold">Addresses</RadioGroup.Label>
                {shippingAddresses.map((item) => (
                    <RadioGroup.Option key={item.id} value={item} className="mt-4">
                        {({ checked }) => (
                            <AddressComponent item={item} checked={checked} editFunction={()=>console.log("pressed")}/>
                        )}
                    </RadioGroup.Option>
                ))}
            </RadioGroup>
        </>
    )
}

export default ShippingAddressesComponent