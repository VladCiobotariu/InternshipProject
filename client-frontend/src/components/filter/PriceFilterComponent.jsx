import React, { useState } from 'react';

const PriceFilterComponent = () => {
    const [minPrice, setMinPrice] = useState(1);
    const [maxPrice, setMaxPrice] = useState(100);
    const [minThumb, setMinThumb] = useState(0);
    const [maxThumb, setMaxThumb] = useState(0);
    const min = 0;
    const max = 101;

    const minTrigger = (event) => {
        const newMinPrice = Math.min(parseInt(event.target.value), maxPrice - 500);
        const newMinThumb = ((newMinPrice - min) / (max - min)) * 100;
        setMinPrice(newMinPrice);
        setMinThumb(newMinThumb);
    };

    const maxTrigger = (event) => {
        const newMaxPrice = Math.max(parseInt(event.target.value), minPrice + 500);
        const newMaxThumb = 100 - ((newMaxPrice - min) / (max - min)) * 100;
        setMaxPrice(newMaxPrice);
        setMaxThumb(newMaxThumb);
    };

    return (
        <div>
            <div className="w-56 rounded border border-zinc-300 bg-white">
                <div className="px-2 pt-2 pb-2 bg-white rounded-md shadow-lg dark-mode:bg-gray-700">
                    <div className="mt-10 flex justify-center items-center">
                        <div className="relative max-w-xl w-full">
                            <div>
                                <input
                                    type="range"
                                    step="100"
                                    min={min}
                                    max={max}
                                    onChange={minTrigger}
                                    value={minPrice}
                                    className="absolute pointer-events-none appearance-none z-20 h-2 w-full opacity-0 cursor-pointer"
                                />

                                <input
                                    type="range"
                                    step="100"
                                    min={min}
                                    max={max}
                                    onChange={maxTrigger}
                                    value={maxPrice}
                                    className="absolute pointer-events-none appearance-none z-20 h-2 w-full opacity-0 cursor-pointer"
                                />

                                <div className="relative z-10 h-2">
                                    <div className="absolute z-10 left-0 right-0 bottom-0 top-0 rounded-md bg-gray-200"></div>
                                    <div
                                        className="absolute z-20 top-0 bottom-0 rounded-md bg-green-300"
                                        style={{ right: maxThumb + '%', left: minThumb + '%' }}
                                    ></div>
                                    <div className="absolute z-30 w-6 h-6 top-0 left-0 bg-green-300 rounded-full -mt-2 -ml-1" style={{ left: minThumb + '%' }}></div>
                                    <div className="absolute z-30 w-6 h-6 top-0 right-0 bg-green-300 rounded-full -mt-2 -mr-3" style={{ right: maxThumb + '%' }}></div>
                                </div>
                            </div>

                            <div className="flex justify-between items-center py-5">
                                <div>
                                    <input
                                        type="text"
                                        maxLength="5"
                                        onChange={minTrigger}
                                        value={minPrice}
                                        className="px-3 py-2 border border-gray-200 rounded w-24 text-center"
                                    />
                                </div>
                                <div>
                                    <input
                                        type="text"
                                        maxLength="5"
                                        onChange={maxTrigger}
                                        value={maxPrice}
                                        className="px-3 py-2 border border-gray-200 rounded w-24 text-center"
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default PriceFilterComponent;
