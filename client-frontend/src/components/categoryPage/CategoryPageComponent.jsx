import React, {useEffect, useState} from 'react'
import CategoryComponent from "./CategoryComponent";
import {getAllCategoriesApi, getAllCategoriesByItemsPerPageAndPage} from "../../security/api/CategoryApi";
import PaginationComponent from "../pagination/PaginationComponent";
import NoCategoryMessageComponent from "./NoCategoryMessageComponent";
import './CategoryPageComponent.css';
import SelectComponent from "../pagination/SelectComponent";

const CategoryPageComponent = () => {

    const [displayedCategories, setDisplayedCategories] = useState([]);
    const [itemsPerPage, setItemsPerPage] = useState(6);
    const [totalNumberCategories, setTotalNumberCategories] = useState(0);
    const [currentPage, setCurrentPage] = useState(1);
    const numberColumns = 2;

    // const getCategoryList = () => {
    //     getAllCategoriesApi()
    //         .then((res) => {
    //             setCategories(res.data.data);
    //         })
    //         .catch((err) => console.log(err));
    // };

    const getCategoriesWithItemsPerPage = (newItemsPerPage, page) => {
        setItemsPerPage(newItemsPerPage);

        getAllCategoriesByItemsPerPageAndPage(page, newItemsPerPage)
            .then((res) => {
                setDisplayedCategories(res.data.data);
                setTotalNumberCategories(res.data.numberOfElements);
            })
            .catch((err) => console.log(err));
    }

    useEffect(() => {
        // getCategoryList();
    }, []);

    useEffect( () => {
        getCategoriesWithItemsPerPage(itemsPerPage, currentPage);
    }, [currentPage, itemsPerPage])

    const handleItemsPerPageChange = (event) => {
        const newItemsPerPage = parseInt(event.target.value);
        setCurrentPage(1);
        getCategoriesWithItemsPerPage(newItemsPerPage, 1);
    };

    return (
        <div>

            {totalNumberCategories === 0 ? (<NoCategoryMessageComponent />) :
                (
                    <div className="bg-gray-100">
                        <div className="justify-center">
                            <div className="mx-auto max-w-2xl py-16 sm:py-24 md:py-24 lg:py-32">
                                <h2 className="text-center text-2xl font-bold text-zinc-800">Check the categories!</h2>

                                <div className="mt-6 category-grid" style={{gridTemplateColumns: `repeat(${numberColumns}, 1fr)`}}>
                                    {displayedCategories.map((category) => (
                                        <CategoryComponent
                                            key={category.id}
                                            categoryName={category.name}
                                            imageUrl={category.imageName}
                                        />
                                    ))}
                                </div>

                            </div>
                        </div>

                        <div className="mt-10 flex items-center justify-between pb-5">
                            <div className="flex-grow flex justify-center ml-44">
                                <PaginationComponent
                                    className="pagination-bar"
                                    currentPage={currentPage}
                                    totalCount={totalNumberCategories}
                                    itemsPerPage={itemsPerPage}
                                    handlePageChange={page => setCurrentPage(page)}
                                    />
                            </div>

                        <SelectComponent
                            itemsPerPage={itemsPerPage}
                            handleItemsPerPageChange={handleItemsPerPageChange}
                            />
                        </div>

                    </div>

                )}
        </div>
    )
}

export default CategoryPageComponent
