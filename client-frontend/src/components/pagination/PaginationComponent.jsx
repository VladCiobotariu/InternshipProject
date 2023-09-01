import React from 'react'
import Pagination from '@mui/material/Pagination';
import Stack from '@mui/material/Stack';

const PaginationComponent = ({ totalPages, handlePageChange, currentPage}) => {

    return (
        <div className="flex justify-center pb-5">
            <Stack spacing={2}>
                <Pagination
                    count={totalPages}
                    page={currentPage}
                    onChange={handlePageChange}
                    variant="outlined"
                />
            </Stack>
        </div>
    )
}

export default PaginationComponent;