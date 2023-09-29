import React, {createContext, useContext, useEffect, useState} from 'react'
import {addFavorite, getFavorites, removeFavorite} from "../api/BuyerApi";
import {useAuth} from "../auth/AuthContext";

const FavoriteContext = createContext(undefined)
export const useFavorite = () => useContext(FavoriteContext)

const FavoriteProvider = ({children}) => {

    const [allFavorites, setAllFavorites] = useState([]);
    const [numberOfFavorites, setNumberOfFavorites] = useState(0);
    const {isAuthenticated} = useAuth()

    const loadFavoriteItems = () => {
        if (!!isAuthenticated) {
            getFavorites()
                .then((res) => {
                    setAllFavorites(res.data)
                    setNumberOfFavorites(res.data.length)
                })
                .catch((err) => console.log(err));
        }
    }

    const addToFavorite = (productId) => {
        addFavorite(productId)
            .then(() => {
                loadFavoriteItems();
            })
            .catch((err) => console.log(err));
    }

    const checkIsFavorite = (favoritesArray, id) => {
        return favoritesArray.some((favorite) => favorite.id === id);
    };

    const removeFromFavorite = (productId) => {
        removeFavorite(productId)
            .then(() => {
                loadFavoriteItems();
            })
            .catch((err) => console.log(err));
    };

    useEffect(() => {
        loadFavoriteItems();
    }, [isAuthenticated]);


    return (
        <FavoriteContext.Provider value={{allFavorites, numberOfFavorites, addToFavorite, removeFromFavorite, checkIsFavorite}}>
            {children}
        </FavoriteContext.Provider>
    )
}

export default FavoriteProvider;
