import React, {useState} from 'react'
import QuantityInput from "../../atoms/input/QuantityInput";
import ProductSpecificInfo from "./ProductSpecificInfo";
import {useCart} from "../../../contexts/CartContext";
import {useAuth} from "../../../auth/AuthContext";
import {useNavigate} from "react-router-dom";
import PopupSuccessAlert from "../../atoms/alerts/PopupSuccessAlert";
import AddRemoveWishlist from "../../atoms/buttons/AddRemoveWishlist";

const ProductInformation = ({description, price, category, producer, city, productId}) => {

    const [quantity, setQuantity] = useState(1);
    const {updateCartItemQuantity} = useCart()
    const {isAuthenticated} = useAuth();
    const navigate = useNavigate();
    const [infoAlertShowing, setInfoAlertShowing] = useState(false)

    const updateQuantity = (input) => {
        setQuantity((prevQuantity) => Math.max(1, prevQuantity + input));
    }

    const addItemToCart = (productId, quantity) => {
        updateCartItemQuantity(productId, quantity)
    }

    const handleAddToCart = () => {
        if (isAuthenticated) {
            addItemToCart(productId, quantity);
            setInfoAlertShowing(true);
            setTimeout(() => navigate('/account/cart'), 2000)
        } else {
            navigate("/login");
        }
    }

    function popupInfoCloseButton(){
        setInfoAlertShowing(false)
        navigate('/products')
    }

    return (
        <div className="flex-col">
            <p className=" font-normal text-base leading-7 text-zinc-600 mt-20 sm:mt-8 dark:text-zinc-100">
                {description}
            </p>
            <p className=" font-semibold text-xl leading-5 mt-6">{price} RON</p>

            {!!infoAlertShowing &&
                <div>
                    <PopupSuccessAlert classname="top-20 mt-12 right-8 sm:top-2 sm:mt-4" handleCloseButton={popupInfoCloseButton} title="Item added" paragraph="You will be redirected..."/>
                </div>
            }

            <div className="mt-10">
                <ProductSpecificInfo
                    label="Category"
                    information={category}
                />
                <ProductSpecificInfo
                    label="Producer"
                    information={producer}
                />
                <ProductSpecificInfo
                    label="City"
                    information={city}
                />
                <div className="flex justify-between items-center">
                    <p className="font-bold text-base leading-4 text-zinc-600 dark:text-zinc-100">Quantity</p>
                    <div className="flex">
                        <QuantityInput
                            quantity={quantity}
                            onQuantityChanged={updateQuantity}
                        />
                    </div>
                </div>
                <hr className="bg-zinc-200 w-full my-3"/>

            </div>
            <div className="flex">
                <AddRemoveWishlist
                    productId={productId}
                />
                <button
                    className="font-medium rounded-lg text-base leading-4 text-white bg-indigo-600 w-full py-5 mt-6 border border-indigo-750 hover:bg-indigo-700 dark:border-indigo-900"
                    onClick={() => handleAddToCart()}>
                    Add to cart
                </button>
            </div>
        </div>
    )
}

export default ProductInformation;