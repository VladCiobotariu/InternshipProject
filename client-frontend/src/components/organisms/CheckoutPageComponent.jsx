import React, {useEffect, useState} from "react";
import {useAuth} from "../../auth/AuthContext";
import {Link, useLocation, useNavigate} from "react-router-dom";
import CartItemCard from "../moleculas/cart/CartItemCard";
import ShippingAddressesComponent from "../moleculas/ShippingAddressesComponent";
import {submitOrder} from "../../api/OrderApi";
import {addShippingAddress, getBuyerAddresses, updateShippingAddress} from "../../api/BuyerApi";
import {useCart} from "../../contexts/CartContext";
import {useAlert} from "../../contexts/AlertContext";
import AddressForm from "../moleculas/forms/AddressForm";
import BaseModal from "../atoms/BaseModal";

function CheckoutPageComponent() {

    const {allCartItems, numberOfCartItems, cartTotalPrice, refreshCart} = useCart()
    const {pushAlert, clearAlert} = useAlert()

    const [shippingAddresses, setShippingAddresses] = useState([])
    const [selectedShippingAddress, setSelectedShippingAddress] = useState(null)
    const shippingPrice = 10

    const {username} = useAuth()
    const location = useLocation()
    const navigate = useNavigate()

    const [isModalOpen, setIsModalOpen] = useState(false);

    const toggleModal = () => {
        setIsModalOpen(!isModalOpen)
    }

    function getShippingAddresses() {
        getBuyerAddresses()
            .then(
                (response) => {
                    setShippingAddresses(response.data)
                    // setSelectedShippingAddress(response.data[0])  //todo modify
                }
            )
            .catch(
                (err) => console.log(err)
            )
    }

    function handleAddressSelected(shippingAddress) {
        setSelectedShippingAddress(shippingAddress)
        if (!!shippingAddress) {
            clearAlert()
        }
    }

    function handleAddAddress() {
        const obj = {
            id: 0,
            address: {
                country: "Romania",
                state: "",
                city: "",
                addressLine1: "",
                addressLine2: "",
                zipCode: "",
            },
            firstName: "",
            lastName: "",
            telephone: ""
        }
        setSelectedShippingAddress(obj)
        toggleModal()
    }

    function handleSaveForm(values) {
        if (values.id === 0) {
            addShippingAddress(values)
                .then(
                    () => {
                        getShippingAddresses()
                    }
                )
                .catch(
                    (err) => console.log(err)
                )
        } else if (JSON.stringify(values) !== JSON.stringify(selectedShippingAddress)) {
            updateShippingAddress(values)
                .then(
                    () => {
                        getShippingAddresses()
                    }
                )
                .catch(
                    (err) => console.log(err)
                )
        }
        setIsModalOpen(false)
    }

    function handlePlaceOrder() {

        const checkoutItems = allCartItems.map(item => {
            return {
                productId: item.product.id,
                quantity: item.quantity
            }
        })

        if (!!selectedShippingAddress) {
            submitOrder(selectedShippingAddress, checkoutItems, username)
                .then(
                    () => {
                        pushAlert({
                            type: "success",
                            title: "Order Placed",
                            paragraph: "You will be redirected..."
                        })
                        setTimeout(() => {
                            refreshCart()
                            navigate('/account/cart') //todo remove after alert context
                        }, 2000)
                    }
                )
                .catch(
                    (e) => {
                        console.log(e)
                    }
                )
        } else {
            pushAlert({
                type: 'danger',
                title: "Validation Error",
                paragraph: "Can't place an order without an address. Please select address or add a new one."
            })
        }

    }

    useEffect(() => {
        if (username) {
            getShippingAddresses()
        }
    }, [location, username]);

    return (
        <div className="">

            <div
                className="sm:block flex justify-center mt-10 md:space-x-8 lg:space-x-8 xl:space-x-8 2xl:space-x-8 mx-8">

                <div className="sm:mt-8 sm:w-full w-1/2 max-w-lg sm:mx-auto">
                    <Link to="/account/cart" className="text-sm font-semibold leading-6 text-inherit dark:text-inherit">
                        <span aria-hidden="true">&larr;</span> Cart
                    </Link>

                    <p className="text-xl font-bold mt-4">Order Summary</p>
                    {numberOfCartItems !== 0 &&
                        <div className="mt-6">
                            {allCartItems.map((item) => (
                                <CartItemCard key={item.id} item={item}/>
                            ))}
                        </div>
                    }
                </div>

                <div className="w-1/2 max-w-lg sm:w-full sm:mx-auto sm:mt-10 relative">

                    <div className="md:mt-6 lg:mt-6 xl:mt-6 2xl:mt-6">
                        <ShippingAddressesComponent shippingAddresses={shippingAddresses}
                                                    selectedShippingAddress={selectedShippingAddress}
                                                    onAddressSelected={handleAddressSelected}
                                                    toggleModal={() => toggleModal(selectedShippingAddress)}
                                                    onAddAddress={handleAddAddress}
                        />
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
                        <hr className="my-4"/>
                        <div className="flex justify-between">
                            <p className="text-lg font-bold">Total</p>
                            <div className="">
                                <p className="mb-1 text-lg font-bold">{cartTotalPrice + shippingPrice} RON</p>
                            </div>
                        </div>
                        <button onClick={handlePlaceOrder}
                                className="mt-6 w-full rounded-md bg-blue-500 py-1.5 font-medium text-blue-50 hover:bg-blue-600">Place
                            Order
                        </button>
                    </div>

                </div>
            </div>

            <BaseModal isModalOpen={isModalOpen} toggleModal={toggleModal}>
                <AddressForm onSaveForm={handleSaveForm} shippingAddress={selectedShippingAddress}/>
            </BaseModal>

        </div>
    )
}

export default CheckoutPageComponent