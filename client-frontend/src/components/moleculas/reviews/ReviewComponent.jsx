import React, {useEffect, useState} from 'react'
import ProductRating from "../../atoms/starReviews/ProductRating";
import {useAuth} from "../../../auth/AuthContext";
import EditReviewModal from "../modals/EditReviewModal";
import {getReviewByIdApi} from "../../../api/ReviewApi";

const ReviewComponent = ({reviewId, firstName, lastName, imageName, rating, description, publishDate, updateReviewInState}) => {


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

    const {isAuthenticated} = useAuth();

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [review, setReview] = useState(null);

    const toggleModal = () => {
        setIsModalOpen(!isModalOpen);
    }

    const updateReview = (updatedReview) => {
        setReview(updatedReview)
        updateReviewInState(updatedReview);
    };

    const getReviewById = (reviewId) => {
        getReviewByIdApi(reviewId)
            .then((res) => {
                setReview(res.data)
            })
            .catch((err) => console.log(err))
    }

    useEffect(() => {
        getReviewById(reviewId);
    }, []);

    return (
        <div>
            <div className="flex flex-col gap-4  mb-2 border rounded-2xl shadow-md dark:border-indigo-300">
                <div className="flex justify justify-between rounded-t-2xl bg-zinc-200 dark:bg-[#1a2747] items-center">
                    <div className="flex gap-2 p-4">
                        <div className="w-7 h-7 text-center text-white rounded-full bg-indigo-500 sm:w-5 sm:h-5">
                            <div className="sm:text-xs align-center">
                                {firstLetterOfFirstName}{firstLetterOfLastName}
                            </div>
                        </div>
                        <span className="font-semibold dark:text-white sm:text-xs">{firstName} {lastName}</span>
                    </div>
                    <div className="flex  gap-1 pr-4">
                        <ProductRating
                            rating={rating}
                            isRatingDisplayed={true}
                            viewType='simple'
                        />
                    </div>
                </div>

               <div className="flex justify-between pt-1.5 ">
                   <div className="px-4 pb-6 w-full">
                       <div className="dark:text-white ">
                           {description}
                       </div>

                       <div className="flex justify-between text-[12px] text-zinc-400 pt-2">
                           <span>{publishDate}</span>
                       </div>
                   </div>
                   <div className="mr-5">
                       <svg xmlns="http://www.w3.org/2000/svg"
                            fill="none" viewBox="0 0 24 24"
                            strokeWidth={1.5}
                            stroke="currentColor"
                            className="w-6 h-6 text-indigo-700 cursor-pointer dark:text-indigo-400 dark:hover:text-indigo-300"
                            onClick={() => toggleModal()}
                       >
                           <path strokeLinecap="round" strokeLinejoin="round" d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10" />
                       </svg>
                   </div>
               </div>
            </div>

            <EditReviewModal
                toggleModal={toggleModal}
                isModalOpen={isModalOpen}
                setIsModalOpen={setIsModalOpen}
                review={review}
                updateReview={updateReview}
            />
        </div>
    )
}

export default ReviewComponent;