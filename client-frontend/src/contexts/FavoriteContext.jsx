import React, {createContext, useContext, useEffect, useState} from 'react'
import {addFavorite, getFavorites, removeFavorite} from "../api/BuyerApi";
import {getProductByNameApi} from "../api/ProductApi";

const FavoriteContext = createContext(undefined)
export const useFavorite = () => useContext(FavoriteContext)

const FavoriteProvider = ({children}) => {

    const [allFavorites, setAllFavorites] = useState([]);
    const [numberOfFavorites, setNumberOfFavorites] = useState(0);

    const addToFavorite = (productId, productName) => {
        getProductByNameApi(productName)
            .then((product) => {
                addFavorite(productId)
                    .then(() => {
                        setAllFavorites([...allFavorites, product.data]);
                    })
                    .catch((err) => console.log(err));
            })
            .catch((err) => console.log(err));
    };

    const removeFromFavorite = (productId) => {
        removeFavorite(productId)
            .then(() => {
                setAllFavorites(allFavorites.filter((favorite) => favorite.id !== productId));
            })
            .catch((err) => console.log(err));
    };

    useEffect(() => {
        getFavorites()
            .then((res) => {
                const favorites = res.data;
                localStorage.setItem('favorites', JSON.stringify(favorites));
                setAllFavorites(favorites)
            })
            .catch((err) => console.log(err));

    }, []);

    useEffect(() => {
        setNumberOfFavorites(allFavorites.length)
    }, [allFavorites])


    return (
        <FavoriteContext.Provider value={{allFavorites, numberOfFavorites, addToFavorite, removeFromFavorite}}>
            {children}
        </FavoriteContext.Provider>
    )
}

export default FavoriteProvider;
