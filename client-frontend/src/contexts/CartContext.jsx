import {createContext, useContext, useEffect, useState} from 'react'
import {useAuth} from "../auth/AuthContext";
import {addOrUpdateCartItem, getCartItems, removeCartItem} from "../api/CartApi";

const CartContext = createContext(undefined)
export const useCart = () => useContext(CartContext)

const CartProvider = ({children}) => {

    const [allCartItems, setAllCartItems] = useState(null)
    const [numberOfCartItems, setNumberOfCartItems] = useState(0)
    const [cartTotalPrice, setCartTotalPrice] = useState(0)

    const {isAuthenticated, username} = useAuth()

    function loadCartItems() {

        /**
         * @param{{
         *          cartItems:[],
         *          totalCartPrice:float
         *  }} data
         */

        if(!!isAuthenticated){
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
        } else {
            setAllCartItems(null)
            setNumberOfCartItems(0)
            setCartTotalPrice(0)
        }

    }

    function updateCartItemQuantity(productId, newQuantity){
        addOrUpdateCartItem(productId, newQuantity)
            .then(
                () => {
                    refreshCart()
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
                    refreshCart()
                }
            )
            .catch(
                (err) => console.log(err)
            )
    }

    function refreshCart(){
        loadCartItems()
    }

    useEffect(() => {
        refreshCart()
    }, [isAuthenticated, username]);

    useEffect(() => {
        if(allCartItems!==null){
            setNumberOfCartItems(allCartItems.length)
        }
    }, [allCartItems]);

    return (
        <CartContext.Provider value={{allCartItems, numberOfCartItems, cartTotalPrice, updateCartItemQuantity, deleteCartItem, refreshCart}}>
            {children}
        </CartContext.Provider>
    )
}

export default CartProvider;
