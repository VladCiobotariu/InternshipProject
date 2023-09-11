import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import ProductComponent from "./ProductComponent";
import {getAllProductsByCategoryNamePageable} from "../../security/api/ProductApi";
import NoProductMessageComponent from "./NoProductMessageComponent";
import SearchComponent from '../search/SearchComponent';
import FilteringComponent from "../filter/FilteringComponent";

function ProductCollection() {

    const { categoryName } = useParams();

    const [displayedProducts, setDisplayedProducts] = useState([]);
    const [copyDisplayedProducts, setCopyDisplayedProducts] = useState([]);
    const [dataForSearch, setDataForSearch] = useState([]);
    const [itemsPerPage, setItemsPerPage] = useState(8);
    const [totalNumberProducts, setTotalNumberProducts] = useState(0);
    const [currentPage, setCurrentPage] = useState(1);


    const getProductsWithItemsPerPage = (newItemsPerPage, page) => {
        setItemsPerPage(newItemsPerPage);

        getAllProductsByCategoryNamePageable(categoryName, page, newItemsPerPage)
            .then((res) => {
                setDisplayedProducts(res.data.data);
                setCopyDisplayedProducts(res.data.data);
                setTotalNumberProducts(res.data.numberOfElements);
            })
            .catch((err) => console.log(err))
    }

    useEffect(() => {
        getProductsWithItemsPerPage(itemsPerPage, currentPage);

    }, [currentPage, itemsPerPage, categoryName]);

    const handleDataForSearch = (displayedProducts) => {
        const names = displayedProducts.map(prod => prod.name);
        setDataForSearch(names);
    }

    useEffect(() => {
        handleDataForSearch(displayedProducts);
    }, [displayedProducts]);

    const handleItemsPerPageChange = (event) => {
        const newItemsPerPage = parseInt(event.target.value);
        setCurrentPage(1);
        getProductsWithItemsPerPage(newItemsPerPage, 1);
    }

    const handleDisplayedChange = (e) => {
        const field = e.target.value;
        if(field === "") {
            setDisplayedProducts(copyDisplayedProducts);
        } else {
            let filteredSearch = [];
            filteredSearch = displayedProducts.filter((prod) => {
                return prod.name.toLowerCase().includes(field.toLowerCase());
            });
            setDisplayedProducts(filteredSearch);
        }
    }


    return (
        <div>

            {totalNumberProducts === 0 ? (<NoProductMessageComponent />) :
            (
                <section>
                    <div className="mx-auto mt-16 max-w-7xl px-10">
                        <header>
                            <h2 className="text-3xl mb-10 font-bold text-zinc-800 dark:text-white">
                                Check the {categoryName}!
                            </h2>

                        </header>

                        <div className="flex justify-between mb-14">
                            <div>
                                <FilteringComponent />
                            </div>
                            <div>
                                <SearchComponent data={dataForSearch}
                                handleDisplayedChange={(e) => handleDisplayedChange(e)}/>
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

export default ProductCollection;
