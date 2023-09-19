import React from 'react'

const FilterTag = ({filterName, value, removeFilter}) => {

    const customLabels = {
        priceTo: "Price To",
        priceFrom: "Price From",
        cityName: "City",
        categoryName: "Category"
    };

    return (
        <div>
            {value ? (
                <div className="bg-red-100 rounded-xl px-10 inline-flex">
                    {customLabels[filterName] || filterName}: {value}

                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5"
                         stroke="currentColor" className="w-5 h-5 cursor-pointer mt-0.5 pl-1"
                         onClick={removeFilter}>
                        <path strokeLinecap="round" strokeLinejoin="round" d="M6 18L18 6M6 6l12 12"/>
                    </svg>

                </div>
            ) : null}
        </div>
    )
}

export default FilterTag;