import React from 'react';
import FilterTag from "./FilterTag";

const FilteringHeader = ({ filterTags, removeFilterOneOption, removeFilterMultipleOptions }) => {

    return (
        <div className="bg-red-200 rounded-xl h-[35px] flex items-center px-4 mt-5 h-full py-2">
            <div className="inline-flex gap-3 flex-wrap">
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
        </div>
    );
};

export default FilteringHeader;