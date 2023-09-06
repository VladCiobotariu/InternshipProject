import {getCartItems} from "../../security/api/BuyerApi";
import React, {useEffect, useState} from "react";
import {baseURL} from "../../security/ApiClient";
import {useAuth} from "../../security/AuthContext";
import {useLocation} from "react-router-dom";


function Cart(){

    const [cartItems, setCartItems] = useState([])
    const {username} = useAuth()

    function getCartItemsList(){
        getCartItems()
            .then(
                (response) => setCartItems(response.data)
            )
            .catch(
                (err) => console.log(err)
            )
    }

    const location = useLocation()

    useEffect(() => {
        if(username){
            getCartItemsList()
        }
    }, [location, username]);

    return(
        <div className="flex px-6 py-12">
            <div className="mx-auto pt-10">

                {cartItems.map((item)=>(
                    <div key={item.id} className="grid grid-cols-4 gap-8 dark:bg-[#192235] rounded-lg px-6 my-10 py-4"
                    style={{
                        // width: "500px"
                    }}>

                        <div className="">
                            <img
                                src={`${baseURL}/${item.product.imageName}`}
                                alt=""
                                className="h-20 w-20"
                            />
                        </div>

                        <div>
                            {item.product.name}
                        </div>

                        <div>
                            <div>{item.product.price}</div>
                            <div>{item.quantity}</div>
                        </div>

                        <div>

                        </div>

                    </div>
                ))}

            </div>
        </div>
    )
}

export default Cart