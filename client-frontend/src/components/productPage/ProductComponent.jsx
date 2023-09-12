import React from 'react';
import {useNavigate} from "react-router-dom";
import {baseURL} from "../../security/ApiClient";

// product are - id, description, imageName, name, price, category.name, seller.alias
const ProductComponent = ({name, imageName, price, categoryName, sellerAlias}) => {

    const navigate = useNavigate();

    return (
        <div>
            <li className="flex mb-10 h-full">


            <a className=" group bg-white border border-zinc-300 rounded-xl w-full flex flex-col justify-between">
                <div className="relative aspect-square overflow-hidden border-b-2 cursor-pointer rounded-xl ">
                    <img
                        src={`${baseURL}${imageName}`}
                        alt={name}
                        className="object-cover w-48 h-auto mx-auto"
                        onClick={() => navigate(`/products/categories/fruits/${name}`)}
                    />
                </div>


                <div className="flex items-center justify-between mx-3 my-3">
                    <div className="">
                        <h3
                            className="font-bold text-xl text-zinc-800 cursor-pointer group-hover:underline group-hover:underline-offset-4"
                            onClick={() => navigate(`/products/categories/fruits/${name}`)}
                        >
                            {name}
                        </h3>

                        <p className="mt-1 text-lg  text-zinc-600">${price}</p>
                    </div>
                    <div>
                        <button type="button"
                                className="text-white bg-gradient-to-r from-indigo-500 via-indigo-600 to-indigo-700 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-indigo-300 dark:focus:ring-indigo-800 shadow-lg shadow-indigo-500/50 dark:shadow-lg dark:shadow-indigo-800/80 font-medium rounded-lg text-sm px-3 py-1.5 text-center mr-2 mb-2"
                                onClick={() => null}>
                            Add to cart
                        </button>
                    </div>
                </div>

            </a>
            </li>
        </div>
    )
}
export default ProductComponent;