import {getCartItems, removeCartItem} from "../../api/CartApi";
import React, {useEffect, useState} from "react";
import {useAuth} from "../../auth/AuthContext";
import {useLocation} from "react-router-dom";

import CartItemCard from "../moleculas/cart/CartItemCard";
import CartSummary from "../moleculas/cart/CartSummary";
import ErrorComponent from "../moleculas/error/ErrorComponent";

function Cart(){

    const [cartItems, setCartItems] = useState([])
    const [cartTotalPrice, setCartTotalPrice] = useState(0)
    const [isLoading, setIsLoading] = useState(true)

    const shippingPrice = 10

    const {username} = useAuth()
    const location = useLocation()


    function getCartItemsList() {

        /**
         * @param{{
         *          cartItems:[],
         *          totalCartPrice:float
         *  }} data
         */

        getCartItems()
            .then(
                (response) => {
                    setCartItems(response.data.cartItems)
                    setCartTotalPrice(response.data.totalCartPrice)
                    setIsLoading(false)
                }
            )
            .catch(
                () => {
                    setCartItems([])
                }
            )
    }

    function handelDeleteCartItem(productId){
        removeCartItem(productId)
            .then(
                async () => {
                    getCartItemsList()
                }
            )
            .catch(
                (err) => console.log(err)
            )
    }

    useEffect(() => {
        if(username){
            getCartItemsList()
        }
    }, [location, username]);

    return(
        <div className="pt-10">

            <h1 className="mb-10 text-center text-2xl font-bold dark:text-white">Cart</h1>

            {cartItems.length!==0 &&
                <div className="mx-auto max-w-5xl px-6 md:flex md:space-x-6 xl:px-0 lg:flex lg:space-x-6 xl:flex xl:space-x-8 2xl:flex 2xl:space-x-8">
                    <div className="rounded-lg md:w-2/3 lg:w-2/3 xl:w-2/3 2xl:w-2/3">
                        {cartItems.map((item)=>(
                            <CartItemCard key={item.id} item={item} getCartItemsList={getCartItemsList} handelDeleteCartItem={handelDeleteCartItem} isModifiable={true}/>
                        ))}
                    </div>
                    <CartSummary cartTotalPrice={cartTotalPrice} shippingPrice={shippingPrice}/>
                </div>
            }

            {(cartItems.length===0 && !isLoading) &&
                <ErrorComponent description="You don't have any items in Cart. Add Items in the shop:" solution="Shop now!" linkTo='/products'/>
            }

        </div>
    )
}

export default Cart