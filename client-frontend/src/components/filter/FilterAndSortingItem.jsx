import React, {useEffect, useState} from 'react';

const FilterAndSortingItem = ({ label, children, isOpen, onClick}) => {

    return (
        <div className="relative">
            <details className="group" onClick={() => onClick()}>
                <summary
                    className="flex cursor-pointer items-center gap-2 border-b border-zinc-300 pb-1 text-zinc-800 transition hover:border-zinc-600 dark:text-white"
                >
                    <span className="text-xl font-medium">
                        {label}
                    </span>
                    <span className={`transform ${isOpen ? 'rotate-90' : ''}`}>
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            fill="none"
                            viewBox="0 0 24 24"
                            strokeWidth="1.5"
                            stroke="currentColor"
                            className="h-4 w-4 transform -rotate-90"
                        >
                            <path
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                d="M19.5 8.25l-7.5 7.5-7.5-7.5"
                            />
                        </svg>
                    </span>
                </summary>

                <div className="z-50 group-open:absolute group-open:top-auto  ltr:group-open:start-0">
                    {isOpen && <div className="z-50 absolute top-10">{children}</div>}
                </div>
            </details>
        </div>
    );
};

export default FilterAndSortingItem;
