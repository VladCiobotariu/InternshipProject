import React from 'react';

const SortFilterComponent = ({onSortChanged}) => {

    const handleSortClick = (criteria, orderSort) => {
        const newSort = { criteria, orderSort }
        onSortChanged(newSort);
    }

    return (
        <div>
            <div className="w-56 bg-white">
                <div className="text-center mx-auto bg-white rounded-2xl border border-zinc-300 shadow-lg">
                    <div className="flex flex-col cursor-pointer font-thin text-zinc-800">
                        <div className="hover:bg-indigo-100 py-2 border-b rounded-t-2xl"
                             onClick={() => handleSortClick('productPrice', 'asc')}>Price: ascending
                        </div>
                        <div className="hover:bg-indigo-100 py-2 border-b"
                             onClick={() => handleSortClick('productPrice', 'desc')}>Price: descending
                        </div>
                        <div className="hover:bg-indigo-100 py-2 border-b"
                             onClick={() => handleSortClick('productName', 'asc')}>Name: ascending
                        </div>
                        <div className="hover:bg-indigo-100 py-2 border-b rounded-b-2xl"
                             onClick={() => handleSortClick('productName', 'desc')}>Name: descending
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default SortFilterComponent;

