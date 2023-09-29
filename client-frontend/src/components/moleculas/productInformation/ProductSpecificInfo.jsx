import React from 'react'

const ProductSpecificInfo = ({label, information}) => {

    return (
        <div>
            <div className="flex justify-between items-center">
                <p className="font-medium text-base leading-4 text-zinc-600 dark:text-zinc-100">{label}</p>
                <div className="flex">{information}</div>
            </div>
            <hr className="bg-gray-200 w-full my-4"/>
        </div>
    )
}

export default ProductSpecificInfo;