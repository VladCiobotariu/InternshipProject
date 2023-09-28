import React from 'react'
import image from './mango.jpg'
const ImageDetails = () => {

    return (
        <div className="flex justify-center items-center w-full h-full pr-2 border-r sm:border-none sm:mt-4">
            <img
                src={image}
                alt="Apple"
                className="w-[30rem] md:w-[22rem] sm:w-[15rem]"/>
        </div>
    )
}

export default ImageDetails;