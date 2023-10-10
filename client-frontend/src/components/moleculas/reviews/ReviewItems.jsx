import React, {useEffect, useState} from 'react';
import ReviewComponent from "./ReviewComponent";
import {getReviewsApi} from "../../../api/ReviewApi";

const ReviewItems = ({productId, updateProductRating}) => {

    const [reviews, setReviews] = useState([]);

    const getReviewItems = (productId) => {
        getReviewsApi(productId)
            .then((res) => {
                setReviews(res.data)
            })
            .catch((err) => {
                console.log(err)
            })
    }

    useEffect(() => {
        getReviewItems(productId)
    }, []);

    const updateReviewInState = (updatedReview) => {
        const updatedReviewIndex = reviews.findIndex((review) => review.id === updatedReview.id);

        if (updatedReviewIndex !== -1) {
            const updatedReviews = [...reviews];
            updatedReviews[updatedReviewIndex] = updatedReview;
            setReviews(updatedReviews);

            const newProductRating = updatedReviews.reduce((sum, review) => sum + review.rating, 0) / updatedReviews.length;
            updateProductRating(newProductRating)
        }
    };

    return (
        <div id="reviews">
            <div className="px-10 flex flex-col gap-2 p-5 text-zinc-800 border rounded-2xl border-indigo-300 shadow-md mt-10 mb-10">
                <h1 className="text-xl pt-5 font-bold dark:text-white">Reviews</h1>


                <div className="flex flex-col gap-3 mt-10">
                    {reviews.map((review) => (
                        <div key={review.id}>
                            <ReviewComponent
                                key={review.id}
                                reviewId={review.id}
                                firstName={review.buyer.firstName}
                                lastName={review.buyer.lastName}
                                imageName={review.buyer.imageName}
                                rating={review.rating}
                                description={review.description}
                                publishDate={review.publishDate}
                                updateReviewInState={updateReviewInState}
                            />
                        </div>
                    ))}
                </div>
            </div>


        </div>

    );
}

export default ReviewItems;
