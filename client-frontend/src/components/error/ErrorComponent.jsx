import {Link} from "react-router-dom";
import React from "react";
import {ReactComponent as Robot} from "../../assets/robot.svg";

function ErrorComponent({description, solution, linkTo}){
    return(
        <div className="md:flex lg:flex xl:flex 2xl:flex mx-auto sm:max-w-xs max-w-lg p-6 bg-white rounded-2xl shadow dark:bg-gray-800 dark:border-gray-700">
            <Robot className="w-40 sm:w-32 sm:mx-auto"></Robot>
            <div className="sm:mt-4 sm:ml-0 ml-8 flex flex-col justify-between">
                <div>
                    <h5 className="text-center mb-2 text-4xl font-semibold tracking-tight text-gray-900 dark:text-white">Oops!</h5>
                    <p className="mt-6 sm:mt-2 mb-3 font-normal text-gray-500 dark:text-gray-400">{description}</p>
                </div>

                <Link to={linkTo} className="inline-flex justify-end items-center text-blue-600 hover:underline">
                    {solution}
                    <svg className="w-3 h-3 ml-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 18 18">
                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M15 11v4.833A1.166 1.166 0 0 1 13.833 17H2.167A1.167 1.167 0 0 1 1 15.833V4.167A1.166 1.166 0 0 1 2.167 3h4.618m4.447-2H17v5.768M9.111 8.889l7.778-7.778"/>
                    </svg>
                </Link>
            </div>
        </div>
    )
}

export default ErrorComponent