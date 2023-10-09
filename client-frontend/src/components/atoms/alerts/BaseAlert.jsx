function BaseAlert({type, title, paragraph, onCloseButton}){
    return(

        <div className="max-w-[1100px] md:mr-4 lg:mr-6 z-10 absolute sm:sticky sm:top-6 sm:mt-4 w-full transform sm:transform-none translate-y-2 mx-auto left-0 right-0 flex justify-end">
            <div role="alert" className={`max-w-sm sm:max-w-sm sm:mx-auto p-4 rounded-xl
                      border border-gray-100 bg-gray-200 dark:bg-white
                      ${type==='danger' ? 'border-red-500 border-s-4 bg-red-50' : ''}
                      ${type==='info' ? 'border-blue-800 border-s-4 bg-blue-50' : ''}
                      ${type==='success' ? 'border border-gray-100 bg-white' : ''}
                      ${type==='warning' ? 'border-yellow-500 border-s-4 bg-yellow-50' : ''}
                      `}>
                <div className="flex items-start gap-4">

                    <span className={`text-black
                                      ${type==='info' ? 'text-blue-800 dark:text-blue-400' : ''}
                                      ${type==='danger' ? 'text-red-800 dark:text-red-400' : ''}
                                      ${type==='warning' ? 'text-yellow-800 dark:text-yellow-300' : ''}
                                   `}>
                        {type==='success' &&
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                fill="none"
                                viewBox="0 0 24 24"
                                strokeWidth="1.5"
                                stroke="currentColor"
                                className="h-6 w-6 text-green-600"
                            >
                                <path
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                    d="M9 12.75L11.25 15 15 9.75M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                                />
                            </svg>
                        }
                        {type!=='success' &&
                            <svg className="flex-shrink-0 w-6 h-6" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24">
                                <path transform="translate(2, 1)" d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
                            </svg>
                        }
                    </span>



                    <div className="flex-1">
                        <strong className={`block font-medium text-gray-900
                                      ${type==='danger' ? 'text-red-800' : ''}
                                      ${type==='info' ? 'text-blue-800' : ''}
                                      ${type==='warning' ? '' : ''}
                        `}>
                            {title}
                        </strong>

                        <p className={`mt-1 text-sm text-gray-700
                                  ${type==='danger' ? 'text-red-700' : ''}
                                  ${type==='info' ? 'text-blue-700' : ''}
                                  ${type==='warning' ? '' : ''}
                    `}>
                            {paragraph}
                        </p>
                    </div>

                    <button onClick={onCloseButton} className="text-gray-500 transition hover:text-gray-600">
                        <span className="sr-only">Dismiss popup</span>

                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            fill="none"
                            viewBox="0 0 24 24"
                            strokeWidth="1.5"
                            stroke="currentColor"
                            className="h-6 w-6"
                        >
                            <path
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                d="M6 18L18 6M6 6l12 12"
                            />
                        </svg>
                    </button>

                </div>
            </div>
        </div>
    )
}

export default BaseAlert