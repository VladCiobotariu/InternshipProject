import React, {useEffect, useState} from 'react';
import {useLocation, useNavigate, useParams, useSearchParams} from "react-router-dom";
import ProductComponent from "./ProductComponent";
import {getProductsApi} from "../../security/api/ProductApi";
import SearchComponent from '../search/SearchComponent';
import FilteringComponent from "../filter/FilteringComponent";
import NoEntityMessageComponent from "../nonExistingEntities/NoEntityMessageComponent";

function ProductPageComponent() {

    const {categoryName} = useParams();

    const [products, setProducts] = useState([]);
    const [itemsPerPage, setItemsPerPage] = useState(8);
    const [totalNumberProducts, setTotalNumberProducts] = useState(0);
    const [currentPage, setCurrentPage] = useState(1);

    const [productSearchFilter, setProductSearchFilter] = useState(null); // todo - make it to filterOptions
    const [productSort, setProductSort] = useState({criteria: null, orderSort: null});

    const [isFilterOptionDisplayed, setIsFilterOptionDisplayed] = useState(false);

    const location = useLocation()
    const navigate = useNavigate();
    const queryParams = new URLSearchParams(location.search)


    const [filterOptions, setFilterOptions] = useState({
        priceFrom: queryParams.get('priceFrom') ? parseInt(queryParams.get('priceFrom')) : null,
        priceTo: queryParams.get('priceTo') ? parseInt(queryParams.get('priceTo')) : null,
        categoryName: queryParams.get('categoryName') ? queryParams.get('categoryName').split('|') : [],
        cityName: queryParams.get('cityName') ? queryParams.get('cityName').split('|') : [],
        productName: null,
    });

    const getProducts = (newItemsPerPage, page, sortSpecs, filterSpecs) => {
        setItemsPerPage(newItemsPerPage);
        getProductsApi(page, newItemsPerPage, sortSpecs, filterSpecs)
            .then((res) => {
                setProducts(res.data.data);
                setTotalNumberProducts(res.data.numberOfElements);
            })
            .catch((err) => console.log(err))
    }


    useEffect(() => {
        const filterSpecs = buildFilterSpecs();
        const sortSpecs = buildSortSpecs();
        setOrRemoveQueryParameters(filterOptions);
        navigate({ search: queryParams.toString() });
        setIsFilterOptionDisplayed(isFilterOptionsEmpty);
        getProducts(itemsPerPage, currentPage, sortSpecs, filterSpecs);
    }, [productSearchFilter, productSort, filterOptions, currentPage, itemsPerPage, categoryName]);

    const handleItemsPerPageChange = (event) => {
        const newItemsPerPage = parseInt(event.target.value);
        setCurrentPage(1);
        getProducts(newItemsPerPage, 1);
    }

    const handleOnProductSearchChanged = (searchText) => {
        if (searchText.length > 1) {
            setProductSearchFilter(searchText); // ban
        } else {
            setProductSearchFilter(null);
        }
    }

    const handleSortChanged = (sortFilter) => {
        setProductSort(sortFilter);
    }

    const createFilterCriteria = (criteria, operation, value) => {
        return `${criteria}[${operation}]${value}`;
    }
    const createSortCriteria = (criteria, orderSort) => {
        return `${criteria}-${orderSort}`;
    }

    const createValueForFilterCriteria = (filterOption) => {
        if (Array.isArray(filterOption)) {
            if (filterOption.length > 1) {
                return filterOption.join('|');
            } else if (filterOption.length === 1) {
                return filterOption[0];
            } else {
                return null;
            }
        } else {
            return filterOption;
        }
    };

    const buildFilterSpecs = () => {
        const filterSearchSpec = [];
        if (productSearchFilter) {
            filterSearchSpec.push(createFilterCriteria("productName", "like", productSearchFilter));
        }
        if (filterOptions.categoryName.length) {
            const value = createValueForFilterCriteria(filterOptions.categoryName);
            filterSearchSpec.push(createFilterCriteria("categoryName", "eq", value));
        }
        if(filterOptions.cityName.length) {
            const value = createValueForFilterCriteria(filterOptions.cityName);
            filterSearchSpec.push(createFilterCriteria("cityName", "eq", value));
        }
        if (filterOptions.priceFrom) {
            filterSearchSpec.push(createFilterCriteria("priceFrom", "gte", filterOptions.priceFrom));
        }
        if (filterOptions.priceTo) {
            filterSearchSpec.push(createFilterCriteria("priceTo", "lte", filterOptions.priceTo));
        }
        return filterSearchSpec;
    }

    const buildSortSpecs = () => {
        const sortSpecs = [];
        if (productSort.criteria && productSort.orderSort) {
            sortSpecs.push(createSortCriteria(productSort.criteria, productSort.orderSort));
        }
        return sortSpecs;
    }

    const setOrRemoveQueryParameters = (filterOptions) => {
        queryParams.forEach((value, key) => {
            if (!(key in filterOptions)) { //
                queryParams.delete(key);
            }
        });

        for (const key in filterOptions) {
            const value = filterOptions[key];
            if (value !== null && value !== '' && (Array.isArray(value) ? value.length > 0 : true)) {
                if (Array.isArray(value)) {
                    queryParams.set(key, value.join('|'));
                } else {
                    queryParams.set(key, value);
                }
            } else {
                queryParams.delete(key);
            }
        }
    };

    const handleOnFilterChanged = (newFilterOptions) => {
        setFilterOptions(newFilterOptions);
    }

    // todo - move this in filteringComponent
    const isFilterOptionsEmpty = Object.values(filterOptions).every((value) => {
        if (Array.isArray(value)) {
            return value.length === 0;
        }
        return value === null || value.toString() === '';

    });

    return (
        <div>
            <section>
                <div className="mx-auto mt-16 max-w-7xl px-10">
                    <header>
                        <h2 className="text-3xl mb-10 font-bold text-zinc-800 dark:text-white">
                            {categoryName ? `Check the ${categoryName}!` : 'All Products'}
                        </h2>
                    </header>

                    <div className="flex justify-between mb-8">
                        <div>
                            <FilteringComponent
                                filterOptions={filterOptions}
                                onFilterChanged={handleOnFilterChanged}
                                onSortChanged={handleSortChanged}
                                isFilterOptionDisplayed={isFilterOptionDisplayed}
                            />
                        </div>
                        <div>
                            <SearchComponent
                                onSearchText={handleOnProductSearchChanged}/>
                        </div>
                    </div>

                    {totalNumberProducts === 0 ? (<NoEntityMessageComponent
                        header="Products do not exist yet."
                        paragraph="Sorry, we could not find the products you are looking for."/>) : (
                        <ul className="mt-2 grid gap-16 sm:grid-cols-1 md:grid-cols-3 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-4 w-full ">
                            {products.map((product) => (
                                <ProductComponent
                                    key={product.id}
                                    name={product.name}
                                    imageName={product.imageName}
                                    price={product.price}
                                    categoryName={product.category.name}
                                    sellerAlias={product.seller.alias}
                                />
                            ))}
                        </ul>
                    )}
                </div>
            </section>

        </div>
    );
}

export default ProductPageComponent;
