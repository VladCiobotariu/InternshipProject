import React from 'react';
import './Search.css'

const Suggestions = ({suggestions, suggestionIndex, handleClick}) => {
    return (
        <ul className="suggestions relative">
            {suggestions.map((suggestion, index) => {
                return (
                    <li
                        className={index === suggestionIndex ? "active" : ""}
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