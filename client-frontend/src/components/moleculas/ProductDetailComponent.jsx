import React from 'react'
import ImageDetails from "../atoms/productDetails/ImageDetails";
import ProductDetails from "./details/ProductDetails";
import useBreakpoint from "../../hooks/useBreakpoint";
import './ProductDetailsComponent.css'
import StarReviewsReadOnly from "../atoms/starReview/StarReviewsReadOnly";

const ProductDetailComponent = () => {

    const breakpoint = useBreakpoint()

    return (
        <div className="mx-auto mt-16 max-w-7xl px-10">
            <div className={`flex justify-center items-center ${breakpoint === 'sm' ? 'sm:flex-col' : 'flex-row'} gap-8`}>

                <div className="w-full items-center grid-wrapper">

                    <div className="grid-header">
                        <p className="font-normal leading-4 text-zinc-600 dark:text-zinc-300">
                            Home / Furniture / Wooden Stool
                        </p>
                        <h2 className="font-semibold text-3xl leading-7 text-zinc-800 mt-4 dark:text-zinc-100">
                            Wooden Stool
                        </h2>
                    </div>

                    <div className="mt-4 block grid-reviews ">
                        <div className="">
                            <StarReviewsReadOnly />
                        </div>
                        <p className="font-normal text-base leading-4 text-zinc-700 cursor-pointer mt-3 dark:text-zinc-300">
                            22 reviews
                        </p>
                    </div>


                    <div className={`${breakpoint === 'sm' ? 'grid-details' : 'flex'} mt-4`}>
                        <div className={` ${breakpoint === 'sm' ? 'mb-4' : 'flex-shrink-0'}`}>
                            <ImageDetails/>
                        </div>

                        <div className={` ${breakpoint === 'sm' ? 'ml-0' : 'flex-grow ml-8'}`}>
                            <ProductDetails/>
                        </div>
                    </div>
                </div>

            </div>
            <div className="flex justify-center items-center w-full">
                <div
                    className="w-full mt-10">
                    AICI VOR FI REVIEW URILE
                </div>
            </div>
        </div>
    );
};

export default ProductDetailComponent;