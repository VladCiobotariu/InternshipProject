import React from 'react'
import StarReviewsReadOnly from "./StarReviewsReadOnly";

const ProductRating = ({rating, numReviews, isRatingDisplayed, viewType}) => {
    /**
     * viewType can be simple (only the stars), detailed (stars + rating), extended (stars+rating+numReviews)
     */

    const handleClickScroll = () => {
        const element = document.getElementById('reviews');
        if (element) {
            element.scrollIntoView({behavior: 'smooth'})
        }
    }

    return (
        <div className="items-center">
            {isRatingDisplayed ? (
                    <div>
                        <div className="flex">
                            <StarReviewsReadOnly
                                rating={rating}/>
                            {(viewType === 'detailed' || viewType === 'extended') && (
                                <p className="ml-2 text-sm font-medium text-zinc-500 dark:text-zinc-400">
                                    {rating.toFixed(2)} out of 5
                                </p>
                            )}

                        </div>
                        {viewType === 'extended' && (
                            <p className="font-normal text-base leading-4 text-zinc-700 cursor-pointer mt-3 dark:text-zinc-300 hover:underline"
                               onClick={handleClickScroll}>
                                {numReviews} reviews
                            </p>
                        )}

                    </div>
                ) :
                (
                    <div>
                        Not enough reviews yet.
                    </div>
                )}
        </div>
    )
}

export default ProductRating;