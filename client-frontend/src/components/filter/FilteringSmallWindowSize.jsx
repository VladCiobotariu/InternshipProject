import React from 'react'

const FilteringSmallWindowSize = () => {

    return (
        <div>
            <div className="block md:hidden lg:hidden xl:hidden 2xl:hidden">
                <button
                    className="flex cursor-pointer items-center gap-2 border-b border-gray-400 pb-1 text-zinc-800 transition hover:border-gray-600 dark:text-white"
                >
                    <span className="text-xl font-medium"> Filters </span>

                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 24 24"
                        strokeWidth="1.5"
                        stroke="currentColor"
                        className="h-4 w-4 rtl:rotate-180"
                    >
                        <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            d="M8.25 4.5l7.5 7.5-7.5 7.5"
                        />
                    </svg>
                </button>
            </div>
        </div>
    )
}

export default FilteringSmallWindowSize;