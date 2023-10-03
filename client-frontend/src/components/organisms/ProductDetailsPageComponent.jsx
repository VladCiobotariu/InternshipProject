import React, {useEffect, useState} from 'react'
import ProductInformation from "../moleculas/productInformation/ProductInformation";
import StarReviewsReadOnly from "../atoms/starReviews/StarReviewsReadOnly";
import ProductHistorySteps from "../atoms/products/ProductHistorySteps";
import ReviewsList from "../moleculas/ReviewsList";
import {getProductByIdApi} from "../../api/ProductApi";
import {useParams} from "react-router-dom";
import {baseURL} from "../../auth/ApiClient";

const ProductDetailsPageComponent = () => {

    const {productId} = useParams();
    const [product, setProduct] = useState(null);

    const getProduct = (productId) => {
        getProductByIdApi(productId)
            .then((res) => {
                setProduct(res.data)
            })
            .catch((err) => console.log(err))
    }

    useEffect(() => {
        getProduct(productId);
    }, []);

    return (
        product && (<div className="mx-auto mt-16 max-w-7xl px-10">
            <div className={`flex justify-center items-center sm:flex-col gap-8`}>

                <div className="w-full items-center">

                    <div className="">
                        <p className="font-normal leading-4 text-zinc-600 dark:text-zinc-300">
                            <ProductHistorySteps
                                categoryName={product.category.name}
                                productName={product.name}
                            />
                        </p>
                        <h2 className="font-semibold text-3xl leading-7 text-zinc-800 mt-4 dark:text-zinc-100">
                            {product.name}
                        </h2>
                    </div>

                    <div className="mt-4 block">
                        <StarReviewsReadOnly
                            rating={product.reviewInformation.productRating}
                            numReviews={product.reviewInformation.numberReviews}
                            isRatingDisplayed={product.reviewInformation.ratingApplicable}
                        />
                    </div>


                    <div className="flex sm:flex-col mt-4">
                        <div className="flex-shrink-0 sm:mb-4">
                            <div className="flex justify-center items-center w-full h-full pr-2 border-r sm:border-none sm:mt-4">
                                <img
                                    src={`${baseURL}${product.imageName}`}
                                    alt={product.name}
                                    className="w-[30rem] md:w-[20rem]"/>
                            </div>
                        </div>

                        <div className="flex-grow ml-8 sm:ml-0">
                            <ProductInformation
                                description={product.description}
                                price={product.price}
                                category={product.category.name}
                                producer={product.seller.alias}
                                city={product.seller.city}
                                productId={product.id}
                            />
                        </div>
                    </div>
                </div>

            </div>
            <div className="flex justify-center items-center w-full">
                <div
                    className="w-full mt-10">
                    <ReviewsList />
                </div>
            </div>
        </div>)
    );
};

export default ProductDetailsPageComponent;