import React from 'react';
import FilterTag from "./productsFilter/FilterTag";

const FilterTagContainer = ({ filterTags, removeFilterOneOption, removeFilterMultipleOptions, removeAllTags }) => {

    return (
        <div className="bg-zinc-200 border border-zinc-300 rounded-xl flex items-center px-4 my-5 py-2">
            <div className="inline-flex gap-3 flex-wrap ">
                {filterTags.map((tag, index) => (
                    <div key={index} className="inline-flex gap-3">
                        {Array.isArray(tag.value) ? (
                            tag.value.map((value, valueIndex) => (
                                <FilterTag
                                    key={valueIndex}
                                    filterName={tag.filterName}
                                    value={value}
                                    removeFilter={() => removeFilterMultipleOptions(tag.filterName, value)}
                                />
                            ))
                        ) : (
                            <div>
                                <FilterTag
                                    filterName={tag.filterName}
                                    value={tag.value}
                                    removeFilter={() => removeFilterOneOption(tag.filterName)}
                                />
                            </div>
                        )}
                    </div>
                ))}
            </div>
            <div className="flex-grow"></div> {/* Add a flex-grow element to push the button to the end */}
            <div className="flex justify-end">
                <button className="font-semibold text-zinc-800 hover:bg-zinc-100 rounded-2xl px-2 py-1"
                            onClick={removeAllTags}>
                    Clear All
                </button>

            </div>
        </div>
    );
};

export default FilterTagContainer;