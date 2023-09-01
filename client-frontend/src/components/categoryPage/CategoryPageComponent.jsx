import React, {useEffect, useState} from 'react'
import CategoryComponent from "./CategoryComponent";
import {getAllCategoriesApi} from "../../security/api/CategoryApi";
import PaginationComponent from "../pagination/PaginationComponent";
import {usePaginationContext} from "../context/PaginationContext";
import NoCategoryMessageComponent from "./NoCategoryMessageComponent";
import './CategoryPageComponent.css';
const CategoryPageComponent = () => {

    const paginationContext = usePaginationContext();
    const itemsPerPage = paginationContext.itemsPerPage;
    const handlePageChange = paginationContext.handlePageChange;
    const currentPage = paginationContext.currentPage;

    const [categories, setCategories] = useState([]);

    const getCategoryList = () => {
        getAllCategoriesApi()
            .then((res) => {
                setCategories(res.data);
            })
            .catch((err) => console.log(err));
    };

    useEffect(() => {
        getCategoryList();
    }, []);

    const lastIndex = currentPage * itemsPerPage;
    const firstIndex = lastIndex - itemsPerPage;
    const currentCategories = categories.slice(firstIndex, lastIndex);
    const totalPages = Math.ceil(categories.length / itemsPerPage);
    const numberOfCategories = categories.length;

    return (
        <div>
            {numberOfCategories === 0 ? (<NoCategoryMessageComponent />) :
                (
                    <div className="bg-gray-100">
                        <div className="justify-center">
                            <div className="mx-auto max-w-2xl py-16 sm:py-24 md:py-24 lg:py-32">
                                <h2 className="text-center text-2xl font-bold text-zinc-800">Check the categories!</h2>

                                <div className="mt-6 category-grid">
                                    {currentCategories.map((category) => (
                                        <CategoryComponent
                                            key={category.id}
                                            categoryName={category.name}
                                            imageUrl={category.imageName}
                                        />
                                    ))}
                                </div>

                            </div>
                        </div>

                        <PaginationComponent totalPages={totalPages}
                                             handlePageChange={handlePageChange}
                                             currentPage={currentPage}/>
                    </div>
                )}
        </div>
    )
}

export default CategoryPageComponent
