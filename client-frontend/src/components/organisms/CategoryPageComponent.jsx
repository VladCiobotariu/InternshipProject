import React, {useEffect, useState} from 'react'
import CategoryComponent from "../moleculas/CategoryComponent";
import '../../styles/CategoryPageComponent.css';
import NoEntityMessageComponent from "../atoms/error/NoEntityMessageComponent";
import {getAllCategoriesApi} from "../../api/CategoryApi";

const CategoryPageComponent = () => {

    const [categories, setCategories] = useState([]);
    const [totalNumberCategories, setTotalNumberCategories] = useState(0);
    const numberColumns = 4;

    const getCategoryList = () => {
        getAllCategoriesApi()
            .then((res) => {
                console.log(res)
                setCategories(res.data.data);
                setTotalNumberCategories(res.data.numberOfElements);
            })
            .catch((err) => console.log(err));
    };

    useEffect(() => {
        getCategoryList();
    }, []);


    return (
        <div>
            {totalNumberCategories === 0 ? (<NoEntityMessageComponent
                                                header="Categories do not exist yet."
                                                paragraph="Sorry, we could not find the categories you are looking for."/>) :
                (
                    <div className="bg-gray-100">
                        <div className="justify-center">
                            <div className="mx-auto max-w-2xl py-16 sm:py-24 md:py-24 lg:py-32">
                                <h2 className="text-center text-2xl font-bold text-zinc-800">Check the categories!</h2>

                                <div className="mt-6 category-grid" style={{gridTemplateColumns: `repeat(${numberColumns}, 1fr)`}}>
                                    {categories.map((category) => (
                                        <CategoryComponent
                                            key={category.id}
                                            categoryName={category.name}
                                            imageUrl={category.imageName}
                                        />
                                    ))}
                                </div>

                            </div>
                        </div>

                    </div>

                )}
        </div>
    )
}

export default CategoryPageComponent
