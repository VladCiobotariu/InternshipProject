import React from 'react';
import './Search.css'

const Suggestions = ({suggestions, handleClick}) => {
    return (
        <ul className="mt-1 absolute text-sm font-medium w-[300px] sm:w-[150px] text-zinc-800 bg-white border border-gray-200 rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-white">
            {suggestions.map((suggestion, index) => {
                return (
                    <li className="w-full px-4 py-2 border-b border-gray-200 rounded-t-lg dark:border-gray-600 hover:bg-zinc-100 cursor-pointer"
                        key={index}
                        onClick={handleClick}
                    >
                        {suggestion}
                    </li>
                );
            })}

        </ul>
    );
};

export default Suggestions;