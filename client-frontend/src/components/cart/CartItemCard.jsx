import {baseURL} from "../../security/ApiClient";
import QuantityInput from "../input/QuantityInput";
import React, {useState} from "react";
import useBreakpoint from "../../hooks/useBreakpoint";
import {modifyCartItemQuantity} from "../../security/api/BuyerApi";

function CartItemCard({item, getCartItemsList, handelDeleteCartItem}){

    /**
     * @param {{
     *          product:
     *              {
     *                  id: long,
     *                  name: string,
     *                  description: string
     *              },
     *          quantity:float
     *       }} item
     */

    const breakpoint = useBreakpoint()

    const [quantity, setQuantity] = useState(item.quantity)

    function updateDataBaseQuantity(newQuantity){
        modifyCartItemQuantity(item.product.id, newQuantity)
            .then(
                () => {
                    getCartItemsList()
                    setQuantity(quantity + newQuantity)
                }
            )
            .catch(
                (err) => console.log(err)
            )
    }

    return(
        <div className="sm:justify-between mb-6 rounded-2xl dark:bg-[#192235] p-6 shadow-md flex justify-start overflow-hidden">

            <div className="sm:flex sm:flex-col sm:justify-between">
                <img src={`${baseURL}${item.product.imageName}`}
                     alt=""
                     className="rounded-lg md:w-40 sm:h-20 sm:w-20 w-44"
                />
                {breakpoint==='sm' &&
                    <div className="mt-4">
                        <QuantityInput quantity={quantity} functionToBeCalled={(newQuantity)=>updateDataBaseQuantity(newQuantity)}/>
                    </div>
                }
            </div>

            {breakpoint!=='sm' &&
                <div className="ml-4 flex justify-between w-full">
                    <div className="mt-0 mr-4">
                        <h2 className="sm:text-right text-lg font-bold text-gray-900 dark:text-white">{item.product.name}</h2>
                        <p className="mt-1 text-xs text-gray-700 dark:text-gray-200">{item.product.description}</p>
                    </div>
                    <div className="flex flex-col justify-between mt-0">
                        <div className="flex items-center border-gray-100">
                            <QuantityInput quantity={quantity} functionToBeCalled={(newQuantity)=>updateDataBaseQuantity(newQuantity)}/>
                        </div>
                        <div className="flex items-center justify-end gap-2">
                            <p className="text-sm">{item.product.price} RON</p>
                            <button onClick={()=>handelDeleteCartItem(item.product.id)}>
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="h-5 w-5 cursor-pointer duration-150 hover:text-red-500">
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M6 18L18 6M6 6l12 12" />
                                </svg>
                            </button>
                        </div>
                    </div>
                </div>
            }

            {breakpoint==='sm' &&
                <div className="flex flex-col justify-between items-end ml-4">
                    <div className="mt-0 mb-4">
                        <h2 className="text-right text-lg font-bold text-gray-900 dark:text-white">{item.product.name}</h2>
                        <p className="mt-1 text-xs text-gray-700 dark:text-gray-200">{item.product.description}</p>
                    </div>
                    <div className="flex justify-between mt-0 mb-3">
                        <div className="flex items-center gap-2 ml-4">
                            <p className="text-sm">{item.product.price} RON</p>
                            <button onClick={()=>handelDeleteCartItem(item.product.id)}>
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="h-5 w-5 cursor-pointer duration-150 hover:text-red-500">
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M6 18L18 6M6 6l12 12" />
                                </svg>
                            </button>
                        </div>
                    </div>
                </div>
            }
        </div>
    )
}

export default CartItemCard;