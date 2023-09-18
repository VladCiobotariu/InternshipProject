import React from 'react'
import RangeFilterTag from "./RangeFilterTag";
import FilterTag from "./FilterTag";

const FilteringHeader = ({filterTags, removeFilter}) => {

    return (
        <div className="bg-red-200 rounded-xl h-[35px] flex items-center px-4 mt-5">
            <div className="inline-flex gap-6 " >
                {filterTags.map((tag, index) => (
                    // check if the filterType is RANGE or ONE_VALUE
                    // if its RANGE then put <RangeFilterTag /> otherwise <FilterTag />
                    <div key={index}>
                        {/*{tag.filterType === 'RANGE' ? (*/}
                        {/*    <RangeFilterTag*/}
                        {/*        filterName={tag.filterName}*/}
                        {/*        value={tag.value}*/}
                        {/*        valueTo={tag.valueTo}*/}
                        {/*        removeFilter={() => removeFilter(index)} />*/}
                        {/*) : (*/}
                            <FilterTag
                                filterName={tag.filterName}
                                value={tag.value}
                                removeFilter={() => removeFilter(tag.filterName)}
                            />
                        {/*)}*/}
                    </div>
                    ))}
            </div>
        </div>
    )
}

export default FilteringHeader;