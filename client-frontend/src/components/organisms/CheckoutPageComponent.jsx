import React, {useEffect, useState} from "react";
import {useAuth} from "../../auth/AuthContext";
import {Link, useLocation, useNavigate} from "react-router-dom";
import CartItemCard from "../moleculas/cart/CartItemCard";
import ShippingAddressesComponent from "../moleculas/ShippingAddressesComponent";
import {submitOrder} from "../../api/OrderApi";
import PopupSuccessAlert from "../atoms/alerts/PopupSuccessAlert";
import DangerAlert from "../atoms/alerts/DangerAlert";
import {getBuyerAddresses} from "../../api/BuyerApi";
import {useCart} from "../../contexts/CartContext";

function CheckoutPageComponent(){

    const {allCartItems, cartTotalPrice} = useCart()

    const [infoAlertShowing, setInfoAlertShowing] = useState(false)
    const [dangerAlertShowing, setDangerAlertShowing] = useState(false)

    const checkoutItems =  allCartItems.map(item => {
        return {
            productId: item.product.id,
            quantity: item.quantity
        }
    })

    const [shippingAddresses, setShippingAddresses]= useState([])
    const [selectedShippingAddress, setSelectedShippingAddress] = useState(null)

    const {username} = useAuth()
    const location = useLocation()
    const navigate = useNavigate()

    const shippingPrice = 10

    function getShippingAddresses(){
        getBuyerAddresses()
            .then(
                (response) => {
                    setShippingAddresses(response.data)
                    setSelectedShippingAddress(response.data[0])
                }
            )
            .catch(
                (err) => console.log(err)
            )
    }

    function handleAddressSelected(shippingAddress){
        setSelectedShippingAddress(shippingAddress)
    }

    function handlePlaceOrder(){
        if(!!selectedShippingAddress){
            console.log(checkoutItems)
            submitOrder(selectedShippingAddress,checkoutItems,username)
                .then(
                    () => {
                        setInfoAlertShowing(true)
                        setTimeout(() => navigate('/account/cart'), 2000)
                    }
                )
                .catch(
                    (e) => {
                        console.log(e)
                    }
                )
        } else {
            setDangerAlertShowing(true)
        }

    }

    function popupInfoCloseButton(){
        setInfoAlertShowing(false)
        navigate('/account/cart')
    }

    useEffect(() => {
        if(username){
            getShippingAddresses()
        }
    }, [location, username]);

    return (
        // todo type for alert
        <div className="mb-">

            {!!dangerAlertShowing &&
                <DangerAlert className="top-[86px] right-5 sm:top-2 sm:mt-4" paragraph="To place an order you nedd to selet a shipping address or add a new address" />
            }

            <div className="sm:block flex justify-center mt-10 md:space-x-8 lg:space-x-8 xl:space-x-8 2xl:space-x-8 mx-8">

                <div className="sm:mt-8 sm:w-full w-1/2 max-w-lg sm:mx-auto">
                    <Link to="/account/cart" className="text-sm font-semibold leading-6 text-inherit dark:text-inherit">
                        <span aria-hidden="true">&larr;</span> Cart
                    </Link>
                    <div className="mt-4">
                        <ShippingAddressesComponent shippingAddresses={shippingAddresses}
                                                    selectedShippingAddress={selectedShippingAddress}
                                                    onAddressSelected={handleAddressSelected}/>
                    </div>
                </div>

                <div className="w-1/2 max-w-lg sm:w-full sm:mx-auto sm:mt-10 relative">

                    {!!infoAlertShowing &&
                        <div>
                            <PopupSuccessAlert classname="-top-6 right-0 sm:top-2 sm:mt-4" handleCloseButton={popupInfoCloseButton} title="Order Placed" paragraph="You will be redirected..."/>
                        </div>
                    }

                    <p className="text-xl font-medium md:mt-10 lg:mt-10 xl:mt-10 2xl:mt-10">Order Summary</p>
                    <div className="mt-4">
                        {allCartItems.map((item)=>(
                            <CartItemCard key={item.id} item={item}/>
                        ))}
                    </div>

                    <div className="dark:text-white mt-6 rounded-2xl bg-white dark:bg-[#192235] p-6 shadow-md mb-14">
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
        </div>
    )
}

export default CheckoutPageComponent