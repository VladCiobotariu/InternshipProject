import { useState } from 'react'
import {Link, useNavigate} from 'react-router-dom'
import { useAuth } from '../../security/AuthContext'

function LoginComponent(){

    const [email, setUser] = useState('')
    const [password, setPassword] = useState('')

    const [showErrorMessage, setErrorMessage] = useState(false)

    const navigate = useNavigate()
    const auth = useAuth()

    function handleUsernameChange(event){
        setUser(event.target.value)
    }

    function handlePasswordChange(event){
        setPassword(event.target.value)
    }

    async function handelSubmit(){
        if(await auth.login(email, password)){
            navigate('/')
        }
        else{
            setErrorMessage(true)
        }
    }

    return (
        <>
            <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-12 sm:py-6 lg:px-8 text-gray-900 dark:text-gray-100">
                <div className="mx-auto w-full sm:max-w-sm md:max-w-md lg:max-w-lg xl:max-w-lg 2xl:max-w-xl">
                    <h2 className="mt-20 text-center text-2xl font-bold leading-9 tracking-tight">
                        Sign in to your account
                    </h2>

                    {showErrorMessage &&
                    <div className="mt-1 text-center text-md leading-9 tracking-tight">
                        Authentication Failed. Please check your credentials
                    </div>
                    }
                </div>

                <div className="mt-5 mx-auto w-full sm:max-w-sm md:max-w-md lg:max-w-lg xl:max-w-lg 2xl:max-w-xl">
                    <form className="space-y-6" action="#" method="POST">
                        <div>
                            <label htmlFor="email" className="block text-sm font-medium leading-6">
                                Email address
                            </label>
                            <div className="mt-2">
                                <input
                                    id="email"
                                    name="email"
                                    type="email"
                                    value={email}
                                    onChange={handleUsernameChange}
                                    autoComplete="email"
                                    required
                                    className={`dark:bg-[#192235] dark:ring-gray-800 block w-full rounded-md border-0 py-1.5 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6`}
                                />
                            </div>
                        </div>

                        <div>
                            <div className="flex items-center justify-between">
                                <label htmlFor="password" className="block text-sm font-medium leading-6">
                                    Password
                                </label>
                                <div className="text-sm">
                                    <Link to="/forgot-password" className="font-semibold text-indigo-600 hover:text-indigo-500">
                                        Forgot password?
                                    </Link>
                                </div>
                            </div>
                            <div className="mt-2">
                                <input
                                    id="password"
                                    name="password"
                                    type="password"
                                    autoComplete="current-password"
                                    value={password}
                                    onChange={handlePasswordChange}
                                    required
                                    className="dark:bg-[#192235] dark:ring-gray-800
                                        block w-full rounded-md border-0 py-1.5 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                />
                            </div>
                        </div>

                        <div>
                            <button
                                type="button"
                                name="login"
                                onClick={handelSubmit}
                                className="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                            >
                                Sign in
                            </button>
                        </div>
                    </form>

                    <p className="mt-10 text-center text-sm text-gray-500 dark:text-gray-300">
                        Not a member?{' '}
                        <Link to="/register" className="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">
                            Sign up!
                        </Link>
                    </p>
                </div>
            </div>
        </>
    )
}


export default LoginComponent