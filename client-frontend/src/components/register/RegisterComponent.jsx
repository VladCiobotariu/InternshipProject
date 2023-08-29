import { useState } from 'react'
import {Link, useNavigate} from 'react-router-dom'
import { useAuth } from '../../security/AuthContext'

function RegisterComponent(){

    const [email, setUser] = useState('')
    const [password, setPassword] = useState('')
    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [telephone, setTelephone] = useState('')
    const [image, setImage] = useState('')

    const navigate = useNavigate()
    const auth = useAuth()

    function handleEmailChange(event){
        setUser(event.target.value)
    }

    function handlePasswordChange(event){
        setPassword(event.target.value)
    }

    function handleFirstNameChange(event){
        setFirstName(event.target.value)
    }
    function handleLastNameChange(event){
        setLastName(event.target.value)
    }
    function handleTelephoneChange(event){
        setTelephone(event.target.value)
    }
    function handleImageChange(event){
        setImage(event.target.value)
    }

    async function handelSubmit(){
        await auth.registerUser(email, password, firstName, lastName, telephone, image)
            .then(
                ()=>navigate('/login')
            )
            .catch(
                (error) => console.log(error.response.data)
            )
    }

    return (
        <>
            <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-0 lg:px-8 text-gray-900 dark:text-gray-100">
                <div className="mx-auto w-full sm:max-w-sm md:max-w-md lg:max-w-lg xl:max-w-lg 2xl:max-w-xl">
                    <h2 className="mt-20 text-center text-2xl font-bold leading-9 tracking-tight">
                        Sign up
                    </h2>
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
                                    onChange={handleEmailChange}
                                    autoComplete="email"
                                    required
                                    className="dark:bg-[#192235] dark:ring-gray-800
                                        block w-full rounded-md border-0 py-1.5 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                />
                            </div>
                        </div>

                        <div>
                            <div className="flex items-center justify-between">
                                <label htmlFor="password" className="block text-sm font-medium leading-6">
                                    Password
                                </label>
                            </div>
                            <div className="mt-2">
                                <input
                                    id="password"
                                    name="password"
                                    type="password"
                                    value={password}
                                    onChange={handlePasswordChange}
                                    required
                                    className="dark:bg-[#192235] dark:ring-gray-800
                                        block w-full rounded-md border-0 py-1.5 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                />
                            </div>
                        </div>

                        <div>
                            <div className="flex items-center justify-between">
                                <label htmlFor="password" className="block text-sm font-medium leading-6">
                                    First Name
                                </label>
                            </div>
                            <div className="mt-2">
                                <input
                                    name="firstname"
                                    type="text"
                                    value={firstName}
                                    onChange={handleFirstNameChange}
                                    required
                                    className="dark:bg-[#192235] dark:ring-gray-800
                                        block w-full rounded-md border-0 py-1.5 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                />
                            </div>
                        </div>

                        <div>
                            <div className="flex items-center justify-between">
                                <label htmlFor="password" className="block text-sm font-medium leading-6">
                                    Last Name
                                </label>
                            </div>
                            <div className="mt-2">
                                <input
                                    name="lastname"
                                    type="text"
                                    value={lastName}
                                    onChange={handleLastNameChange}
                                    required
                                    className="dark:bg-[#192235] dark:ring-gray-800
                                        block w-full rounded-md border-0 py-1.5 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                />
                            </div>
                        </div>

                        <div>
                            <div className="flex items-center justify-between">
                                <label htmlFor="password" className="block text-sm font-medium leading-6">
                                    Telephone
                                </label>
                            </div>
                            <div className="mt-2">
                                <input
                                    name="telephone"
                                    type="tel"
                                    value={telephone}
                                    onChange={handleTelephoneChange}
                                    required
                                    className="dark:bg-[#192235] dark:ring-gray-800
                                        block w-full rounded-md border-0 py-1.5 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                />
                            </div>
                        </div>

                        <div>
                            <div className="flex items-center justify-between">
                                <label htmlFor="password" className="block text-sm font-medium leading-6">
                                    Image
                                </label>
                            </div>
                            <div className="mt-2">
                                <input
                                    name="image"
                                    type="text"
                                    value={image}
                                    onChange={handleImageChange}
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
                                Sign up
                            </button>
                        </div>
                    </form>

                    <p className="mt-10 text-center text-sm text-gray-500 dark:text-gray-300">
                        Already a member?{' '}
                        <Link to="/login" className="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">
                            Sign in!
                        </Link>
                    </p>
                </div>
            </div>
        </>
    )
}


export default RegisterComponent