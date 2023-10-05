
import React, {useEffect} from 'react';
import '../../../styles/SelectComponent.css';
import useBreakpoint from "../../../hooks/useBreakpoint";

const SelectionOfNumberPerPage = ({itemsPerPage, setItemsPerPage, handleItemsPerPageChange}) => {

    const breakpoint = useBreakpoint();

    useEffect(() => {
        setItemsPerPage(12);
    }, [breakpoint]);


    return (
        <div className="items-center mb-4">
            <select value={itemsPerPage} onChange={handleItemsPerPageChange}
                    className="text-zinc-800 border cursor-pointer rounded-lg dark:bg-[#1a2747] dark:text-white border-indigo-900 dark:border-zinc-600 focus:border-indigo-900">
                {(breakpoint !== '2xl' && breakpoint !== 'xl') && (
                    <option value="6">6 per page</option>
                )}
                {breakpoint !== 'lg' && (
                    <option value="8">8 per page</option>
                )}
                <option value="12">12 per page</option>
            </select>
        </div>
    )
}

export default SelectionOfNumberPerPage;