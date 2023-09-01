import React, {createContext, useContext, useEffect, useState} from 'react'

const PaginationContext = createContext()
export const usePaginationContext = () => useContext(PaginationContext)

export const calculateItemsPerPage = () => {
    const screenWidth = window.innerWidth;
    if (screenWidth >= 1536) {
        return 6;
    } else if (screenWidth >= 1280) {
        return 6;
    } else if (screenWidth >= 1024) {
        return 6;
    } else if (screenWidth >= 768) {
        return 4;
    }
    return 2;
};
const PaginationProvider = ({ children }) => {

    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(calculateItemsPerPage())

    const handleResize = () => {
        setItemsPerPage(calculateItemsPerPage());
    }
    const handlePageChange = (event, page) => {
        setCurrentPage(page);
    };

    useEffect(() => {
        window.addEventListener('resize', handleResize)
        return _ => {
            window.removeEventListener('resize', handleResize)
        }
    }, []);

    return (
        <PaginationContext.Provider value = {{ currentPage, itemsPerPage, handlePageChange}}>
            {children}
        </PaginationContext.Provider>

    )
}

export default PaginationProvider;

