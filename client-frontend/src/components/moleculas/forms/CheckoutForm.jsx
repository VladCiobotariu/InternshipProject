import {shippingAddressSchema} from "../../../validators/shippingAddressSchema";
import {Form, Formik} from "formik";
import TextInputWithError from "../../atoms/input/TextInputWithError";
import CountrySelector from "../../atoms/countries/selector";
import {COUNTRIES} from "../../atoms/countries/countries";
import React, {useState} from "react";

function CheckoutForm({submitCheckoutForm}){

    const [isOpen, setIsOpen] = useState(false);
    const [country, setCountry] = useState('RO');

    function onCheckout(values){
        values.country = country
        submitCheckoutForm(values)
    }

    return(
        <Formik
            initialValues={{
                email: "",
                firstName: "",
                lastName: "",
                addressLine1: "",
                addressLine2: "",
                city: "",
                country: "",
                state: "",
                zipCode: "",
                telephone: ""
            }}
            onSubmit={()=>{}}
            validationSchema={shippingAddressSchema}
            validateOnBlur={false}
            validateOnChange={false}
        >

            {({ errors, validateField , values}) => {

                return (

                    <Form id='my-form' onSubmit={()=> {
                        onCheckout(values)
                    }}>

                        <p className="text-xl font-bold">Contact Information</p>
                        <div className='mt-4'>
                            <TextInputWithError fieldName={'email'}
                                                errorName={errors.email}
                                                labelName={'Email address'}
                                                onBlur={()=>validateField('email')}/>
                        </div>

                        <hr
                            className="my-4 h-0.5 border-t-0 bg-neutral-100 dark:bg-neutral-700 opacity-100 dark:opacity-50" />

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

                            <TextInputWithError fieldName={'addressLine1'}
                                                errorName={errors.addressLine1}
                                                labelName={'Street, number'}
                                                onBlur={()=>validateField('addressLine1')}
                                                fieldType={"text"}/>

                            <TextInputWithError fieldName={'addressLine2'}
                                                errorName={errors.addressLine2}
                                                labelName={'Block, Apartment'}
                                                onBlur={()=>validateField('addressLine2')}
                                                fieldType={"text"}/>

                            <TextInputWithError fieldName={'telephone'}
                                                errorName={errors.telephone}
                                                labelName={'Telephone'}
                                                onBlur={()=>validateField('telephone')}
                                                fieldType={"tel"}/>

                            <div className="flex flex-row space-x-4">
                                <div className="w-1/2">
                                    <TextInputWithError fieldName={'state'}
                                                        labelName={'State'}
                                                        onBlur={()=>validateField('state')}
                                                        fieldType={"text"}/>
                                </div>

                                <div className="w-1/2">
                                    <CountrySelector
                                        id='country'
                                        open={isOpen}
                                        errorName={errors.country}
                                        onToggle={() => setIsOpen(!isOpen)}
                                        onChange={val => {
                                            setCountry(val)
                                        }}
                                        selectedValue={COUNTRIES.find(option => option.value === country)}
                                    />
                                </div>
                            </div>

                            <div className="flex flex-row space-x-4">

                                <div className="w-1/2">
                                    <TextInputWithError fieldName={'city'}
                                                        errorName={errors.city}
                                                        labelName={'City'}
                                                        onBlur={()=>validateField('city')}
                                                        fieldType={"text"}/>
                                </div>

                                <div className="w-1/2">
                                    <TextInputWithError fieldName={'zipCode'}
                                                        errorName={errors.zipCode}
                                                        labelName={'Zip Code'}
                                                        onBlur={()=>validateField('zipCode')}
                                                        fieldType={"text"}/>
                                </div>

                            </div>

                        </div>

                    </Form>
                );
            }}
        </Formik>
    )
}

export default CheckoutForm