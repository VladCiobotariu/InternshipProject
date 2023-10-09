import React from 'react'
import {useNavigate} from "react-router-dom";

const ProductHistorySteps = ({categoryName, productName}) => {

    const navigate = useNavigate()

    return (
        <div>
            <span className="cursor-pointer"
                  onClick={() => navigate('/')}>
                Home
            </span>
            <span> / </span>
            <span className="cursor-pointer"
                  onClick={() => navigate('/products')}>
                Products
            </span>
            <span> / </span>
            <span className="cursor-pointer"
                  onClick={() => navigate(`/products?categoryName=${categoryName}`)}>
                {categoryName}
            </span>
            <span> / </span>
            <span>{productName}</span>
        </div>
    )
}

export default ProductHistorySteps;