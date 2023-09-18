import React, {useEffect, useState} from 'react';
import FilterAndSortingItem from './FilterAndSortingItem';
import PriceFilterComponent from './PriceFilterComponent';
import CityFilterComponent from './CityFilterComponent';
import CategoryFilterComponent from './CategoryFilterComponent';
import SortFilterComponent from './SortFilterComponent';
import FilteringHeader from "./FilteringHeader";
import Tag from './Tag';

const FilteringComponent = ({ filterOptions, onFilterChanged }) => {
    const [openFilter, setOpenFilter] = useState(null);
    const [filterTags, setFilterTags] = useState([]);

    const togglePriceFilter = () => {
        setOpenFilter(null);
    };

    const handleFilterClick = (filter) => {
        if (openFilter === filter) {
            setOpenFilter(null); // same filter clicked again
        } else {
            setOpenFilter(filter);
        }
    };

    useEffect(() => {
        const newFilterTags = Object.keys(filterOptions)
            .filter(key => !!filterOptions[key]) // get only not null elements
            .map(key => {
                return new Tag(key, filterOptions[key], "ONE_VALUE");
            });

        setFilterTags(newFilterTags);
    }, [filterOptions]);
    // onFilterAdded

    const onFilterRemoved = (filterNameToRemove) => {
        const updatedFilterOptions = {...filterOptions};
        delete updatedFilterOptions[filterNameToRemove];
        onFilterChanged(updatedFilterOptions);
    }

    const handlePriceChanged = (priceFrom, priceTo) => {
        const newFilterOptions = {...filterOptions, "priceFrom": priceFrom, "priceTo": priceTo};
        onFilterChanged(newFilterOptions);
    }

    // restul
    const handleFilterChanged = (filterName, filterValue) => {
        const newFilterOptions = {...filterOptions, [filterName]: filterValue};
        onFilterChanged(newFilterOptions);
    }

    return (
        <div>
            <div className="flex items-center">
                <div className="sm:hidden flex gap-4">
                    <FilterAndSortingItem
                        // to do - name expandableItem
                        label="Price"
                        isOpen={openFilter === 'Price'}
                        onClick={() => handleFilterClick('Price')}
                    >
                        <PriceFilterComponent onClickInside={(e) => e.stopPropagation()}
                                              handlePriceChanged={handlePriceChanged}
                                              togglePriceFilter={togglePriceFilter}/>
                    </FilterAndSortingItem>

                    <FilterAndSortingItem
                        label="City"
                        isOpen={openFilter === 'City'}
                        onClick={() => handleFilterClick('City')}
                    >
                        <CityFilterComponent onClickInside={(e) => e.stopPropagation()}/>
                    </FilterAndSortingItem>

                    <FilterAndSortingItem
                        label="Category"
                        isOpen={openFilter === 'Category'}
                        onClick={() => handleFilterClick('Category')}
                    >
                        <CategoryFilterComponent onClickInside={(e) => e.stopPropagation()}/>
                    </FilterAndSortingItem>

                    <FilterAndSortingItem
                        label="Sort By"
                        isOpen={openFilter === 'Sort'}
                        onClick={() => handleFilterClick('Sort')}
                    >
                        <SortFilterComponent onClickInside={(e) => e.stopPropagation()}/>
                    </FilterAndSortingItem>
                </div>
            </div>

            <div>
                <FilteringHeader
                    filterTags={filterTags}
                    removeFilter={onFilterRemoved}
                    />
            </div>

        </div>
    );
};

export default FilteringComponent;
