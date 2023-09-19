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

    const toggleFilter = () => {
        setOpenFilter(null);
    }

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


    // for price, sort
    const onFilterRemovedOneOption = (filterNameToRemove) => {
        const updatedFilterOptions = {...filterOptions};
        delete updatedFilterOptions[filterNameToRemove];
        onFilterChanged(updatedFilterOptions);
    }

    // for category, city
    const onFilterRemovedMultipleOptions = (filterNameToRemove, valueToRemove) => {
        const updatedFilterOptions = { ...filterOptions };
        if (Array.isArray(updatedFilterOptions[filterNameToRemove])) {
            updatedFilterOptions[filterNameToRemove] = updatedFilterOptions[filterNameToRemove].filter(
                (value) => value !== valueToRemove
            );
            onFilterChanged(updatedFilterOptions);
        }
    };

    // price
    const handlePriceChanged = (priceFrom, priceTo) => {
        const newFilterOptions = {...filterOptions, "priceFrom": priceFrom, "priceTo": priceTo};
        onFilterChanged(newFilterOptions);
    }

    // sort
    const handleFilterChanged = (filterName, filterValue) => {
        const newFilterOptions = {...filterOptions, [filterName]: filterValue};
        onFilterChanged(newFilterOptions);
    }

    // price, category
    const handleFilterMultipleOptionsChanged = (filterName, filterValues) => {
        const newFilterOptions = { ...filterOptions };
        if (Array.isArray(filterValues)) {
            newFilterOptions[filterName] = [];
            newFilterOptions[filterName] = filterValues;
        } else {
            newFilterOptions[filterName] = [...(newFilterOptions[filterName] || []), filterValues];
        }
        onFilterChanged(newFilterOptions);
    };


    return (
        <div>
            <div className="flex items-center">
                <div className="flex gap-4">
                    <FilterAndSortingItem
                        // todo - name expandableItem
                        label="Price"
                        isOpen={openFilter === 'Price'}
                        onClick={() => handleFilterClick('Price')}
                    >
                        <PriceFilterComponent onClickInside={(e) => e.stopPropagation()}
                                              handlePriceChanged={handlePriceChanged}
                                              togglePriceFilter={toggleFilter}
                                              getPriceFrom={filterOptions.priceFrom}
                                              getPriceTo={filterOptions.priceTo}
                        />
                    </FilterAndSortingItem>

                    <FilterAndSortingItem
                        label="City"
                        isOpen={openFilter === 'City'}
                        onClick={() => handleFilterClick('City')}
                    >
                        <CityFilterComponent onClickInside={(e) => e.stopPropagation()}
                                             handleCityChanged={handleFilterMultipleOptionsChanged}
                                             toggleCityFilter={toggleFilter}
                                             getCityNames={filterOptions.cityName}/>
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

            <div className="max-h-full">
                <FilteringHeader
                    filterTags={filterTags}
                    removeFilterOneOption={onFilterRemovedOneOption}
                    removeFilterMultipleOptions={onFilterRemovedMultipleOptions}
                    />
            </div>

        </div>
    );
};

export default FilteringComponent;
