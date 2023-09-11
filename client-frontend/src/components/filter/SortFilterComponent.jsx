import React from 'react';

const SortFilterComponent = () => {

    return (
        <div>
            <div className="w-56 rounded border border-zinc-300 bg-white">
                <div className="px-2 pt-2 pb-2 bg-white rounded-md shadow-lg dark-mode:bg-gray-700">
                    <div className="flex flex-col hover-bg-black cursor-pointer font-thin gap-4">
                        <div className="hover:bg-gray-200"
                             onClick={() => null}>Price: ascending</div>
                        <div className="hover:bg-gray-200"
                             onClick={() => null}>Price: descending</div>
                        <div className="hover:bg-gray-200"
                             onClick={() => null}>Name: ascending</div>
                        <div className="hover:bg-gray-200"
                             onClick={() => null}>Name: descending</div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default SortFilterComponent;

