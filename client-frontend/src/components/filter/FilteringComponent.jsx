import React, {useEffect, useState} from 'react';
import ExpandableItem from './ExpandableItem';
import SortFilterComponent from './SortFilterComponent';
import FilteringHeader from "./FilteringHeader";
import Tag from './Tag';
import RangeFilterComponent from "./RangeFilterComponent";
import {getLocationsApi} from "../../security/api/LocationApi";
import MultipleChoicesFilterComponent from "./MultipleChoicesFilterComponent";
import {getAllCategoryNames} from "../../security/api/CategoryApi";
import useBreakpoint from "../../hooks/useBreakpoint";
import FilteringSmallWindowSize from "./FilteringSmallWindowSize";

const FilteringComponent = ({filterOptions, onFilterChanged, onSortChanged, isFilterOptionDisplayed}) => {

    const [openFilter, setOpenFilter] = useState(null);
    const [filterTags, setFilterTags] = useState([]);

    const [cityOptions, setCityOptions] = useState([]);
    const [categoryOptions, setCategoryOptions] = useState([]);

    const breakpoint = useBreakpoint()

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

    const getCities = () => {
        getLocationsApi()
            .then((res) => {
                setCityOptions(res.data);
            })
            .catch((err) => console.log(err));
    };
    const getCategoryNames = () => {
        getAllCategoryNames()
            .then((res) => {
                setCategoryOptions(res.data)
            })
            .catch((err) => console.log(err))
    }

    useEffect(() => {
        getCities();
        getCategoryNames();
    }, []);

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
        onFilterChanged(updatedFilterOptions, filterNameToRemove);
    }

    // for category, city
    const onFilterRemovedMultipleOptions = (filterNameToRemove, valueToRemove) => {
        const updatedFilterOptions = {...filterOptions};
        if (Array.isArray(updatedFilterOptions[filterNameToRemove])) {
            updatedFilterOptions[filterNameToRemove] = updatedFilterOptions[filterNameToRemove].filter(
                (value) => value !== valueToRemove
            );
            onFilterChanged(updatedFilterOptions, filterNameToRemove);
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

    // city, category
    const handleFilterMultipleOptionsChanged = (filterName, filterValues) => {
        const newFilterOptions = {...filterOptions};
        newFilterOptions[filterName] = filterValues;

        onFilterChanged(newFilterOptions);
    };


    return (
        <div>
            {breakpoint === "sm" ? (
                <div>
                    <FilteringSmallWindowSize/>
                </div>
            ) : (
                <div className="flex items-center ">
                    <div className="flex gap-4 ">
                        <ExpandableItem
                            label="Price"
                            isOpen={openFilter === 'Price'}
                            onClick={() => handleFilterClick('Price')}
                        >
                            <RangeFilterComponent onClickInside={(e) => e.stopPropagation()}
                                                  toggleRangeFilter={toggleFilter}
                                                  handleRangeChanged={handlePriceChanged}
                                                  labelFrom="Price From"
                                                  labelTo="Price To"
                                                  getRangeFrom={filterOptions.priceFrom}
                                                  getRangeTo={filterOptions.priceTo}/>
                        </ExpandableItem>

                        <ExpandableItem
                            label="City"
                            isOpen={openFilter === 'City'}
                            onClick={() => handleFilterClick('City')}
                        >
                            <MultipleChoicesFilterComponent
                                onClickInside={(e) => e.stopPropagation()}
                                toggleFilter={toggleFilter}
                                handleListChanged={handleFilterMultipleOptionsChanged}
                                list={cityOptions}
                                filterName="cityName"
                                getElementsNames={filterOptions.cityName}/>
                        </ExpandableItem>

                        <ExpandableItem
                            label="Category"
                            isOpen={openFilter === 'Category'}
                            onClick={() => handleFilterClick('Category')}
                        >
                            <MultipleChoicesFilterComponent
                                onClickInside={(e) => e.stopPropagation()}
                                toggleFilter={toggleFilter}
                                handleListChanged={handleFilterMultipleOptionsChanged}
                                list={categoryOptions}
                                filterName="categoryName"
                                getElementsNames={filterOptions.categoryName}/>
                        </ExpandableItem>

                        <ExpandableItem
                            label="Sort By"
                            isOpen={openFilter === 'Sort'}
                            onClick={() => handleFilterClick('Sort')}
                        >
                            <SortFilterComponent onClickInside={(e) => e.stopPropagation()}
                                                 onSortChanged={onSortChanged}/>
                        </ExpandableItem>
                    </div>
                </div>
            )}

            {!isFilterOptionDisplayed &&
                <div className="">
                    <FilteringHeader
                        filterTags={filterTags}
                        removeFilterOneOption={onFilterRemovedOneOption}
                        removeFilterMultipleOptions={onFilterRemovedMultipleOptions}
                    />
                </div>
            }

        </div>
    );
};

export default FilteringComponent;
