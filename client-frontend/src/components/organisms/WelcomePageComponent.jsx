import {Link} from "react-router-dom";
import React from "react";

export default function WelcomePageComponent() {



    return (
        <div className="bg-transparent dark:bg-transparent">

            <div className="px-6 pt-14 lg:px-8 xl:px-10 2xl:px-12 sm:pt-0 text-gray-900 dark:text-gray-100">

                <div className="mx-auto max-w-2xl py-32 lg:py-32 md:py-32 lg:max-w-2xl md:max-w-xl">

                    <div className="hidden md:mb-8 md:flex md:justify-center lg:mb-8 lg:flex lg:justify-center xl:mb-8 xl:flex xl:justify-center 2xl:mb-8 2xl:flex 2xl:justify-center">
                        <div className="relative rounded-full px-3 py-1 text-sm leading-6 text-inherit dark:text-inherit ring-1
                            ring-gray-900/10 hover:ring-gray-900/20
                            dark:ring-gray-100/10 dark:hover:ring-gray-100/20">
                            New products available.{' '}
                            <Link to="/shop" className="font-semibold text-indigo-600 dark:text-indigo-400">
                                <span className="absolute inset-0" aria-hidden="true" />
                                Shop now<span aria-hidden="true">&rarr;</span>
                            </Link>
                        </div>
                    </div>

                    <div className="text-center bg-transparent text-inherit dark:text-inherit">
                        <h1 className="text-4xl font-bold tracking-tight text-inherit dark:text-inherit">
                            Fresh products everyday
                        </h1>
                        <p className="mt-6 text-lg leading-8 text-gray-600 dark:text-gray-200">
                            Quality based products brought to you with fast shipping directly from the producers!
                        </p>
                        <div className="mt-10 flex items-center justify-center gap-x-6">
                            <Link
                                to='/login'
                                className="rounded-md bg-indigo-600 px-3.5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                            >
                                Get started
                            </Link>
                            <Link to="/about-us" className="text-sm font-semibold leading-6 text-inherit dark:text-inherit">
                                Learn more <span aria-hidden="true">→</span>
                            </Link>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    )
}
