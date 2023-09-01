import './App.css';
import "@fontsource/roboto";

import {BrowserRouter, Navigate, Route, Routes} from 'react-router-dom'
import AuthProvider, { useAuth } from './security/AuthContext'
import PaginationProvider from './components/context/PaginationContext'

import Header from "./components/header/Header";
import WelcomeComponent from "./components/welcomePage/WelcomeComponent";
import LoginComponent from "./components/login/LoginComponent";
import RegisterComponent from "./components/register/RegisterComponent";
import CategoryPageComponent from './components/categoryPage/CategoryPageComponent'


function AuthenticatedRoute({children}){

    const auth = useAuth()
    if(auth.isAuthenticated){
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
              <PaginationProvider>
                  <BrowserRouter>
                      <Header/>
                      <Routes>
                          <Route path='/' element={<WelcomeComponent/>}/>
                          <Route path='' element={<WelcomeComponent/>}/>
                          <Route path='/login' element={<LoginComponent/>}/>
                          <Route path='/register' element={<RegisterComponent/>}/>
                          <Route path='/products/categories' element={<CategoryPageComponent/>}/>
                          <Route path='/favorites' element={
                              <AuthenticatedRoute>

                              </AuthenticatedRoute>
                          }></Route>

                      </Routes>
                  </BrowserRouter>
             </PaginationProvider>
          </AuthProvider>
      </div>
  );
}

export default App;
