import React, {useState, useEffect} from 'react'
import './Search.css'

const SearchComponent = ({ onSearchText }) => {

    const [searchText, setSearchText] = useState("");

    const handleChange = (e) => {
        setSearchText(e.target.value);
    }

    const handleClick = () => {
        onSearchText(searchText);
    }

    return (
        <div>
            <div className="relative w-[300px] sm:w-[150px]">
                <input
                    type="search"
                    id="search-dropdown"
                    className="block p-2.5 w-full z-20 text-sm text-zinc-800 bg-gray-50 rounded-lg border-l-2 border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-l-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:border-blue-500"
                    placeholder="Search"
                    value={searchText}
                    onChange={handleChange}
                    // todo - add enter
                    required
                />
                <button
                    type="submit"
                    className="absolute top-0 right-0 p-2.5 text-sm font-medium h-full text-white bg-indigo-600 rounded-r-lg border border-indigo-700 hover:bg-indigo-800 focus:ring-4 focus:outline-none focus:ring-indigo-300 dark:bg-indigo-600 dark:hover:bg-indigo-700 dark:focus:ring-indigo-800"
                    onClick={handleClick}
                >
                    <svg
                        className="w-4 h-4"
                        aria-hidden="true"
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 20 20">
                        <path
                            stroke="currentColor"
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            strokeWidth="2"
                            d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"
                        />
                    </svg>
                    <span className="sr-only">Search</span>
                </button>
            </div>
        </div>
    )
}

export default SearchComponent;