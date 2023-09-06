import React from 'react';
import {useNavigate} from "react-router-dom";

// product are - id, description, imageName, name, price, category.name, seller.alias
const ProductComponent = ({product}) => {

    const navigate = useNavigate();

    return (
        <div>
            <li className="flex justify-center items-center">


            <a href="#" className="block group">
                <img
                    src="https://images.unsplash.com/photo-1592921870789-04563d55041c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
                    alt=""
                    className="object-cover w-64 h-64 rounded aspect-square"
                    // onClick={() => navigate("/")}
                />

                <div className="mt-3">
                    <h3
                        className="font-medium text-zinc-800 group-hover:underline group-hover:underline-offset-4"
                    >
                        Simple Watch
                    </h3>

                    <p className="mt-1 text-sm text-zinc-600">$150</p>
                </div>
            </a>
            </li>
        </div>
    )
}
export default ProductComponent;