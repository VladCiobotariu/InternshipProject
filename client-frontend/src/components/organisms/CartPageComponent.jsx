import React from "react";

import CartItemCard from "../moleculas/cart/CartItemCard";
import CartSummary from "../moleculas/cart/CartSummary";
import ErrorComponent from "../moleculas/error/ErrorComponent";
import {useCart} from "../../contexts/CartContext";

function Cart(){

    const {allCartItems, numberOfCartItems, cartTotalPrice} = useCart()
    const shippingPrice = 10

    return(
        <div className="pt-10">

            <h1 className="mb-10 text-center text-2xl font-bold dark:text-white">Cart</h1>

            {numberOfCartItems!==0 &&
                <div className="mx-auto max-w-5xl px-6 md:flex md:space-x-6 xl:px-0 lg:flex lg:space-x-6 xl:flex xl:space-x-8 2xl:flex 2xl:space-x-8">
                    <div className="rounded-lg md:w-2/3 lg:w-2/3 xl:w-2/3 2xl:w-2/3">
                        {allCartItems.map((cartItem)=>(
                            <CartItemCard key={cartItem.id} item={cartItem} isModifiable={true}/>
                        ))}
                    </div>
                    <CartSummary cartTotalPrice={cartTotalPrice} shippingPrice={shippingPrice}/>
                </div>
            }

            {(numberOfCartItems===0 && allCartItems!==null) &&
                <ErrorComponent description="You don't have any items in Cart. Add Items in the shop:" solution="Shop now!" linkTo='/products'/>
            }

        </div>
    )
}

export default Cart