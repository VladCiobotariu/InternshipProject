import {shippingAddressSchema} from "../../../validators/shippingAddressSchema";
import {Form, Formik} from "formik";
import TextInputWithError from "../../atoms/input/TextInputWithError";
import CountrySelector from "../../atoms/countries/selector";
import {COUNTRIES} from "../../atoms/countries/countries";
import React, {useState} from "react";

function AddressForm({onSaveForm, shippingAddress}){

    const [isOpen, setIsOpen] = useState(false);
    const [country, setCountry] = useState(COUNTRIES.find(option => option.title === shippingAddress.address.country).title)

    // const { username } = useAuth()
    // shippingAddress = {...shippingAddress, email:username}

    function handleSubmit(values){
        values.address.country = country
        const address = {...values, id: shippingAddress.id}
        onSaveForm(address)
    }

    return(
        <Formik
            initialValues={shippingAddress}
            onSubmit={handleSubmit}
            validationSchema={shippingAddressSchema}
            validateOnBlur={false}
            validateOnChange={false}
        >

            {({ errors, validateField, values}) => {

                return (

                    <Form className="px-10 py-10">

                        {/*<p className="text-xl font-bold">Contact Information</p>*/}
                        {/*<div className='mt-4'>*/}
                        {/*    <TextInputWithError fieldName={'email'}*/}
                        {/*                        errorName={errors.email}*/}
                        {/*                        labelName={'Email address'}*/}
                        {/*                        onBlur={()=>validateField('email')}/>*/}
                        {/*</div>*/}

                        {/*<hr*/}
                        {/*    className="my-4 h-0.5 border-t-0 bg-neutral-100 dark:bg-neutral-700 opacity-100 dark:opacity-50" />*/}

                        <p className="text-xl font-bold mb-4">Shipping Information</p>
                        <div className="space-y-2">

                            <div className="flex flex-row space-x-4">
                                <div className="w-1/2">
                                    <TextInputWithError fieldName={'firstName'}
                                                        errorName={errors.firstName}
                                                        labelName={'First Name'}
                                                        onBlur={()=>validateField('firstName')}
                                                        fieldType={"text"}/>
                                </div>

                                <div className="w-1/2">
                                    <TextInputWithError fieldName={'lastName'}
                                                        errorName={errors.lastName}
                                                        labelName={'Last Name'}
                                                        onBlur={()=>validateField('lastName')}
                                                        fieldType={"text"}/>
                                </div>
                            </div>

                            <TextInputWithError fieldName={'address.addressLine1'}
                                                errorName={errors.address && errors.address.addressLine1}
                                                labelName={'Street, number'}
                                                onBlur={()=> {
                                                        validateField('address.addressLine1')
                                                }}
                                                fieldType={"text"}/>

                            <TextInputWithError fieldName={'address.addressLine2'}
                                                errorName={errors.address && errors.address.addressLine2}
                                                labelName={'Block, Apartment'}
                                                onBlur={()=>validateField('address.addressLine2')}
                                                fieldType={"text"}/>

                            <TextInputWithError fieldName={'telephone'}
                                                errorName={errors.telephone}
                                                labelName={'Telephone'}
                                                onBlur={()=>validateField('telephone')}
                                                fieldType={"tel"}/>

                            <div className="flex flex-row space-x-4">
                                <div className="w-1/2">
                                    <TextInputWithError fieldName={'address.state'}
                                                        labelName={'State'}
                                                        errorName={errors.address && errors.address.state}
                                                        onBlur={()=>validateField('address.state')}
                                                        fieldType={"text"}/>
                                </div>

                                <div className="w-1/2">
                                    <CountrySelector
                                        id='address.country'
                                        open={isOpen}
                                        onToggle={() => setIsOpen(!isOpen)}
                                        onChange={val => {
                                            setCountry(COUNTRIES.find(option => option.value === val).title)
                                        }}
                                        selectedValue={COUNTRIES.find(option => option.title === country)}
                                    />
                                </div>
                            </div>

                            <div className="flex flex-row space-x-4">

                                <div className="w-1/2">
                                    <TextInputWithError fieldName={'address.city'}
                                                        errorName={errors.address && errors.address.city}
                                                        labelName={'City'}
                                                        onBlur={()=>validateField('address.city')}
                                                        fieldType={"text"}/>
                                </div>

                                <div className="w-1/2">
                                    <TextInputWithError fieldName={'address.zipCode'}
                                                        errorName={errors.address && errors.address.zipCode}
                                                        labelName={'Zip Code'}
                                                        onBlur={()=>validateField('address.zipCode')}
                                                        fieldType={"text"}/>
                                </div>

                            </div>

                        </div>

                        <div className="mt-4">
                            <button type="submit" onClick={()=>handleSubmit(values)} className="mt-6 w-full rounded-md bg-blue-500 py-1.5 font-medium text-blue-50 hover:bg-blue-600">Save</button>
                        </div>

                    </Form>
                );
            }}
        </Formik>
    )
}

export default AddressForm