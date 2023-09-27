import React, {useState} from 'react'
import QuantityInput from "../../atoms/input/QuantityInput";

const ProductDetails = () => {

    const [quantity, setQuantity] = useState(1);

    const updateQuantity = (input) => {
        setQuantity((prevQuantity) => Math.max(1, prevQuantity + input));
    }

    return (
        <div className="flex-col">
            <p className=" font-normal text-base leading-7 text-gray-600 mt-20 sm:mt-8">It is a long established
                fact
                that a reader will be distracted by the readable content of a page when looking at its
                layout.
                The point of using. Lorem Ipsum is that it has a more-or-less normal distribution of
                letters.</p>
            <p className=" font-semibold text-xl leading-5 mt-6">$ 790.89</p>

            <div className="mt-10">
                <div className="flex justify-between items-center">
                    <p className="font-medium text-base leading-4 text-gray-600">Category</p>
                    <div className="flex">Fruits</div>
                </div>
                <hr className="bg-gray-200 w-full my-4"/>
                <div className="flex justify-between items-center">
                    <p className="font-medium text-base leading-4 text-gray-600">Producer</p>
                    <div className="flex">SellerAlias</div>
                </div>
                <hr className="bg-gray-200 w-full my-3"/>
                <div className="flex justify-between items-center">
                    <p className="font-medium text-base leading-4 text-gray-600">Select quantity</p>
                    <div className="flex">
                        <QuantityInput
                            quantity={quantity}
                            functionToBeCalled={updateQuantity}
                        />
                    </div>
                </div>
                <hr className="bg-gray-200 w-full my-3"/>

            </div>
            <button
                className="font-medium text-base leading-4 text-white bg-gray-800 w-full py-5 mt-6">
                Add to cart
            </button>
        </div>
    )
}

export default ProductDetails;