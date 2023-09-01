import React from 'react';
import { baseURL } from '../../security/ApiClient';
import './CategoryPageComponent.css'

const CategoryComponent = ({ categoryName, imageUrl }) => {
    return (
        <div className="category-component group relative items-center justify-center p-4">
            <div className="relative overflow-hidden rounded-xl bg-white group-hover:opacity-75 border" style={{ paddingBottom: '100%' }}>
                <img
                    src={`${baseURL}${imageUrl}`}
                    alt={categoryName}
                    className="absolute inset-0 h-full w-full object-cover object-center bg-zinc-200 dark:bg-zinc-200"
                />
            </div>
            <h3 className="text-center mt-6 text-lg font-bold text-zinc-800">
                <a href={`/products/categories/${categoryName.toLowerCase().replace(/\s+/g, '-')}`}>
                    <span className="absolute inset-0" />
                    {categoryName}
                </a>
            </h3>
        </div>
    );
};

export default CategoryComponent;
