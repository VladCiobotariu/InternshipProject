import './styles/App.css';
import "@fontsource/roboto";

import {BrowserRouter, Navigate, Route, Routes} from 'react-router-dom'
import AuthProvider, {useAuth} from './auth/AuthContext'
import FavoriteProvider from './contexts/FavoriteContext'

import HeaderComponent from "./components/moleculas/header/HeaderComponent";
import WelcomePageComponent from "./components/organisms/WelcomePageComponent";
import LoginPageComponent from "./components/organisms/LoginPageComponent";
import RegisterPageComponent from "./components/organisms/RegisterPageComponent";
import {AuthVerify} from "./auth/AuthVerify";
import CartComponent from "./components/organisms/CartPageComponent";
import CategoryPageComponent from './components/organisms/CategoryPageComponent'
import ProductPageComponent from './components/organisms/ProductPageComponent'
import CheckoutPageComponent from "./components/organisms/CheckoutPageComponent";
import CartProvider from "./contexts/CartContext";

function AuthenticatedRoute({children}) {

    const auth = sessionStorage.getItem('isAuthenticated')

    if (auth === "false") {
        return <Navigate to={"/"}/>
    }
    if (auth) {
        return (
            children
        )
    }
    return <Navigate to={"/"}/>
}

function NotAuthenticatedRoute({children}) {

    const auth = useAuth()
    if (!auth.isAuthenticated) {
        return (
            children
        )
    }
    return <Navigate to={"/"}/>
}

function App() {

    return (
        <div className="bg-white dark:bg-inherit">
            <AuthProvider>
                <FavoriteProvider>
                    <BrowserRouter>
                        <CartProvider>
                            <HeaderComponent/>

                            <Routes>
                                <Route path='/' element={<WelcomePageComponent/>}/>
                                <Route path='' element={<WelcomePageComponent/>}/>
                                <Route path='/login' element={
                                    <NotAuthenticatedRoute>
                                        <LoginPageComponent/>
                                    </NotAuthenticatedRoute>
                                }/>

                                <Route path='/register' element={
                                    <NotAuthenticatedRoute>
                                        <RegisterPageComponent/>
                                    </NotAuthenticatedRoute>
                                }/>

                                <Route path='/products/categories' element={<CategoryPageComponent/>}/>
                                <Route path='/products' element={<ProductPageComponent/>}/>

                                <Route path='/account/cart' element={
                                    <AuthenticatedRoute>
                                        <CartComponent/>
                                    </AuthenticatedRoute>
                                }/>

                                <Route path='/checkout' element={
                                    <AuthenticatedRoute>
                                        <CheckoutPageComponent/>
                                    </AuthenticatedRoute>
                                }/>
                            </Routes>

                            <AuthVerify/>
                        </CartProvider>
                    </BrowserRouter>
                </FavoriteProvider>
            </AuthProvider>
        </div>
    );
}

export default App;
