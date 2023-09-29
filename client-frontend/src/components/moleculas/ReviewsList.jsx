import React from 'react'

const ReviewsList = () => {

    return (
        <div>
            <div className="flex justify-between">
                <div>
                    Reviews!
                </div>
                <div>
                    <button
                        onClick="">Click here</button>
                </div>
            </div>
        </div>
    )
}

export default ReviewsList;

// todo - event is created when a new review is added -  reviewAdded event
// todo - take product and recalculate rating - listener