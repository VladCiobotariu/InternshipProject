import React from 'react'
import StarReviewsReadOnly from "../../atoms/starReviews/StarReviewsReadOnly";
import ProductRating from "../../atoms/starReviews/ProductRating";

const ReviewComponent = ({firstName, lastName, imageName, rating, description}) => {


    /***
     *
     * @param review {{
     *     id: long,
     *     description: string,
     *     rating: float,
     *     buyer: {
     *         firstName: string,
     *         lastName: string,
     *         imageName: string,
     *         telephone: string
     *     }
     *      }}
     */

    const firstLetterOfFirstName = firstName.charAt(0);
    const firstLetterOfLastName = lastName.charAt(0);

    return (
        <div>
            <div className="flex flex-col gap-4  mb-2 border rounded-2xl shadow-md">
                <div className="flex justify justify-between rounded-t-2xl bg-zinc-200 items-center">
                    <div className="flex gap-2 p-4">
                        <div className="w-7 h-7 text-center text-white rounded-full bg-indigo-500">
                            {firstLetterOfFirstName}{firstLetterOfLastName}
                        </div>
                        <span className="font-semibold">{firstName} {lastName}</span>
                    </div>
                    <div className="flex  gap-1 pr-4">
                        <ProductRating
                            rating={rating}
                            isRatingDisplayed={true}
                            viewType='simple'
                        />
                    </div>
                </div>

                <div className="px-4 pb-6 pt-2">
                    <div>
                        {description}
                    </div>

                    <div className="flex justify-between text-[12px] text-zinc-400 pt-2">
                        <span>Feb 13, 2021</span>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ReviewComponent;