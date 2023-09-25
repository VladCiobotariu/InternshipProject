import React, {Fragment, useState, useRef, useEffect} from 'react'
import {Dialog, Transition} from "@headlessui/react";
import {getProductByNameApi} from "../../security/api/ProductApi";
import {baseURL} from "../../security/ApiClient";
import {useAuth} from "../../security/AuthContext";
import {useNavigate} from "react-router-dom";
import QuantityInput from "../input/QuantityInput";
import {addOrUpdateCartItem} from "../../security/api/CartApi";

const ProductAddToCartModal = ({setIsModalOpen, productName}) => {

    const navigate = useNavigate();

    const [product, setProduct] = useState(null);
    const [quantity, setQuantity] = useState(1);

    const {isAuthenticated} = useAuth();

    const getProductByName = (productName) => {
        getProductByNameApi(productName)
            .then((res) => {
                setProduct(res.data);
            })
            .catch((err) => console.log(err))
    }

    const addItemToCart = (productId, quantity) => {
        addOrUpdateCartItem(productId, quantity)
            .then((res) => {
                console.log(res.data)
            })
            .catch((err) => console.log(err))
    }

    useEffect(() => {
        if (productName) {
            getProductByName(productName)
            setQuantity(1)
        }
    }, [productName]);

    const updateQuantity = (input) => {
        setQuantity((prevQuantity) => Math.max(1, prevQuantity + input));
    }

    const handleAddToCart = () => {
        setIsModalOpen(false);
        if (isAuthenticated) {
            addItemToCart(product.id, quantity);
        } else {
            navigate("/login");
        }
    }

    return (
        <div>
            {product &&
                <div>
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
                </div>
            }
        </div>
    )
}

export default ProductAddToCartModal;