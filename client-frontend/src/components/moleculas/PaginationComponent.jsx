import React from 'react'
import usePagination, {DOTS} from "../../hooks/usePagination";
import classnames from 'classnames';
import "../../styles/pagination.css";

const PaginationComponent = ({ handlePageChange, totalCount, siblingCount=1, currentPage, itemsPerPage, className}) => {

    const paginationRange = usePagination({
        currentPage,
        totalCount,
        siblingCount,
        itemsPerPage
    });

    if (currentPage === 0 || paginationRange.length < 2) {
        return null;
    }

    const onNext = () => {
        handlePageChange(currentPage + 1);
    };

    const onPrevious = () => {
        handlePageChange(currentPage - 1);
    };

    let lastPage = paginationRange[paginationRange.length - 1];

    return (
        <ul
            className={classnames("pagination-container", { [className]: className })}
        >
            <li
                className={classnames("pagination-item", {
                    disabled: currentPage === 1
                })}
                onClick={onPrevious}
            >
                <div>{'<'}</div>
            </li>
            {paginationRange.map((pageNumber) => {
                if (pageNumber === DOTS) {
                    return <li className="pagination-item dots">&#8230;</li>;
                }

                return (
                    <li
                        className={classnames("pagination-item", {
                            selected: pageNumber === currentPage
                        })}
                        onClick={() => handlePageChange(pageNumber)}
                    >
                        {pageNumber}
                    </li>
                );
            })}
            <li
                className={classnames("pagination-item", {
                    disabled: currentPage === lastPage
                })}
                onClick={onNext}
            >
                <div>{'>'}</div>
            </li>
        </ul>

    )
}

export default PaginationComponent;