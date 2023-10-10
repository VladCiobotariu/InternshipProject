import React from 'react'
import BaseModal from "../../atoms/BaseModal";
import {Form, Formik} from "formik";
import TextInputWithError from "../../atoms/input/TextInputWithError";
import {updateReviewApi} from "../../../api/ReviewApi";
import {editReviewSchema} from "../../../validators/editReviewSchema";

const EditReviewModal = ({isModalOpen, toggleModal, setIsModalOpen, review, updateReview}) => {

    const onSubmit = (values) => {
        updateReviewApi(values.id, values.description, values.rating)
            .then((res) => {
                updateReview(res.data)
                setIsModalOpen(false);
            })
            .catch((err) => console.log(err))
    }

    return (
        <div>
            <BaseModal
                isModalOpen={isModalOpen}
                toggleModal={toggleModal}
                children={review &&
                    <Formik
                        initialValues={review}
                        onSubmit={(values)=> {
                            onSubmit(values)
                        }}
                        validationSchema={editReviewSchema}
                        validateOnBlur={false}
                        validateOnChange={false}
                    >

                        {({ errors, validateField}) => {

                            return (

                                <Form className="px-10 py-10">

                                    <p className="text-xl font-bold mb-6">Edit the review</p>
                                    <div className="space-y-2">

                                        <div className="flex flex-col space-y-8">
                                            <div className="w-full">
                                                <TextInputWithError fieldName={'description'}
                                                                    errorName={errors.description}
                                                                    labelName={'Description'}
                                                                    onBlur={()=>validateField('description')}
                                                                    fieldType={"textarea"}
                                                />
                                            </div>

                                            <div className="w-full">
                                                <TextInputWithError fieldName={'rating'}
                                                                    errorName={errors.rating}
                                                                    labelName={'Rating'}
                                                                    onBlur={()=>validateField('rating')}
                                                                    fieldType={"text"}
                                                />
                                            </div>
                                        </div>


                                    </div>

                                    <button type="submit" className="mt-10 flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Save</button>
                                </Form>
                            );
                        }}
                    </Formik>
                }
            />
        </div>
    )
}

export default EditReviewModal;