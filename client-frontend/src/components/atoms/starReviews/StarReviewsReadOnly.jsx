import React from 'react'

const StarReviewsReadOnly = () => {
    // todo - make half stars as well
    const fullStars = 3.;
    const emptyStars = 5 - fullStars;

    return (
        <div className="items-center">
            <div className="flex">
                {[...Array(fullStars)].map((_, index) => (
                    <svg
                        key={index}
                        className="w-5 h-5 text-yellow-400 mr-1"
                        aria-hidden="true"
                        xmlns="http://www.w3.org/2000/svg"
                        fill="currentColor"
                        viewBox="0 0 22 20"
                    >
                        <path
                            d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"/>
                    </svg>
                ))}


                {[...Array(emptyStars)].map((_, index) => (
                    <svg
                        key={index}
                        className="w-5 h-5 text-zinc-400 mr-1"
                        aria-hidden="true"
                        xmlns="http://www.w3.org/2000/svg"
                        fill="currentColor"
                        viewBox="0 0 22 20"
                    >
                        <path
                            d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"/>
                    </svg>
                ))}
                <p className="ml-2 text-sm font-medium text-zinc-500 dark:text-zinc-400">{fullStars} out of 5</p>
            </div>

            <p className="font-normal text-base leading-4 text-zinc-700 cursor-pointer mt-3 dark:text-zinc-300">
                22 reviews
            </p>
        </div>

    )
}

export default StarReviewsReadOnly;