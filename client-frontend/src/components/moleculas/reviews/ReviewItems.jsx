import React, {useEffect, useState} from 'react';
import ReviewComponent from "./ReviewComponent";
import {getReviewsApi} from "../../../api/ReviewApi";

const ReviewItems = ({productId}) => {

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


    return (
        <div id="reviews">
            <div className="px-10 flex flex-col gap-2 p-5 text-zinc-800 border rounded-2xl border-indigo-200 shadow-md mt-10 mb-10">
                <h1 className="text-xl pt-5 font-bold">Reviews</h1>


                <div className="flex flex-col gap-3 mt-10">
                    {reviews.map((review) => (
                        <div key={review.id}>
                            <ReviewComponent
                                key={review.id}
                                firstName={review.buyer.firstName}
                                lastName={review.buyer.lastName}
                                imageName={review.buyer.imageName}
                                rating={review.rating}
                                description={review.description}
                            />
                        </div>
                    ))}
                </div>
            </div>
        </div>

    );
}

export default ReviewItems;
