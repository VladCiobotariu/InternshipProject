import React, {useEffect, useState} from 'react';
import ExpandableItem from '../../atoms/filtering/ExpandableItem';
import SortFilterComponent from './SortFilterComponent';
import FilterTagContainer from "./FilterTagContainer";
import Tag from '../../atoms/filtering/Tag';
import RangeFilterComponent from "./RangeFilterComponent";
import {getLocationsApi} from "../../../api/LocationApi";
import MultipleChoicesFilterComponent from "./MultipleChoicesFilterComponent";
import {getAllCategoryNames} from "../../../api/CategoryApi";
import useBreakpoint from "../../../hooks/useBreakpoint";
import FilteringSmallWindowSize from "../../atoms/filtering/FilteringSmallWindowSize";
import SearchComponent from "../../atoms/input/SearchComponent";

const FilteringComponent = ({filterOptions, onFilterChanged, onSortChanged }) => {

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
            .filter(key => !!filterOptions[key])
            .filter(key => key !== "productName")
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
        const updatedFilterOptions = {...filterOptions};
        if (Array.isArray(updatedFilterOptions[filterNameToRemove])) {
            updatedFilterOptions[filterNameToRemove] = updatedFilterOptions[filterNameToRemove].filter(
                (value) => value !== valueToRemove
            );
            onFilterChanged(updatedFilterOptions);
        }
    };

    const removeAllTags = () => {
        const newFilterOptions = {
            priceFrom: null,
            priceTo: null,
            categoryName: [],
            cityName: [],
            productName: null,
        };
        onFilterChanged(newFilterOptions);
    }

    // price
    const handlePriceChanged = (priceFrom, priceTo) => {
        const newFilterOptions = {...filterOptions, "priceFrom": priceFrom, "priceTo": priceTo};
        onFilterChanged(newFilterOptions);
    }

    // productName
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

    /**  false if displayed, true if not*/
    const isFilterOptionsEmpty = Object.keys(filterOptions).every((key) => {
        const value = filterOptions[key];
        if(key==="productName" && value) {
            return true;
        }
        if (Array.isArray(value)) {
            return value.length === 0;
        }
        return value === null || value.toString() === '' || key === 'filterName';
    });


    return (
        <div>
            {breakpoint === "sm" ? (
                <div>
                    <FilteringSmallWindowSize/>
                </div>
            ) : (
                <div className="">
                    <div className="flex justify-between ">
                        <div className="inline-flex  gap-4">
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

                        <div>
                            <SearchComponent
                                handleSearchChanged={handleFilterChanged}
                                filterName="productName"/>
                        </div>
                    </div>

                </div>
            )}

            {!isFilterOptionsEmpty &&
                <div className="">
                    <FilterTagContainer
                        filterTags={filterTags}
                        removeFilterOneOption={onFilterRemovedOneOption}
                        removeFilterMultipleOptions={onFilterRemovedMultipleOptions}
                        removeAllTags={removeAllTags}
                    />
                </div>
            }

        </div>
    );
};

export default FilteringComponent;
