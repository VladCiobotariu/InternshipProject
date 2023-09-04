import {getCartItemsByEmail} from "../../security/api/BuyerApi";
import React, {useEffect, useState} from "react";
import {baseURL} from "../../security/ApiClient";
import {useAuth} from "../../security/AuthContext";
import {useLocation} from "react-router-dom";


function Cart(){

    const [cartItems, setCartItems] = useState([])
    const {username} = useAuth()

    function getCartItems(email){
        getCartItemsByEmail(email)
            .then(
                (response) => setCartItems(response.data)
            )
            .catch(
                (err) => console.log(err)
            )
    }

    const location = useLocation()

    useEffect(() => {
        console.log('use effect get cart items')
        if(username){
            getCartItems(username)
        }
    }, [location, username]);

    return(
        <div className="flex min-h-full flex-1 justify-center px-6 py-12">
            <div className="mx-auto pt-10">

                <div className="grid grid-cols-3 gap-8 dark:bg-[#192235] rounded-lg px-8">

                    {cartItems.map((item)=>(
                        <div key={item.id}>

                            <div className="flex h-11 w-11 flex-none items-center justify-center rounded-lg bg-gray-50 group-hover:bg-white dark:bg-transparent dark:group-hover:bg-transparent dark:bg-zinc-800">
                                <img
                                    // src={`${baseURL}${item.getProduct().imageName}`}
                                    alt={item.name}
                                    className="h-6 w-6" aria-hidden="true"
                                />
                            </div>

                            <div>
                                {item.quantity}
                            </div>

                        </div>
                    ))}



                </div>

            </div>
        </div>
    )
}

export default Cart