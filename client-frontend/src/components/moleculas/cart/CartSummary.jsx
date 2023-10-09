import React from "react";

function CartSummary({cartTotalPrice, shippingPrice, children, className}){

    return(
        <div className={`dark:text-white mt-6 rounded-lg bg-white dark:bg-[#192235] p-6 shadow-md ${className}`}>
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
            {children}
        </div>
    )
}

export default CartSummary