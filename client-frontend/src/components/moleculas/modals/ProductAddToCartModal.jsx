import React, {useEffect, useState} from 'react'
import {getProductByIdApi} from "../../../api/ProductApi";
import {baseURL} from "../../../auth/ApiClient";
import {useAuth} from "../../../auth/AuthContext";
import {useNavigate} from "react-router-dom";
import QuantityInput from "../../atoms/input/QuantityInput";
import {useCart} from "../../../contexts/CartContext";
import {useTranslation} from "react-i18next";
import BaseModal from "../../atoms/BaseModal";

const ProductAddToCartModal = ({isModalOpen, toggleModal, setIsModalOpen, productId}) => {

    const navigate = useNavigate();

    const [product, setProduct] = useState(null);
    const [quantity, setQuantity] = useState(1);
    const {updateCartItemQuantity} = useCart()

    const {isAuthenticated} = useAuth();
    const {t} = useTranslation();

    const getProductById = (productId) => {
        getProductByIdApi(productId)
            .then((res) => {
                setProduct(res.data);
            })
            .catch((err) => console.log(err))
    }

    const addItemToCart = (productId, quantity) => {
        updateCartItemQuantity(productId, quantity)
    }

    useEffect(() => {
        if (productId) {
            getProductById(productId)
            setQuantity(1)
        }
    }, [productId]);

    const updateQuantity = (input) => {
        setQuantity((prevQuantity) => Math.max(1, prevQuantity + input));
    }

    const handleAddToCart = () => {
        if (isAuthenticated) {
            addItemToCart(product.id, quantity);
        } else {
            navigate("/login");
        }
        setIsModalOpen(false);
    }

    return (
        <div>
            <BaseModal
                isModalOpen={isModalOpen}
                toggleModal={toggleModal}
                children={product &&
                    <div>
                        <div className="bg-white px-8 pb-4 pt-5 dark:bg-[#0F172A]">
                            <div>
                                <div className="flex flex-col">
                                    <div className="flex justify-between">
                                        <div>
                                            <img src={`${baseURL}${product.imageName}`}
                                                 alt=""
                                                 className="object-cover w-44 h-auto mx-auto rounded-lg dark:mb-2"
                                            />
                                        </div>
                                        <div className="flex items-center text-zinc-800 dark:text-zinc-100">
                                            <QuantityInput
                                                quantity={quantity}
                                                onQuantityChanged={updateQuantity}
                                            />
                                        </div>
                                    </div>
                                    <div className="flex justify-between border-t border-t-zinc-300">
                                        <div className="mt-2 mr-4">
                                            <h2 className="text-lg font-bold text-gray-900 dark:text-zinc-100">{product.name}</h2>
                                            <p className="mt-1 text-xs text-gray-700  dark:text-zinc-300">Price
                                                per {t(`enums.unitOfMeasure.${product.unitOfMeasure}`)}: {product.price} RON</p>
                                            <div className="mt-7">
                                                <p className="mt-1 text-xs text-gray-700  dark:text-zinc-300">{product.seller.city}</p>
                                                <p className="mt-1 text-xs text-gray-700  dark:text-zinc-300">{product.seller.alias}</p>

                                            </div>

                                        </div>
                                        <div className="flex items-center">
                                            <p className="text-sm text-zinc-800 dark:text-zinc-100">{(product.price * quantity).toFixed(2)} RON</p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div
                            className="flex justify-end border px-4 py-3 dark:bg-[#0F172A] dark:border-t-zinc-300 dark:border-x-[#0F172A] dark:border-b-[#0F172A]">
                            <button type="button"
                                    className="text-white items-end bg-gradient-to-r from-indigo-500 via-indigo-600 to-indigo-700 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-indigo-300 dark:focus:ring-indigo-800 shadow-lg shadow-indigo-500/50 dark:shadow-lg dark:shadow-indigo-800/80 font-medium rounded-lg text-sm px-3 py-1.5 text-center mr-2 mb-2"
                                    onClick={() => handleAddToCart()}>
                                Add to cart
                            </button>
                        </div>
                    </div>}
            />

        </div>
    )
}

export default ProductAddToCartModal;