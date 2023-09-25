import React, {useState} from 'react';

const RangeFilterComponent = ({onClickInside, toggleRangeFilter, handleRangeChanged, labelFrom, labelTo, getRangeFrom, getRangeTo }) => {

    const [rangeFrom, setRangeFrom] = useState(getRangeFrom || '');
    const [rangeTo, setRangeTo] = useState(getRangeTo || '');

    const [displayErrorMessage, setDisplayErrorMessage] = useState(false);

    const onRangeFromChanged = (e) => {
        setRangeFrom(e.target.value);
    }

    const onRangeToChanged = (e) => {
        setRangeTo(e.target.value);
    }

    const handleSaveRanges = () => {
        if(rangeFrom && rangeTo) {
            if (parseFloat(rangeFrom) <= parseFloat(rangeTo)) {
                handleRangeChanged(rangeFrom, rangeTo);
                toggleRangeFilter();
                setDisplayErrorMessage(false);
            } else {
                setDisplayErrorMessage(true);
            }
        } else if(parseFloat(rangeFrom) || parseFloat(rangeTo)) {
            handleRangeChanged(rangeFrom, rangeTo);
            toggleRangeFilter();
            setDisplayErrorMessage(false);
        }
        else {
            setDisplayErrorMessage(true);
        }
    }

    // todo - add borders to buttons

    return (
        <div onClick={onClickInside}>
            <div className="w-80 bg-white">
                <div className="px-10 py-4 bg-white rounded-2xl border border-zinc-300 shadow-lg dark-mode:bg-zinc-700 flex-col">

                    <div className="flex justify-between items-center gap-10">
                        <div>
                            <label htmlFor={labelFrom}
                                   className="block mb-2 text-sm font-md text-zinc-900">
                                {labelFrom}
                            </label>
                            <input type="text" id={labelFrom}
                                   className="bg-zinc-50 border border-zinc-300 text-zinc-900 text-sm rounded-lg focus:ring-indigo-600 focus:border-indigo-600 block w-full dark:bg-zinc-200 dark:border-zinc-300 dark:placeholder-zinc-400 dark:focus:ring-indigo-600 dark:focus:border-indigo-600"
                                   placeholder="from.."
                                   value={rangeFrom}
                                   onChange={onRangeFromChanged}
                            />
                        </div>
                        <div>
                            <label htmlFor={labelTo}
                                   className="block mb-2 text-sm font-md text-zinc-900">
                                {labelTo}
                            </label>
                            <input type="text" id={labelTo}
                                   className="bg-zinc-50 border border-zinc-300 text-zinc-900 text-sm rounded-lg focus:ring-indigo-600 focus:border-indigo-600 block w-full dark:bg-zinc-200 dark:border-zinc-300 dark:placeholder-zinc-400 dark:focus:ring-indigo-600 dark:focus:border-indigo-600"
                                   placeholder="to.."
                                   value={rangeTo}
                                   onChange={onRangeToChanged}
                            />
                        </div>
                    </div>
                    {displayErrorMessage &&
                        <div
                            className="p-1 mt-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400"
                            role="alert">
                            <span className="text-xs">Please enter the correct prices!</span>
                        </div>
                    }
                    <div className="flex justify-start">
                        <button type="button"
                                className="text-white bg-indigo-600 hover:bg-indigo-700 rounded-lg  px-3 py-1.5 mt-2"
                                onClick={handleSaveRanges}>
                            Save
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default RangeFilterComponent;
