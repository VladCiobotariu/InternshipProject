import React, { useState } from 'react';

const PriceFilterComponent = ({onClickInside, togglePriceFilter, handlePriceChanged, getPriceFrom, getPriceTo }) => {

    // todo - name RangeFilterComponent
    const [priceFrom, setPriceFrom] = useState(getPriceFrom || '');
    const [priceTo, setPriceTo] = useState(getPriceTo || '');

    const onPriceFromChanged = (e) => {
        setPriceFrom(e.target.value);
    }

    const onPriceToChanged = (e) => {
        setPriceTo(e.target.value);
    }

    const handleSavePrices = () => {
        // todo - display error message
        if(priceFrom && priceTo) {
            if (parseFloat(priceFrom) <= parseFloat(priceTo)) {
                handlePriceChanged(priceFrom, priceTo);
                togglePriceFilter();
            } else {
                console.log("bad credentials");
            }
        } else if(parseFloat(priceFrom) || parseFloat(priceTo)) {
            handlePriceChanged(priceFrom, priceTo);
            togglePriceFilter();
        }
        else {
            console.log("bad cred");
        }
    }

    return (
        <div onClick={onClickInside}>
            <div className="w-80 rounded border border-zinc-300 bg-white">
                <div className="px-2 py-2 bg-white rounded-md shadow-lg dark-mode:bg-gray-700">
                    <div className="flex justify-between items-center gap-10">
                        <div>
                            <label htmlFor="priceFrom"
                                   className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                                Price From
                            </label>
                            <input type="text" id="priceFrom"
                                   className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                   placeholder="from.."
                                   value={priceFrom}
                                   onChange={onPriceFromChanged}
                            />
                        </div>
                        <div>
                            <label htmlFor="priceTo"
                                   className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                                Price To
                            </label>
                            <input type="text" id="priceTo"
                                   className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                   placeholder="to.."
                                   value={priceTo}
                                   onChange={onPriceToChanged}
                            />
                        </div>
                    </div>
                    <button type="button"
                            className="focus:outline-none text-white bg-purple-700 hover:bg-purple-800 focus:ring-4 focus:ring-purple-300 font-medium rounded-lg text-sm px-5 py-2.5 mb-2 dark:bg-purple-600 dark:hover:bg-purple-700 dark:focus:ring-purple-900"
                            onClick={handleSavePrices}>
                        Save
                    </button>
                </div>
            </div>
        </div>
    );
};

export default PriceFilterComponent;
