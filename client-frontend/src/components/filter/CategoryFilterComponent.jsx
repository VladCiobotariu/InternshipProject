import React from 'react'

const CategoryFilterComponent = ({onClickInside}) =>{

    return (
        <div onClick={onClickInside}>
            <div className="w-56 rounded border border-zinc-300 bg-white">
                <div className="px-2 pt-2 pb-2 bg-white rounded-md shadow-lg dark-mode:bg-gray-700">
                    Category filter
                </div>
            </div>
        </div>
    );
}

export default CategoryFilterComponent;