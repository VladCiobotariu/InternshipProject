import React, {useEffect, useState} from 'react'
import {useAuth} from "../../../auth/AuthContext";
import {useFavorite} from "../../../contexts/FavoriteContext";

const AddRemoveWishlist = ({productId}) => {

    const {isAuthenticated} = useAuth();

    const {allFavorites, addToFavorite, removeFromFavorite, checkIsFavorite} = useFavorite();
    const [isFavorite, setIsFavorite] = useState(false);

    useEffect(() => {
        setIsFavorite(checkIsFavorite(allFavorites, productId));
    }, [allFavorites]);


    const toggleFavorite = () => {
        if (!isFavorite) {
            addToFavorite(productId);
        } else {
            removeFromFavorite(productId);
        }
    };


    return (
        isAuthenticated && (
            <button
                className="flex justify-center font-medium rounded-lg text-base leading-4 text-white bg-indigo-600 w-full py-5 mt-6 border border-indigo-750 hover:bg-indigo-700 dark:border-indigo-900"
                onClick={toggleFavorite}>
                <div className="flex">
                    <div className="mt-[3px]">
                        {isFavorite ? (
                            'Remove from wishlist'
                        ) : (
                            'Add to wishlist'
                        )}
                    </div>
                    <div className="">
                        <svg xmlns="http://www.w3.org/2000/svg" fill={isFavorite ? "white" : "none"}
                             viewBox="0 0 24 24" strokeWidth={1.5}
                             stroke="currentColor" className="ml-2 w-5 h-5 text-white">
                            <path strokeLinecap="round" strokeLinejoin="round"
                                  d="M21 8.25c0-2.485-2.099-4.5-4.688-4.5-1.935 0-3.597 1.126-4.312 2.733-.715-1.607-2.377-2.733-4.313-2.733C5.1 3.75 3 5.765 3 8.25c0 7.22 9 12 9 12s9-4.78 9-12z"/>
                        </svg>
                    </div>
                </div>
            </button>
        )
    )
}

export default AddRemoveWishlist