import { object, string, ref } from "yup";
import {api} from "../../security/ApiClient";
import "yup-phone-lite";

const getCharacterValidationError = (str) => {
    return `Your password must have at least 1 ${str} character`;
};

export const passwordSchema = object().shape({
        password: string()
            .required("Please enter a password")
            .min(8, "Password must have at least 8 characters")
            .max(30, "Password must be at max 30 characters")
            .matches(/[0-9]/, getCharacterValidationError("digit"))
            .matches(/[a-z]/, getCharacterValidationError("lowercase"))
            .matches(/[A-Z]/, getCharacterValidationError("uppercase"))
            .matches(/[!@#%^&*$]/, 'Must contain at least 1 special character'),
        confirmPassword: string()
            .required("Please re-type your password")
            .oneOf([ref("password")], "Passwords does not match"),
        email: string()
            .required()
            .email("Invalid email")
            .test('checkDuplicateEmail', "Email already registered", function (value){
                return new Promise((resolve) => {
                    api.get(`/users/${value}`)
                        .then(() => resolve(false))
                        .catch(() => resolve(true))
                });
            }),
        firstName: string()
            .required(),
        lastName: string()
            .required(),
        telephone: string()
            .phone("RO", 'Please enter valid RO number')
            .required("Telephone Required")
    })
;