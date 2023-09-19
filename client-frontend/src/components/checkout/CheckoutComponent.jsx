import {getCartItems, removeCartItem} from "../../security/api/BuyerApi";
import React, {useEffect, useState} from "react";
import {useAuth} from "../../security/AuthContext";
import {useLocation} from "react-router-dom";
import CartItemCard from "../cart/CartItemCard";
import {checkoutSchema} from "./checkoutSchema"
import {Field, Form, Formik} from "formik";
import ErrorField from "../error/ErrorField";
import TextInputWithError from "../input/TextInputWithError";

function CheckoutComponent(){

    const [cartItems, setCartItems] = useState([])
    const [cartTotalPrice, setCartTotalPrice] = useState(0)

    const {username} = useAuth()
    const location = useLocation()

    const shippingPrice = 10

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
                }
            )
            .catch(
                (err) => console.log(err)
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

    async function onSubmit(values){
        // await processOrder(values.email, values.password, values.firstName, values.lastName, values.telephone, values.image)
        //     .then(
        //         ()=>navigate('/login')
        //     )
        //     .catch(
        //         (error) => console.log(error.response.data)
        //     )
        console.log(values)
    }

    useEffect(() => {
        if(username){
            getCartItemsList()
        }
    }, [location, username]);

    return (
        <div className="grid sm:mx-4 mx-6 lg:grid-cols-2 xl:grid-cols-2 2xl:grid-cols-2 lg:px-20 xl:px-32 2xl:px-40 mt-10">

            <div className="px-4 sm:mt-8 md:mt-8">

                <p className="text-xl font-medium">Contact Information</p>
                <div className="">
                    <Formik
                        initialValues={{
                            email: "",
                            firstName: "",
                            lastName: "",
                            addressLine1: "",
                            addressLine2: "",
                            city: "",
                            country: "",
                            state: "",
                            postalCode: "",
                            telephone: ""
                        }}
                        onSubmit={onSubmit}
                        validationSchema={checkoutSchema}
                        validateOnBlur={false}
                        validateOnChange={false}
                    >

                        {({ errors, validateField }) => {

                            return (
                                <Form>

                                    <div className='mt-4'>
                                        <TextInputWithError fieldName={'email'}
                                                            errorName={errors.email}
                                                            labelName={'Email address'}
                                                            onBlur={()=>validateField('email')}/>
                                    </div>

                                    <hr
                                        className="my-4 h-0.5 border-t-0 bg-neutral-100 dark:bg-neutral-700 opacity-100 dark:opacity-50" />

                                    <div className="space-y-2">

                                        <div className="flex flex-row">
                                            <TextInputWithError fieldName={'firstName'}
                                                                errorName={errors.firstName}
                                                                labelName={'First Name'}
                                                                onBlur={()=>validateField('firstName')}
                                                                fieldType={"text"}/>

                                            <TextInputWithError fieldName={'lastName'}
                                                                errorName={errors.lastName}
                                                                labelName={'Last Name'}
                                                                onBlur={()=>validateField('lastName')}
                                                                fieldType={"text"}/>
                                        </div>

                                        <TextInputWithError fieldName={'telephone'}
                                                            errorName={errors.telephone}
                                                            labelName={'Telephone'}
                                                            onBlur={()=>validateField('telephone')}
                                                            fieldType={"tel"}/>
                                    </div>

                                </Form>
                            );
                        }}
                    </Formik>
                </div>

            </div>

            <div className="sm:mr-0">

                <p className="text-xl font-medium">Order Summary</p>
                <div className="mt-4 px-2 ">
                    {cartItems.map((item)=>(
                        <CartItemCard key={item.id} item={item} getCartItemsList={getCartItemsList} handelDeleteCartItem={handelDeleteCartItem}/>
                    ))}
                </div>

                <div className="dark:text-white mt-6 rounded-lg bg-white dark:bg-[#192235] p-6 shadow-md">
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
                    <button onClick={()=> null} className="mt-6 w-full rounded-md bg-blue-500 py-1.5 font-medium text-blue-50 hover:bg-blue-600">Place Order</button>
                </div>

            </div>
        </div>
    )
}

export default CheckoutComponent