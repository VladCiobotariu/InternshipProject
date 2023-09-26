import React from 'react'
import {Link, useNavigate} from 'react-router-dom'
import { useAuth } from '../../auth/AuthContext'
import {Field, Formik, Form} from "formik";
import { passwordSchema } from "../../validators/passwordSchema";
import TextInputWithError from "../atoms/input/TextInputWithError";
import ErrorField from "../atoms/error/ErrorField";

function RegisterPageComponent(){

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

                                    {Object.keys(errors).map((key, i) => (
                                        <div key={i}>
                                            <ErrorField errorName={errors[key]}/>
                                        </div>
                                    ))}

                                    <TextInputWithError fieldName={'email'}
                                                        errorName={errors.email}
                                                        labelName={'Email address'}
                                                        onBlur={()=>validateField('email')}/>

                                    <TextInputWithError fieldName={'password'}
                                                        errorName={errors.password}
                                                        labelName={'Password'}
                                                        onBlur={()=>validateField('password')}
                                                        fieldType={"password"}/>

                                    <TextInputWithError fieldName={'confirmPassword'}
                                                        errorName={errors.confirmPassword}
                                                        labelName={'Confirm Password'}
                                                        onBlur={()=>validateField('confirmPassword')}
                                                        fieldType={"password"}/>

                                    <TextInputWithError fieldName={'firstName'}
                                                        errorName={errors.firstName}
                                                        labelName={'First Name'}
                                                        onBlur={()=>validateField('firstName')}
                                                        fieldType={"text"}/>

                                    <TextInputWithError fieldName={'lastName'}
                                                        errorName={errors.lastName}
                                                        labelName={'Last Name'}
                                                        onBlur={()=>validateField('lastName')}
                                                        fieldType={"text"}/>

                                    <TextInputWithError fieldName={'telephone'}
                                                        errorName={errors.telephone}
                                                        labelName={'Telephone'}
                                                        onBlur={()=>validateField('telephone')}
                                                        fieldType={"tel"}/>

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


export default RegisterPageComponent