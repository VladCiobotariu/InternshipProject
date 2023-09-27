import React, {useEffect, useState} from 'react';
import {useLocation, useNavigate} from "react-router-dom";
import ProductComponent from "../moleculas/ProductComponent";
import {getProductsApi} from "../../api/ProductApi";
import FilteringComponent from "../moleculas/filter/FilteringComponent";
import NoEntityMessageComponent from "../atoms/error/NoEntityMessageComponent";
import ProductAddToCartModal from "../moleculas/modals/ProductAddToCartModal";
import BaseModal from "../atoms/BaseModal";
import PaginationComponent from "../moleculas/PaginationComponent";
import SelectionOfNumberPerPage from "../atoms/input/SelectionOfNumberPerPage";


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

    const [products, setProducts] = useState([]);
    const [itemsPerPage, setItemsPerPage] = useState(12);
    const [totalNumberProducts, setTotalNumberProducts] = useState(0);
    const [currentPage, setCurrentPage] = useState(1);

    const [productSort, setProductSort] = useState({criteria: null, orderSort: null});

    const location = useLocation()
    const navigate = useNavigate();
    const [queryParams, setQueryParams] = useState(new URLSearchParams(location.search));

    const [filterOptions, setFilterOptions] = useState(buildFilterOptionsFromQueryParams(new URLSearchParams(location.search)));

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [productName, setProductName] = useState(null);

    const toggleModal = (productName) => {
        setIsModalOpen(!isModalOpen);
        setProductName(productName);
    }

    const getProducts = (page, newItemsPerPage, sortSpecs, filterSpecs) => {
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
        getProducts(currentPage, itemsPerPage, sortSpecs, filterSpecs);
    }, [filterOptions, productSort, itemsPerPage, currentPage]);

    useEffect(() => {
        navigate({search: queryParams.toString()});
    }, [queryParams]);

    useEffect(() => {
        setFilterOptions(buildFilterOptionsFromQueryParams(new URLSearchParams(location.search)));
    }, [location.search]);

    const handleItemsPerPageChange = (event) => {
        const newItemsPerPage = parseInt(event.target.value);
        const filterSpecs = buildFilterSpecs();
        const sortSpecs = buildSortSpecs();
        setCurrentPage(1);
        getProducts(1, newItemsPerPage, sortSpecs, filterSpecs);
    }

    const handleSortChanged = (sortFilter) => {
        setProductSort(sortFilter);
        setCurrentPage(1);
    }
    const handleOnFilterChanged = (newFilterOptions) => {
        setFilterOptions(newFilterOptions);
        setCurrentPage(1);
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
        if (filterOptions.cityName.length) {
            const value = createValueForFilterCriteria(filterOptions.cityName);
            filterSearchSpec.push(createFilterCriteria("cityName", "eq", value));
        }
        if (filterOptions.priceFrom) {
            filterSearchSpec.push(createFilterCriteria("priceFrom", "gte", filterOptions.priceFrom));
        }
        if (filterOptions.priceTo) {
            filterSearchSpec.push(createFilterCriteria("priceTo", "lte", filterOptions.priceTo));
        }
        if (filterOptions.productName) {
            filterSearchSpec.push(createFilterCriteria("productName", "starts_with", filterOptions.productName));
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
            for (let val in value) {
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
                        <div>

                            <ul className="mt-2 grid gap-16 sm:grid-cols-1 md:grid-cols-3 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-4 w-full ">
                                {products.map((product) => (
                                    <div key={product.id}>
                                        <ProductComponent
                                            key={product.id}
                                            id={product.id}
                                            name={product.name}
                                            imageName={product.imageName}
                                            price={product.price}
                                            toggleModal={() => toggleModal(product.name)}
                                        />
                                    </div>
                                ))}
                            </ul>

                            <div className="mt-10 flex items-center justify-between pb-5">
                                <div className="flex-grow flex justify-center ml-44">
                                    <PaginationComponent
                                        className="pagination-bar"
                                        currentPage={currentPage}
                                        totalCount={totalNumberProducts}
                                        itemsPerPage={itemsPerPage}
                                        handlePageChange={page => setCurrentPage(page)}
                                    />
                                </div>

                                <SelectionOfNumberPerPage
                                    itemsPerPage={itemsPerPage}
                                    handleItemsPerPageChange={handleItemsPerPageChange}
                                />
                            </div>

                        </div>
                    )}
                </div>

            </section>

            <BaseModal
                isModalOpen={isModalOpen}
                toggleModal={() => toggleModal(productName)}>

                <ProductAddToCartModal
                    toggleModal={toggleModal}
                    isModalOpen={isModalOpen}
                    setIsModalOpen={setIsModalOpen}
                    productName={productName}
                />
            </BaseModal>


        </div>
    );
}

export default ProductPageComponent;
