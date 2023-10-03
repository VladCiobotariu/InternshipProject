import { object, string } from "yup";
import "yup-phone-lite";

export const shippingAddressSchema = object().shape({
        email: string()
            .required()
            .email("Invalid email"),
        firstName: string()
            .required(),
        lastName: string()
            .required(),
        address: object().shape({
                addressLine1: string()
                    .required(),
                addressLine2: string()
                    .required(),
                country: string()
                    .required(),
                zipCode: string()
                    .required(),
                city: string()
                    .required(),
                state: string()
                    .required()
        }),
        telephone: string()
            .phone("RO", 'Please enter valid RO number')
            .required("Telephone Required")
    })
;