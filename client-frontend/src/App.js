import './App.css';
import "@fontsource/roboto";

import {BrowserRouter, Navigate, Route, Routes} from 'react-router-dom'
import AuthProvider, { useAuth } from './security/AuthContext'

import Header from "./components/header/Header";
import WelcomeComponent from "./components/welcomePage/WelcomeComponent";
import LoginComponent from "./components/login/LoginComponent";
import RegisterComponent from "./components/register/RegisterComponent";
import {AuthVerify} from "./security/AuthVerify";
import Favorites from "./components/favorites/Favorites";
import CartComponent from "./components/cart/CartComponent";
import CategoryPageComponent from './components/categoryPage/CategoryPageComponent'
import ProductPageComponent from './components/productPage/ProductPageComponent'


function AuthenticatedRoute({children}){

    const auth = sessionStorage.getItem('isAuthenticated')

    if(auth === "false"){
        return <Navigate to={"/"}/>
    }
    if(auth){
        return(
            children
        )
    }
    return <Navigate to={"/"}/>
}

function NotAuthenticatedRoute({children}){

    const auth = useAuth()
    if(!auth.isAuthenticated){
        return(
            children
        )
    }
    return <Navigate to={"/"}/>
}

function App() {
  return (
      <div className="bg-white dark:bg-inherit">
          <AuthProvider>
              <BrowserRouter>

                  <Header/>

                  <Routes>
                      <Route path='/' element={<WelcomeComponent/>}/>
                      <Route path='' element={<WelcomeComponent/>}/>
                      <Route path='/login' element={
                          <NotAuthenticatedRoute>
                              <LoginComponent/>
                          </NotAuthenticatedRoute>
                      }/>

                      <Route path='/register' element={
                          <NotAuthenticatedRoute>
                              <RegisterComponent/>
                          </NotAuthenticatedRoute>
                      }/>

                      <Route path='/account/favorites' element={
                          <AuthenticatedRoute>
                            <Favorites/>
                          </AuthenticatedRoute>
                      }/>

                      <Route path='/products/categories' element={<CategoryPageComponent/>}/>
                      <Route path='/products/categories/:categoryName' element={<ProductPageComponent/>}/>

                      <Route path='/account/cart' element={<CartComponent/>}/>
                  </Routes>

                  <AuthVerify/>

              </BrowserRouter>
          </AuthProvider>
      </div>
  );
}

export default App;
