import {number, object, string} from "yup";
import "yup-phone-lite";

export const editReviewSchema = object().shape( {
    description: string()
        .required("Review Description Required"),
    rating: number()
        .required("Rating Required")
        .min(1, "Rating can't be less than 0")
        .max(5, "Rating can't be more then 5")

})