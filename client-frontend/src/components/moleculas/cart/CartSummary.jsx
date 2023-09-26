import React from "react";
import {useNavigate} from "react-router-dom";

function CartSummary({cartTotalPrice, shippingPrice}){

    const navigate = useNavigate()

    return(
        <div className="dark:text-white mt-6 h-full rounded-lg bg-white dark:bg-[#192235] p-6 shadow-md md:mt-0 md:w-1/3 lg:mt-0 lg:w-1/3 xl:mt-0 xl:w-1/3 2xl:mt-0 2xl:w-1/3">
            <div className="mb-2 flex justify-between">
                <p>Subtotal</p>
                <p>{cartTotalPrice} RON</p>
            </div>
            <div className="flex justify-between">
                <p>Shipping</p>
                <p>{shippingPrice} RON</p>
            </div>
            <hr className="my-4" />
            <div className="flex justify-between">
                <p className="text-lg font-bold">Total</p>
                <div className="">
                    <p className="mb-1 text-lg font-bold">{cartTotalPrice + shippingPrice} RON</p>
                </div>
            </div>
            <button onClick={()=>navigate('/checkout')} className="mt-6 w-full rounded-md bg-blue-500 py-1.5 font-medium text-blue-50 hover:bg-blue-600">Check out</button>
        </div>
    )
}

export default CartSummary