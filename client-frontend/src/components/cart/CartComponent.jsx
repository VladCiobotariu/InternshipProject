import {getCartItems} from "../../security/api/BuyerApi";
import React, {useEffect, useState} from "react";
import {baseURL} from "../../security/ApiClient";
import {useAuth} from "../../security/AuthContext";
import {useLocation} from "react-router-dom";
import QuantityInput from "./QuantityInput";
import useBreakpoint from "../../hooks/useBreakpoint";

function Cart(){

    const [cartItems, setCartItems] = useState([])
    const [cartTotalPrice, setCartTotalPrice] = useState(0)

    const {username} = useAuth()
    const breakpoint = useBreakpoint()

    function getCartItemsList(){
        getCartItems()
            .then(
                (response) => {
                    setCartItems(response.data.cartItems)
                    setCartTotalPrice(response.data.totalCartPrice)
                    console.log(response.data)
                }
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
        <div className="h-screen pt-10">
            <h1 className="mb-10 text-center text-2xl font-bold dark:text-white">Cart</h1>
                <div className="mx-auto max-w-5xl px-6 md:flex md:space-x-6 xl:px-0 lg:flex lg:space-x-6 xl:flex xl:space-x-8 2xl:flex 2xl:space-x-8">
                    <div className="rounded-lg md:w-2/3 lg:w-2/3 xl:w-2/3 2xl:w-2/3">
                        {cartItems.map((item)=>(
                            <div key={item.id} className="sm:justify-between mb-6 rounded-2xl dark:bg-[#192235] p-6 shadow-md flex justify-start">
                                <div>
                                    <img src={`${baseURL}${item.product.imageName}`}
                                         alt=""
                                         className="rounded-lg md:w-40 sm:h-20 sm:w-auto lg:w-52 w-56"
                                    />
                                    {breakpoint==='sm' &&
                                        <div className="mt-4">
                                            <QuantityInput inputQuantity={item.quantity}/>
                                        </div>
                                    }
                                </div>

                                {breakpoint!=='sm' &&
                                    <div className="ml-4 flex justify-between w-full">
                                        <div className="mt-0">
                                            <h2 className="sm:text-right text-lg font-bold text-gray-900 dark:text-white">{item.product.name}</h2>
                                            <p className="sm:invisible mt-1 text-xs text-gray-700 dark:text-gray-200">{item.product.description}</p>
                                        </div>
                                        <div className="flex flex-col justify-between mt-0">
                                            <div className="flex items-center border-gray-100">
                                                <QuantityInput inputQuantity={item.quantity}/>
                                            </div>
                                            <div className="flex items-center justify-end gap-2">
                                                <p className="text-sm">{item.product.price} RON</p>
                                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="h-5 w-5 cursor-pointer duration-150 hover:text-red-500">
                                                    <path strokeLinecap="round" strokeLinejoin="round" d="M6 18L18 6M6 6l12 12" />
                                                </svg>
                                            </div>
                                        </div>
                                    </div>
                                }

                                {breakpoint==='sm' &&
                                    <div className="flex flex-col justify-between">
                                        <div className="mt-0">
                                            <h2 className="text-right text-lg font-bold text-gray-900 dark:text-white">{item.product.name}</h2>
                                        </div>
                                        <div className="flex justify-between mt-0 mb-3">
                                            <div className="flex items-center gap-2">
                                                <p className="text-sm">{item.product.price} RON</p>
                                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="h-5 w-5 cursor-pointer duration-150 hover:text-red-500">
                                                    <path strokeLinecap="round" strokeLinejoin="round" d="M6 18L18 6M6 6l12 12" />
                                                </svg>
                                            </div>
                                        </div>
                                    </div>
                                }

                            </div>
                        ))}
                    </div>

                    <div className="dark:text-white mt-6 h-full rounded-lg bg-white dark:bg-[#192235] p-6 shadow-md md:mt-0 md:w-1/3 lg:mt-0 lg:w-1/3 xl:mt-0 xl:w-1/3 2xl:mt-0 2xl:w-1/3">
                        <div className="mb-2 flex justify-between">
                            <p>Subtotal</p>
                            <p>$129.99</p>
                        </div>
                        <div className="flex justify-between">
                            <p>Shipping</p>
                            <p>$4.99</p>
                        </div>
                        <hr className="my-4" />
                        <div className="flex justify-between">
                            <p className="text-lg font-bold">Total</p>
                            <div className="">
                                <p className="mb-1 text-lg font-bold">{cartTotalPrice} RON</p>
                            </div>
                        </div>
                        <button className="mt-6 w-full rounded-md bg-blue-500 py-1.5 font-medium text-blue-50 hover:bg-blue-600">Check out</button>
                    </div>

                </div>
        </div>
    )
}

export default Cart