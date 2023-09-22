import React, {Fragment, useState, useRef, useEffect} from 'react'
import {Dialog, Transition} from "@headlessui/react";
import {getProductByNameApi} from "../../security/api/ProductApi";
import {baseURL} from "../../security/ApiClient";
import {useAuth} from "../../security/AuthContext";
import {useNavigate} from "react-router-dom";
import QuantityInput from "../input/QuantityInput";

// product are - id, description, imageName, name, price, category.name, seller.alias
const ProductAddToCartModal = ({toggleModal, isModalOpen, setIsModalOpen, productName}) => {

    const cancelButtonRef = useRef(null)
    const navigate = useNavigate();

    const [product, setProduct] = useState(null);
    const [quantity, setQuantity] = useState(1);

    const { isAuthenticated, username } = useAuth();

    const getProductByName = (productName) => {
        getProductByNameApi(productName)
            .then((res) => {
                setProduct(res.data);
            })
            .catch((err) => console.log(err))
    }

    useEffect(() => {
        if(productName) {
            getProductByName(productName)
            setQuantity(1)
        }
    }, [productName]);

    const updateQuantity = (input) => {
        setQuantity((prevQuantity) => Math.max(1, prevQuantity + input));
    }

    const handleAddToCart = () => {
        setIsModalOpen(false);
        if(isAuthenticated) {
            console.log("is authenticated");
        } else {
            navigate("/login");
        }
    }

    return (
        <div className="">
            {product && (
                <Transition.Root show={isModalOpen} as={Fragment}>
                    <Dialog
                        as="div"
                        className="relative z-1 sm:p-5"
                        initialFocus={cancelButtonRef}
                        onClose={toggleModal}
                    >
                        <Transition.Child
                            as={Fragment}
                            enter="ease-out duration-300"
                            enterFrom="opacity-0"
                            enterTo="opacity-100"
                            leave="ease-in duration-200"
                            leaveFrom="opacity-100"
                            leaveTo="opacity-0"
                        >
                            <div className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" />
                        </Transition.Child>

                        <div className="fixed inset-0 z-10 w-full h-full flex items-center justify-center">
                            <Transition.Child
                                as={Fragment}
                                enter="ease-out duration-300"
                                enterFrom="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
                                enterTo="opacity-100 translate-y-0 sm:scale-100"
                                leave="ease-in duration-200"
                                leaveFrom="opacity-100 translate-y-0 sm:scale-100"
                                leaveTo="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
                            >
                                <Dialog.Panel className="relative w-[500px] transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:w-[350px]">
                                    <div className="bg-white px-8 pb-4 pt-5">
                                        <div className="">
                                            <div className="flex flex-col">
                                                <div className="flex justify-between">
                                                    <div>
                                                        <img src={`${baseURL}${product.imageName}`}
                                                             alt=""
                                                             className="object-cover w-44 h-auto mx-auto rounded-lg"
                                                        />
                                                    </div>
                                                    <div className="flex items-center text-zinc-800">
                                                        <QuantityInput
                                                            quantity={quantity}
                                                            functionToBeCalled={updateQuantity}
                                                        />
                                                    </div>
                                                </div>
                                                <div className="flex justify-between border-t border-t-zinc-300">
                                                    <div className="mt-2 mr-4">
                                                        <h2 className="text-lg font-bold text-gray-900">{product.name}</h2>
                                                        <p className="mt-1 text-xs text-gray-700">{product.description}</p>
                                                        <div className="mt-7">
                                                            <p className="mt-1 text-xs text-gray-700">{product.seller.city}</p>
                                                            <p className="mt-1 text-xs text-gray-700">{product.seller.alias}</p>

                                                        </div>

                                                    </div>
                                                    <div className="flex items-center">
                                                        <p className="text-sm text-zinc-800">{(product.price * quantity).toFixed(2)} RON</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                    <div className="flex justify-end border border-t-zinc-300 px-4 py-3">
                                        <button type="button"
                                                className="text-white items-end bg-gradient-to-r from-indigo-500 via-indigo-600 to-indigo-700 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-indigo-300 dark:focus:ring-indigo-800 shadow-lg shadow-indigo-500/50 dark:shadow-lg dark:shadow-indigo-800/80 font-medium rounded-lg text-sm px-3 py-1.5 text-center mr-2 mb-2"
                                                onClick={() => handleAddToCart()}>
                                            Add to cart
                                        </button>
                                    </div>
                                </Dialog.Panel>
                            </Transition.Child>
                        </div>
                    </Dialog>
                </Transition.Root>
            )}

        </div>
    )
}

export default ProductAddToCartModal;