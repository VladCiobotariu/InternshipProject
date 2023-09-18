import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import ProductComponent from "./ProductComponent";
import {getProductsApi} from "../../security/api/ProductApi";
import SearchComponent from '../search/SearchComponent';
import FilteringComponent from "../filter/FilteringComponent";
import NoEntityMessageComponent from "../nonExistingEntities/NoEntityMessageComponent";

// has values
class FilterOptions {
    priceFrom;
    priceTo;
    categoryName;
    cityName;
}

function ProductPageComponent() {

    const { categoryName } = useParams();

    const [displayedProducts, setDisplayedProducts] = useState([]);
    const [itemsPerPage, setItemsPerPage] = useState(8);
    const [totalNumberProducts, setTotalNumberProducts] = useState(0);
    const [currentPage, setCurrentPage] = useState(1);

    const [productSearchFilter, setProductSearchFilter] = useState(null);
    const [productCategoryFilter, setProductCategoryFilter] = useState(categoryName || null);
    const [productSort, setProductSort] = useState({criteria: '', orderSort: ''});

    const [filterOptions, setFilterOptions] = useState({});


    const getProducts = (newItemsPerPage, page, sortSpecs, filterSpecs) => {
        setItemsPerPage(newItemsPerPage);
        getProductsApi(page, newItemsPerPage, sortSpecs, filterSpecs)
            .then((res) => {
                setDisplayedProducts(res.data.data);
                setTotalNumberProducts(res.data.numberOfElements);
            })
            .catch((err) => console.log(err))
    }

    useEffect(() => {
        setProductCategoryFilter(categoryName);
        const filterSpecs = buildFilterSpecs();
        const sortSpecs = buildSortSpecs();
        getProducts(itemsPerPage, currentPage, sortSpecs, filterSpecs);
    }, [productSearchFilter, productCategoryFilter, currentPage, itemsPerPage, categoryName, filterOptions]);

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

    const createFilterCriteria = (criteria, operation, value) => {
        return `${criteria}[${operation}]${value}`;
    }

    const createSortCriteria = (criteria, orderSort) => {
        return `${criteria}-${orderSort}`;
    }

    const buildFilterSpecs = () => {
        const filterSearchSpec = [];
        if(productSearchFilter) {
            filterSearchSpec.push(createFilterCriteria("productName", "like", productSearchFilter));
        }
        if(productCategoryFilter) {
            filterSearchSpec.push(createFilterCriteria("categoryName", "eq", productCategoryFilter));
        }
        if(filterOptions.cityName) {
            filterSearchSpec.push(createFilterCriteria("categoryName", "eq", filterOptions.cityName));
        }
        if(filterOptions.priceFrom) {
            filterSearchSpec.push(createFilterCriteria("priceFrom", "gte", filterOptions.priceFrom));
        }
        if(filterOptions.priceTo) {
            filterSearchSpec.push(createFilterCriteria("priceTo", "lte", filterOptions.priceTo));
        }
        return filterSearchSpec;
    }

    const buildSortSpecs = () => {
        const sortSpecs = [];
        if(productSort.criteria && productSort.orderSort) {
            sortSpecs.push(createSortCriteria(productSort.criteria, productSort.orderSort));
        }
        return sortSpecs;
    }

    const handleOnFilterChanged = (newFilterOptions) => {
        setFilterOptions(newFilterOptions);
    }

    return (
        <div>
            {totalNumberProducts === 0 ? (<NoEntityMessageComponent
                header="Products do not exist yet."
                paragraph="Sorry, we could not find the products you are looking for."/>) :
            (
                <section>
                    <div className="mx-auto mt-16 max-w-7xl px-10">
                        <header>
                            <h2 className="text-3xl mb-10 font-bold text-zinc-800 dark:text-white">
                                { categoryName ? `Check the ${categoryName}!` : 'All Products' }
                            </h2>
                        </header>

                        <div className="flex justify-between mb-8">
                            <div>
                                <FilteringComponent
                                    filterOptions={filterOptions}
                                    onFilterChanged={handleOnFilterChanged}/>
                            </div>
                            <div>
                                <SearchComponent
                                    onSearchText={handleOnProductSearchChanged}/>
                            </div>
                        </div>



                        <ul className="mt-4 grid gap-16 sm:grid-cols-1 md:grid-cols-3 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-4 w-full ">
                            {displayedProducts.map((product) => (
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
                    </div>
                </section>
            )}
        </div>
    );
}

export default ProductPageComponent;
