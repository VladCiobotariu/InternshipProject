import {getCartItems} from "../../api/CartApi";
import React, {useEffect, useState} from "react";
import {useAuth} from "../../auth/AuthContext";
import {useLocation} from "react-router-dom";
import CartItemCard from "../moleculas/cart/CartItemCard";
import ShippingAddressesComponent from "../moleculas/ShippingAddressesComponent";
import {submitOrder} from "../../api/OrderApi";

function CheckoutPageComponent(){

    const [cartItems, setCartItems] = useState([])
    const [cartTotalPrice, setCartTotalPrice] = useState(0)

    const checkoutItems =  cartItems.map(item => {
        return {
            productId: item.id,
            quantity: item.quantity
        }
    })

    const [shippingAddress, setShippingAddress] = useState(null)

    const {username} = useAuth()
    const location = useLocation()

    const shippingPrice = 10

    function getCartItemsList() {
        getCartItems()
            .then(
                (response) => {
                    setCartItems(response.data.cartItems)
                    setCartTotalPrice(response.data.totalCartPrice)
                }
            )
            .catch(
                (err) => console.log(err)
            )
    }

    function callback(shippingAddress){
        setShippingAddress(shippingAddress)
    }

    function handlePlaceOrder(){

        submitOrder(shippingAddress,checkoutItems,username)
            .catch(
                (e) => {
                    console.log(e)
                }
            )
    }

    useEffect(() => {
        if(username){
            getCartItemsList()
        }
    }, [location, username]);

    return (
        <div className="sm:block flex justify-center mt-10 md:space-x-8 lg:space-x-8 xl:space-x-8 2xl:space-x-8 mx-8">

            <div>

            </div>

            <div className="sm:mt-8 sm:w-full w-1/2 max-w-lg sm:mx-auto">
                <ShippingAddressesComponent onClick={callback}/>
            </div>

            <div className="w-1/2 max-w-lg sm:w-full sm:mx-auto sm:mt-10">

                <p className="text-xl font-medium">Order Summary</p>
                <div className="mt-4">
                    {cartItems.map((item)=>(
                        <CartItemCard key={item.id} item={item} getCartItemsList={getCartItemsList}/>
                    ))}
                </div>

                <div className="dark:text-white mt-6 rounded-lg bg-white dark:bg-[#192235] p-6 shadow-md mb-14">
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
                    <button onClick={handlePlaceOrder} className="mt-6 w-full rounded-md bg-blue-500 py-1.5 font-medium text-blue-50 hover:bg-blue-600">Place Order</button>
                </div>

            </div>
        </div>
    )
}

export default CheckoutPageComponent