import React, {Fragment, useRef} from 'react'
import {Dialog, Transition} from "@headlessui/react";

const BaseModal = ({children, isModalOpen, toggleModal}) => {

    const cancelButtonRef = useRef(null)
    return (
        <div className="">
            <Transition.Root show={isModalOpen} as={Fragment}>
                <Dialog
                    as="div"
                    className="relative z-1 sm:p-5"
                    initialFocus={cancelButtonRef}
                    onClose={toggleModal}
                >
                    <Transition.Child
                        as={Fragment}
                        enter="ease-out duration-300"
                        enterFrom="opacity-0"
                        enterTo="opacity-100"
                        leave="ease-in duration-200"
                        leaveFrom="opacity-100"
                        leaveTo="opacity-0"
                    >
                        <div className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity"/>
                    </Transition.Child>

                    <div className="fixed inset-0 z-10 w-full h-full flex items-center justify-center">
                        <Transition.Child
                            as={Fragment}
                            enter="ease-out duration-300"
                            enterFrom="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
                            enterTo="opacity-100 translate-y-0 sm:scale-100"
                            leave="ease-in duration-200"
                            leaveFrom="opacity-100 translate-y-0 sm:scale-100"
                            leaveTo="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
                        >
                            <Dialog.Panel
                                className="relative w-[500px] transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:w-[350px]">
                                {children}
                            </Dialog.Panel>

                        </Transition.Child>
                    </div>
                </Dialog>
            </Transition.Root>

        </div>
    )
}

export default BaseModal;