import {createContext, useContext, useEffect, useState} from 'react'
import {useAuth} from "../auth/AuthContext";
import {addOrUpdateCartItem, getCartItems, removeCartItem} from "../api/CartApi";
import {useLocation} from "react-router-dom";

const CartContext = createContext(undefined)
export const useCart = () => useContext(CartContext)

const CartProvider = ({children}) => {

    const [allCartItems, setAllCartItems] = useState(null)
    const [numberOfCartItems, setNumberOfCartItems] = useState(0)
    const [cartTotalPrice, setCartTotalPrice] = useState(0)

    const {isAuthenticated, username} = useAuth()
    const location = useLocation()

    function loadCartItems() {

        /**
         * @param{{
         *          cartItems:[],
         *          totalCartPrice:float
         *  }} data
         */

        getCartItems()
            .then(
                (response) => {
                    setAllCartItems(response.data.cartItems)
                    setNumberOfCartItems(response.data.cartItems.length)
                    setCartTotalPrice(response.data.totalCartPrice)
                }
            )
            .catch(
                (err) => {
                    console.log(err)
                    setAllCartItems([])
                }
            )
    }

    function updateCartItemQuantity(productId, newQuantity){
        addOrUpdateCartItem(productId, newQuantity)
            .then(
                () => {
                    loadCartItems()
                }
            )
            .catch(
                (err) => {
                    console.log(err)
                }
            )
    }

    function deleteCartItem(productId){
        removeCartItem(productId)
            .then(
                () => {
                    loadCartItems()
                }
            )
            .catch(
                (err) => console.log(err)
            )
    }

    useEffect(() => {
        if(!!isAuthenticated){
            loadCartItems()
        }
    }, [isAuthenticated, username, location]);

    useEffect(() => {
        if(allCartItems!==null){
            setNumberOfCartItems(allCartItems.length)
        }
    }, [allCartItems]);

    return (
        <CartContext.Provider value={{allCartItems, numberOfCartItems, cartTotalPrice, updateCartItemQuantity, deleteCartItem}}>
            {children}
        </CartContext.Provider>
    )
}

export default CartProvider;
