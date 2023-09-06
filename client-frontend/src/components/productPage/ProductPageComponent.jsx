import React from 'react';
import {useParams} from "react-router-dom";
import ProductComponent from "./ProductComponent";

function ProductCollection() {

    const { categoryName } = useParams();

    return (
        <section>
            <div className="mx-auto max-w-screen-xl px-4 py-8 sm:px-6 sm:py-12 md:px-8 lg:px-10 xl:px-12 2xl:px-14">
                <header>
                    <h2 className="text-4xl font-bold text-zinc-800 text-center dark:text-white">
                        Check the {categoryName}!
                    </h2>

                </header>

                <div className="mt-8 flex items-center justify-between">
                    <div className="block md:hidden lg:hidden xl:hidden 2xl:hidden">
                        <button
                            className="flex cursor-pointer items-center gap-2 border-b border-gray-400 pb-1 text-zinc-800 transition hover:border-gray-600 dark:text-white"
                        >
                            <span className="text-sm font-medium"> Filters & Sorting </span>

                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                fill="none"
                                viewBox="0 0 24 24"
                                strokeWidth="1.5"
                                stroke="currentColor"
                                className="h-4 w-4 rtl:rotate-180"
                            >
                                <path
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                    d="M8.25 4.5l7.5 7.5-7.5 7.5"
                                />
                            </svg>
                        </button>
                    </div>

                    <div className="sm:hidden flex gap-4">
                        <div className="relative">
                            <details className="group [&_summary::-webkit-details-marker]:hidden">
                                <summary
                                    className="flex cursor-pointer items-center gap-2 border-b border-zinc-300 pb-1 text-zinc-800 transition hover:border-zinc-600 dark:text-white"
                                >
                                    <span className="text-sm font-medium"> Availability </span>

                                    <span className="transition group-open:-rotate-180">
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    strokeWidth="1.5"
                    stroke="currentColor"
                    className="h-4 w-4"
                >
                  <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      d="M19.5 8.25l-7.5 7.5-7.5-7.5"
                  />
                </svg>
              </span>
                                </summary>

                                <div
                                    className="z-50 group-open:absolute group-open:top-auto group-open:mt-2 ltr:group-open:start-0"
                                >
                                    <div className="w-96 rounded border border-zinc-300 bg-white">
                                        <header className="flex items-center justify-between p-4">
                                            <span className="text-sm text-zinc-800"> 0 Selected </span>

                                            <button
                                                type="button"
                                                className="text-sm text-zinc-800 underline underline-offset-4"
                                            >
                                                Reset
                                            </button>
                                        </header>

                                        <ul className="space-y-1 border-t border-zinc-200 p-4">
                                            <li>
                                                <label
                                                    htmlFor="FilterInStock"
                                                    className="inline-flex items-center gap-2"
                                                >
                                                    <input
                                                        type="checkbox"
                                                        id="FilterInStock"
                                                        className="h-5 w-5 rounded border-zinc-300"
                                                    />

                                                    <span className="text-sm font-medium text-zinc-800">
                        In Stock (5+)
                      </span>
                                                </label>
                                            </li>

                                            <li>
                                                <label
                                                    htmlFor="FilterPreOrder"
                                                    className="inline-flex items-center gap-2"
                                                >
                                                    <input
                                                        type="checkbox"
                                                        id="FilterPreOrder"
                                                        className="h-5 w-5 rounded border-zinc-300"
                                                    />

                                                    <span className="text-sm font-medium text-zinc-800">
                        Pre Order (3+)
                      </span>
                                                </label>
                                            </li>

                                            <li>
                                                <label
                                                    htmlFor="FilterOutOfStock"
                                                    className="inline-flex items-center gap-2"
                                                >
                                                    <input
                                                        type="checkbox"
                                                        id="FilterOutOfStock"
                                                        className="h-5 w-5 rounded border-zinc-300"
                                                    />

                                                    <span className="text-sm font-medium text-zinc-800">
                        Out of Stock (10+)
                      </span>
                                                </label>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </details>
                        </div>

                        <div className="relative">
                            <details className="group [&_summary::-webkit-details-marker]:hidden">
                                <summary
                                    className="flex cursor-pointer items-center gap-2 border-b border-zinc-300 pb-1 text-zinc-800 transition hover:border-zinc-600 dark:text-white"
                                >
                                    <span className="text-sm font-medium"> Price </span>

                                    <span className="transition group-open:-rotate-180">
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    strokeWidth="1.5"
                    stroke="currentColor"
                    className="h-4 w-4"
                >
                  <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      d="M19.5 8.25l-7.5 7.5-7.5-7.5"
                  />
                </svg>
              </span>
                                </summary>

                                <div
                                    className="z-50 group-open:absolute group-open:top-auto group-open:mt-2 ltr:group-open:start-0"
                                >
                                    <div className="w-96 rounded border border-zinc-300 bg-white">
                                        <header className="flex items-center justify-between p-4">
                  <span className="text-sm text-zinc-800">
                    The highest price is $600
                  </span>

                                            <button
                                                type="button"
                                                className="text-sm text-zinc-800 underline underline-offset-4"
                                            >
                                                Reset
                                            </button>
                                        </header>

                                        <div className="border-t border-zinc-200 p-4">
                                            <div className="flex justify-between gap-4">
                                                <label
                                                    htmlFor="FilterPriceFrom"
                                                    className="flex items-center gap-2"
                                                >
                                                    <span className="text-sm text-zinc-800">$</span>

                                                    <input
                                                        type="number"
                                                        id="FilterPriceFrom"
                                                        placeholder="From"
                                                        className="w-full rounded-md border-zinc-200 shadow-sm sm:text-sm"
                                                    />
                                                </label>

                                                <label htmlFor="FilterPriceTo" className="flex items-center gap-2">
                                                    <span className="text-sm text-zinc-800">$</span>

                                                    <input
                                                        type="number"
                                                        id="FilterPriceTo"
                                                        placeholder="To"
                                                        className="w-full rounded-md border-zinc-200 shadow-sm sm:text-sm"
                                                    />
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </details>
                        </div>
                    </div>

                    <div className="sm:block">
                        <label htmlFor="SortBy" className="sr-only">SortBy</label>

                        <select id="SortBy" className="h-10 rounded border-zinc-300 text-xl dark:text-zinc-800">
                            <option>Sort By</option>
                            <option value="Title, DESC">Title, DESC</option>
                            <option value="Title, ASC">Title, ASC</option>
                            <option value="Price, DESC">Price, DESC</option>
                            <option value="Price, ASC">Price, ASC</option>
                        </select>
                    </div>
                </div>

                <ul className="mt-4 grid gap-4 sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-2 xl:grid-cols-4 2xl:grid-cols-4">
                    <ProductComponent />
                    <ProductComponent />
                    <ProductComponent />
                    <ProductComponent />

                </ul>
            </div>
        </section>
    );
}

export default ProductCollection;
