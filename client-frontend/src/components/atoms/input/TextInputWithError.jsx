import {Field} from "formik";
import React from "react";


function TextInputWithError({fieldName, errorName, labelName, onBlur, fieldType}){

    return (
        <div>
            <label className={` ${errorName ? 'text-red-600':'text-inherit' } block text-sm font-medium leading-6`}>
                {labelName}
            </label>
            <div className="mt-2">
                <Field
                    name={fieldName}
                    type={fieldType}
                    className={` ${errorName ? 'ring-red-500 dark:ring-red-500':'ring-gray-300 dark:ring-gray-800'} 
                                                    dark:bg-[#192235] block w-full rounded-md 
                                                    border-0 py-1.5 shadow-sm 
                                                    ring-1 ring-inset
                                                    placeholder:text-gray-400  
                                                    focus:ring-2 focus:ring-inset focus:ring-indigo-600 
                                                    sm:text-sm sm:leading-6`}
                    onBlur={onBlur}
                >
                </Field>
            </div>
        </div>
    )
}

export default TextInputWithError