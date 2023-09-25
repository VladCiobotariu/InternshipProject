import React from 'react';
import './SelectComponent.css';

const SelectComponent = ({itemsPerPage, handleItemsPerPageChange}) => {

    return (
        <div className="mr-10">
            <select value={itemsPerPage} onChange={handleItemsPerPageChange} className="ml-auto mb-2 dark:text-gray-800 select">
                <option value="4">4 per page</option>
                <option value="8">8 per page</option>
                <option value="12">12 per page</option>
            </select>
        </div>
    )
}

export default SelectComponent;