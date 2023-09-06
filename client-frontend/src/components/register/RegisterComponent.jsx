import React from 'react'
import {Link, useNavigate} from 'react-router-dom'
import { useAuth } from '../../security/AuthContext'
import {Field, Formik, Form} from "formik";
import { passwordSchema } from "./passwordSchema";

function RegisterComponent(){

    const auth = useAuth()
    const navigate = useNavigate()

    async function onSubmit(values){
        await auth.registerUser(values.email, values.password, values.firstName, values.lastName, values.telephone, values.image)
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
                    <h2 className="mt-8 text-center text-2xl font-bold leading-9 tracking-tight">
                        Sign up
                    </h2>
                </div>

                <div className="mt-5 mx-auto w-full sm:max-w-sm md:max-w-md lg:max-w-lg xl:max-w-lg 2xl:max-w-xl">
                    <Formik
                            initialValues={{
                                password: "",
                                confirmPassword: "",
                                firstName: "",
                                lastName: "",
                                email: "",
                                telephone: "",
                                image: ""
                            }}
                            onSubmit={onSubmit}
                            validationSchema={passwordSchema}
                            validateOnBlur={false}
                            validateOnChange={false}
                    >

                        {({ errors, validateField }) => {
                            return (
                                <Form className="space-y-2">

                                    {!!errors.email && (
                                        <div className="flex items-center p-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400" role="alert">
                                            <svg className="flex-shrink-0 inline w-4 h-4 mr-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                                                <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
                                            </svg>
                                            <span className="sr-only">Info</span>
                                            <div>
                                                <p className="font-medium">{errors.email}</p>
                                            </div>
                                        </div>
                                    )}

                                    {!!errors.password && (
                                        <div className="flex items-center p-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400" role="alert">
                                            <svg className="flex-shrink-0 inline w-4 h-4 mr-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                                                <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
                                            </svg>
                                            <span className="sr-only">Info</span>
                                            <div>
                                                <p className="font-medium">{errors.password}</p>
                                            </div>
                                        </div>
                                    )}

                                    {!!errors.confirmPassword && (
                                        <div className="flex items-center p-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400" role="alert">
                                            <svg className="flex-shrink-0 inline w-4 h-4 mr-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                                                <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
                                            </svg>
                                            <span className="sr-only">Info</span>
                                            <div>
                                                <p className="font-medium">{errors.confirmPassword}</p>
                                            </div>
                                        </div>
                                    )}

                                    {!!(errors.firstName || errors.lastName) && (
                                        <div className="flex items-center p-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400" role="alert">
                                            <svg className="flex-shrink-0 inline w-4 h-4 mr-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                                                <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
                                            </svg>
                                            <span className="sr-only">Info</span>
                                            <div>
                                                <p className="font-medium">Field required</p>
                                            </div>
                                        </div>
                                    )}

                                    {!!errors.telephone && (
                                        <div className="flex items-center p-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400" role="alert">
                                            <svg className="flex-shrink-0 inline w-4 h-4 mr-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                                                <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
                                            </svg>
                                            <span className="sr-only">Info</span>
                                            <div>
                                                <p className="font-medium">{errors.telephone}</p>
                                            </div>
                                        </div>
                                    )}

                                    <div>
                                        <label className={` ${errors.email ? 'text-red-600':'text-inherit' } block text-sm font-medium leading-6`}>
                                            Email address
                                        </label>
                                        <div className="mt-2">
                                            <Field
                                                name="email"
                                                className={` ${errors.email ? 'ring-red-500 dark:ring-red-500':'ring-gray-300 dark:ring-gray-800'} 
                                                    dark:bg-[#192235] block w-full rounded-md 
                                                    border-0 py-1.5 shadow-sm 
                                                    ring-1 ring-inset
                                                    placeholder:text-gray-400  
                                                    focus:ring-2 focus:ring-inset focus:ring-indigo-600 
                                                    sm:text-sm sm:leading-6`}
                                                onBlur={()=>{
                                                    validateField('email')
                                                }}
                                            >
                                            </Field>
                                        </div>
                                    </div>

                                    <div>
                                        <label className={` ${errors.password ? 'text-red-600':'text-inherit' } block text-sm font-medium leading-6`}>
                                            Password
                                        </label>
                                        <div className="mt-2">
                                            <Field
                                                name="password"
                                                type="password"
                                                className={` ${errors.password ? 'ring-red-500 dark:ring-red-500':'ring-gray-300 dark:ring-gray-800'} 
                                                    dark:bg-[#192235] block w-full rounded-md 
                                                    border-0 py-1.5 shadow-sm 
                                                    ring-1 ring-inset
                                                    placeholder:text-gray-400  
                                                    focus:ring-2 focus:ring-inset focus:ring-indigo-600 
                                                    sm:text-sm sm:leading-6`}
                                                onBlur={()=>{
                                                    validateField('password')
                                                }}
                                            ></Field>
                                        </div>
                                    </div>

                                    <div>
                                        <label className={` ${errors.confirmPassword ? 'text-red-600':'text-inherit' } block text-sm font-medium leading-6`}>
                                            Confirm Password
                                        </label>
                                        <div className="mt-2">
                                            <Field
                                                name="confirmPassword"
                                                type="password"
                                                className={` ${errors.confirmPassword ? 'ring-red-500 dark:ring-red-500':'ring-gray-300 dark:ring-gray-800'} 
                                                    dark:bg-[#192235] block w-full rounded-md 
                                                    border-0 py-1.5 shadow-sm 
                                                    ring-1 ring-inset
                                                    placeholder:text-gray-400  
                                                    focus:ring-2 focus:ring-inset focus:ring-indigo-600 
                                                    sm:text-sm sm:leading-6`}
                                                onBlur={()=>{
                                                    validateField('confirmPassword')
                                                }}
                                            ></Field>
                                        </div>
                                    </div>

                                    <div>
                                        <label className={` ${errors.firstName ? 'text-red-600':'text-inherit' } block text-sm font-medium leading-6`}>
                                            First Name
                                        </label>
                                        <div className="mt-2">
                                            <Field
                                                name="firstName"
                                                type="text"
                                                className={` ${errors.firstName ? 'ring-red-500 dark:ring-red-500':'ring-gray-300 dark:ring-gray-800'} 
                                                    dark:bg-[#192235] block w-full rounded-md 
                                                    border-0 py-1.5 shadow-sm 
                                                    ring-1 ring-inset
                                                    placeholder:text-gray-400  
                                                    focus:ring-2 focus:ring-inset focus:ring-indigo-600 
                                                    sm:text-sm sm:leading-6`}
                                                onBlur={()=>{
                                                    validateField('firstName')
                                                }}
                                            ></Field>
                                        </div>
                                    </div>

                                    <div>
                                        <label className={` ${errors.lastName ? 'text-red-600':'text-inherit' } block text-sm font-medium leading-6`}>
                                            Last Name
                                        </label>
                                        <div className="mt-2">
                                            <Field
                                                name="lastName"
                                                type="text"
                                                className={` ${errors.lastName ? 'ring-red-500 dark:ring-red-500':'ring-gray-300 dark:ring-gray-800'} 
                                                    dark:bg-[#192235] block w-full rounded-md 
                                                    border-0 py-1.5 shadow-sm 
                                                    ring-1 ring-inset
                                                    placeholder:text-gray-400  
                                                    focus:ring-2 focus:ring-inset focus:ring-indigo-600 
                                                    sm:text-sm sm:leading-6`}
                                                onBlur={()=>{
                                                    validateField('lastName')
                                                }}
                                            ></Field>
                                        </div>
                                    </div>

                                    <div>
                                        <label className={` ${errors.telephone ? 'text-red-600':'text-inherit' } block text-sm font-medium leading-6`}>
                                            Telephone
                                        </label>
                                        <div className="mt-2">
                                            <Field
                                                name="telephone"
                                                type="tel"
                                                className={` ${errors.telephone ? 'ring-red-500 dark:ring-red-500':'ring-gray-300 dark:ring-gray-800'} 
                                                    dark:bg-[#192235] block w-full rounded-md 
                                                    border-0 py-1.5 shadow-sm 
                                                    ring-1 ring-inset
                                                    placeholder:text-gray-400  
                                                    focus:ring-2 focus:ring-inset focus:ring-indigo-600 
                                                    sm:text-sm sm:leading-6`}
                                                onBlur={()=>{
                                                    validateField('telephone')
                                                }}
                                            ></Field>
                                        </div>
                                    </div>

                                    <div>
                                        <label className="block text-sm font-medium leading-6">
                                            Image
                                        </label>
                                        <div className="mt-2">
                                            <Field
                                                name="image"
                                                type="text"
                                                className="dark:bg-[#192235] dark:ring-gray-800 block w-full rounded-md border-0 py-1.5 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                            ></Field>
                                        </div>
                                    </div>

                                    <div>
                                        <button
                                            type="submit"
                                            className="mt-6 flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                                        >
                                            Sign up
                                        </button>
                                    </div>

                                </Form>
                            );
                        }}
                    </Formik>

                    <p className="mt-10 text-center text-sm text-gray-500 dark:text-gray-300">
                        Already a member?{' '}
                        <Link to="/login" className="font-semibold leading-6 text-indigo-600 hover:text-indigo-500 pb-10">
                            Sign in!
                        </Link>
                    </p>
                </div>
            </div>
        </>
    )
}


export default RegisterComponent