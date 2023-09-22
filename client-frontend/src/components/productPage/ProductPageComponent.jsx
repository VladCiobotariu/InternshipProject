import React, {useEffect, useState} from 'react';
import {useLocation, useNavigate} from "react-router-dom";
import ProductComponent from "./ProductComponent";
import {getProductsApi} from "../../security/api/ProductApi";
import FilteringComponent from "../filter/FilteringComponent";
import NoEntityMessageComponent from "../nonExistingEntities/NoEntityMessageComponent";
import ProductAddToCartModal from "./ProductAddToCartModal";


const buildFilterOptionsFromQueryParams = (queryParams) => {
    return {
        priceFrom: queryParams.get('priceFrom') ? parseInt(queryParams.get('priceFrom')) : null,
        priceTo: queryParams.get('priceTo') ? parseInt(queryParams.get('priceTo')) : null,
        categoryName: queryParams.get('categoryName') ? queryParams.getAll('categoryName') : [],
        cityName: queryParams.get('cityName') ? queryParams.getAll('cityName') : [],
        productName: queryParams.get('productName') ? queryParams.get('productName') : null
    };
}

function ProductPageComponent() {

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [productName, setProductName] = useState(null);

    const toggleModal = (productName) => {;
        setIsModalOpen(!isModalOpen);
        setProductName(productName);
    }

    const [products, setProducts] = useState([]);
    const [itemsPerPage, setItemsPerPage] = useState(8);
    const [totalNumberProducts, setTotalNumberProducts] = useState(0);
    const [currentPage, setCurrentPage] = useState(1);

    const [productSort, setProductSort] = useState({criteria: null, orderSort: null});

    const location = useLocation()
    const navigate = useNavigate();
    const [queryParams, setQueryParams] = useState(new URLSearchParams(location.search));

    const [filterOptions, setFilterOptions] = useState(buildFilterOptionsFromQueryParams(new URLSearchParams(location.search)));

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
        getProducts(itemsPerPage, currentPage, sortSpecs, filterSpecs);
    }, [filterOptions, productSort, currentPage, itemsPerPage]);

    useEffect(() => {
        navigate({ search: queryParams.toString() });
    }, [queryParams]);

    useEffect(() => {
        setFilterOptions(buildFilterOptionsFromQueryParams(new URLSearchParams(location.search)));
    }, [location.search]);


    const handleItemsPerPageChange = (event) => {
        const newItemsPerPage = parseInt(event.target.value);
        setCurrentPage(1);
        getProducts(newItemsPerPage, 1);
    }

    const handleSortChanged = (sortFilter) => {
        setProductSort(sortFilter);
    }
    const handleOnFilterChanged = (newFilterOptions) => {
        setFilterOptions(newFilterOptions);
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
        if(filterOptions.productName) {
            filterSearchSpec.push(createFilterCriteria("productName", "like", filterOptions.productName));
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
        const newQueryParams = new URLSearchParams();

        for (let key in filterOptions) {
            const value = filterOptions[key];
            if (value !== null && value !== '' && (Array.isArray(value) ? value.length > 0 : true)) {
                setSearchQueryParameters(key, value, newQueryParams);
            } else {
                newQueryParams.delete(key);
            }
        }
        setQueryParams(newQueryParams);
    }


    const setSearchQueryParameters = (key, value, newQueryParams) => {
        if (Array.isArray(value)) {
            for(let val in value) {
                newQueryParams.append(key, value[val]);
            }
        } else {
            newQueryParams.set(key, value);
        }
    }


    return (
        <div className="">
            <section>
                <div className="mx-auto mt-16 max-w-7xl px-10">
                    <header>
                        <h2 className="text-3xl mb-10 font-bold text-zinc-800 dark:text-white">
                            Check the products
                        </h2>
                    </header>

                    <div className="mb-8">
                        <div>
                            <FilteringComponent
                                filterOptions={filterOptions}
                                onFilterChanged={handleOnFilterChanged}
                                onSortChanged={handleSortChanged}
                            />
                        </div>

                    </div>

                    {totalNumberProducts === 0 ? (<NoEntityMessageComponent
                        header="Products do not exist yet."
                        paragraph="Sorry, we could not find the products you are looking for."/>) : (
                        <ul className="mt-2 grid gap-16 sm:grid-cols-1 md:grid-cols-3 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-4 w-full ">
                            {products.map((product) => (
                                <div key={product.id}>
                                    <ProductComponent
                                        key={product.id}
                                        name={product.name}
                                        imageName={product.imageName}
                                        price={product.price}
                                        toggleModal={() => toggleModal(product.name)}
                                    />
                                </div>
                            ))}
                        </ul>
                    )}
                </div>

            </section>

            <ProductAddToCartModal
                toggleModal={toggleModal}
                isModalOpen={isModalOpen}
                setIsModalOpen={setIsModalOpen}
                productName={productName}
            />

        </div>
    );
}

export default ProductPageComponent;
