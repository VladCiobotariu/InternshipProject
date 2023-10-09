import React, {useEffect, useState} from 'react';
import {useNavigate} from "react-router-dom";
import {baseURL} from "../../auth/ApiClient";
import {useAuth} from "../../auth/AuthContext";
import {useFavorite} from "../../contexts/FavoriteContext";
import ProductRating from "../atoms/starReviews/ProductRating";


const ProductComponent = ({ id, name, imageName, price, sellerAlias, rating, toggleModal }) => {
    const navigate = useNavigate();
    const { isAuthenticated } = useAuth();

    const {allFavorites, addToFavorite, removeFromFavorite, checkIsFavorite} = useFavorite();

    const [isFavorite, setIsFavorite] = useState(false);

    useEffect(() => {
        setIsFavorite(checkIsFavorite(allFavorites, id));
    }, [allFavorites]);


    const toggleFavorite = () => {
        if (!isFavorite) {
            addToFavorite(id);
        } else {
            removeFromFavorite(id);
        }
    };

    return (
        <div>
            <li className="flex mb-10 h-full ">

                <a className=" group bg-white border border-zinc-300 rounded-xl w-full flex flex-col justify-between dark:border dark:border-[#312e81]">
                    <div className="relative aspect-square overflow-hidden border-b-2 cursor-pointer rounded-xl ">
                        <img
                            src={`${baseURL}${imageName}`}
                            alt={name}
                            className="object-cover w-48 h-48 mx-auto"
                            onClick={() => navigate(`/${sellerAlias}/products/${id}`)}
                        />
                        {isAuthenticated &&
                            <div className="absolute top-0 left-0 p-2">
                                <svg xmlns="http://www.w3.org/2000/svg" fill={isFavorite ? "rgb(244, 63, 94)" : "none"}
                                     viewBox="0 0 24 24" strokeWidth={1.5}
                                     stroke="currentColor" className="w-7 h-7 text-rose-500"
                                     onClick={toggleFavorite}>
                                    <path strokeLinecap="round" strokeLinejoin="round"
                                          d="M21 8.25c0-2.485-2.099-4.5-4.688-4.5-1.935 0-3.597 1.126-4.312 2.733-.715-1.607-2.377-2.733-4.313-2.733C5.1 3.75 3 5.765 3 8.25c0 7.22 9 12 9 12s9-4.78 9-12z"/>
                                </svg>
                            </div>
                        }
                    </div>


                    <div className="dark:bg-[#1a2747]">
                        <div className="flex items-center justify-between mx-3 my-3">
                            <div className="">
                                <h3
                                    className="font-bold text-xl text-zinc-800 cursor-pointer group-hover:underline group-hover:underline-offset-4 dark:text-white"
                                    onClick={() => navigate(`/${sellerAlias}/products/${id}`)}
                                >
                                    {name}
                                </h3>

                                <p className="mt-1 text-lg text-zinc-600 dark:text-zinc-300">{price} RON</p>
                            </div>
                            <div>
                                <button type="button"
                                        className="text-white bg-gradient-to-r from-indigo-500 via-indigo-600 to-indigo-700 hover:bg-gradient-to-br focus:ring-1 focus:outline-none focus:ring-indigo-300 dark:focus:ring-indigo-800 shadow-lg shadow-indigo-500/50 dark:shadow-lg dark:shadow-indigo-800/80 font-medium rounded-lg text-sm px-3 py-1.5 text-center mr-2 mb-2"
                                        onClick={() => toggleModal(id)}>
                                    Add to cart
                                </button>

                            </div>
                        </div>
                    </div>



                </a>
            </li>
        </div>
    )
}
export default ProductComponent;