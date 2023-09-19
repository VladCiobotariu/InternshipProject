import { object, string } from "yup";
import "yup-phone-lite";

export const checkoutSchema = object().shape({
        email: string()
            .required()
            .email("Invalid email"),
        firstName: string()
            .required(),
        lastName: string()
            .required(),
        telephone: string()
            .phone("RO", 'Please enter valid RO number')
            .required("Telephone Required")
    })
;