import React, {useState} from 'react'
import QuantityInput from "../../atoms/input/QuantityInput";

const ProductDetails = () => {

    const [quantity, setQuantity] = useState(1);

    const updateQuantity = (input) => {
        setQuantity((prevQuantity) => Math.max(1, prevQuantity + input));
    }

    return (
        <div className="flex-col">
            <p className=" font-normal text-base leading-7 text-zinc-600 mt-20 sm:mt-8 dark:text-zinc-100">It is a long established
                fact
                that a reader will be distracted by the readable content of a page when looking at its
                layout.
                The point of using. Lorem Ipsum is that it has a more-or-less normal distribution of
                letters.</p>
            <p className=" font-semibold text-xl leading-5 mt-6">$ 790.89</p>

            <div className="mt-10">
                <div className="flex justify-between items-center">
                    <p className="font-medium text-base leading-4 text-zinc-600 dark:text-zinc-100">Category</p>
                    <div className="flex">Fruits</div>
                </div>
                <hr className="bg-gray-200 w-full my-4"/>
                <div className="flex justify-between items-center">
                    <p className="font-medium text-base leading-4 text-zinc-600 dark:text-zinc-100">Producer</p>
                    <div className="flex">SellerAlias</div>
                </div>
                <hr className="bg-gray-200 w-full my-3"/>
                <div className="flex justify-between items-center">
                    <p className="font-medium text-base leading-4 text-zinc-600 dark:text-zinc-100">Quantity</p>
                    <div className="flex">
                        <QuantityInput
                            quantity={quantity}
                            functionToBeCalled={updateQuantity}
                        />
                    </div>
                </div>
                <hr className="bg-zinc-200 w-full my-3"/>

            </div>
            <button
                className="font-medium text-base leading-4 text-white bg-indigo-600 w-full py-5 mt-6 border border-indigo-750 hover:bg-indigo-700 dark:border-indigo-900">
                Add to cart
            </button>
        </div>
    )
}

export default ProductDetails;