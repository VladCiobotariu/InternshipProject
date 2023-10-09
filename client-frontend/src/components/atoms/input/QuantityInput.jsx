function QuantityInput({quantity, onQuantityChanged}){

    return(
        <div>
            <label htmlFor="Quantity" className="sr-only"> Quantity </label>

            <div className="flex items-center border border-gray-200 rounded">
                <button
                    onClick={()=>onQuantityChanged(-1)}
                    type="button"
                    className="dark:text-white dark:bg-[#192235] sm:bg-blue-500 dark:hover:text-black hover:text-white w-10 h-10 leading-10 text-gray-600 transition hover:bg-blue-500 dark:hover:bg-white"
                >
                    -
                </button>

                <div
                    id="quantity"
                    className="flex items-center justify-center dark:bg-[#192235] h-10 w-16 border-transparent text-center [-moz-appearance:_textfield] sm:text-sm [&::-webkit-outer-spin-button]:m-0 [&::-webkit-outer-spin-button]:appearance-none [&::-webkit-inner-spin-button]:m-0 [&::-webkit-inner-spin-button]:appearance-none">
                    {quantity}
                </div>

                <button
                    onClick={()=>onQuantityChanged(1)}
                    type="button"
                    className="dark:text-white dark:bg-[#192235] sm:bg-blue-500 dark:hover:text-black hover:text-white
                     w-10 h-10 leading-10 text-gray-600 transition hover:bg-blue-500 dark:hover:bg-white"
                >
                    +
                </button>
            </div>
        </div>
    )
}

export default QuantityInput;