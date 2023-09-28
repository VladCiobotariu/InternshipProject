import React, {useEffect, useState} from "react";
import {useAuth} from "../../auth/AuthContext";
import {Link, useLocation, useNavigate} from "react-router-dom";
import CartItemCard from "../moleculas/cart/CartItemCard";
import ShippingAddressesComponent from "../moleculas/ShippingAddressesComponent";
import {submitOrder} from "../../api/OrderApi";
import BaseAlert from "../atoms/alerts/BaseAlert";
import {getBuyerAddresses} from "../../api/BuyerApi";
import {useCart} from "../../contexts/CartContext";
import useBreakpoint from "../../hooks/useBreakpoint";

function CheckoutPageComponent(){

    const {allCartItems, numberOfCartItems, cartTotalPrice} = useCart()

    const [alert, setAlert] = useState(null)
    const breakpoint = useBreakpoint()

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
        const checkoutItems =  allCartItems.map(item => {
            return {
                productId: item.product.id,
                quantity: item.quantity
            }
        })
        if(!!selectedShippingAddress){
            submitOrder(selectedShippingAddress,checkoutItems,username)
                .then(
                    () => {
                        setAlert({
                            type: "success",
                            title: "Order Placed",
                            paragraph: "You will be redirected..."
                        })
                        setTimeout(() => {
                            navigate('/account/cart')
                        }, 2000)
                    }
                )
                .catch(
                    (e) => {
                        console.log(e)
                    }
                )
        } else {
            setAlert({
                type: 'danger',
                title: "Something went wrong",
                paragraph: "Can't place an order without an address. Please select address or add a new one."
            })
        }

    }

    function popupInfoCloseButton(){
        setAlert(null)
    }

    useEffect(() => {
        if(username){
            getShippingAddresses()
        }
    }, [location, username]);

    return (
        <div className="mb-">

            {(!!alert && breakpoint==='sm') &&
                <BaseAlert type={alert.type} classname="sm:top-4 sm:mt-4" handleCloseButton={popupInfoCloseButton} title={alert.title} paragraph={alert.paragraph}/>
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

                    {(!!alert && breakpoint!=='sm') &&
                        <BaseAlert type={alert.type} classname="-top-6 right-0" handleCloseButton={popupInfoCloseButton} title={alert.title} paragraph={alert.paragraph}/>
                    }

                    <p className="text-xl font-medium md:mt-10 lg:mt-10 xl:mt-10 2xl:mt-10">Order Summary</p>
                    {numberOfCartItems!==0 &&
                        <div className="mt-4">
                            {allCartItems.map((item)=>(
                                <CartItemCard key={item.id} item={item}/>
                            ))}
                        </div>
                    }

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