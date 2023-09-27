import React, {useState} from 'react';

const CityFilterComponent = ({onClickInside, toggleFilter, list, handleListChanged, filterName, getElementsNames}) => {

    const [checkedElements, setCheckedElements] = useState(getElementsNames || []);

    const handleCheck = (e) => {
        const element = e.target.value;
        let updatedList = [...checkedElements];
        if(e.target.checked && !checkedElements.includes(element)) {
            updatedList = [...checkedElements, element];
        } else {
            updatedList = updatedList.filter((item) => item !== element);
        }
        setCheckedElements(updatedList);
    }

    const handleClickOnSave = () => {
        handleListChanged(filterName, checkedElements);
        toggleFilter();
    }

    return (
        <div onClick={onClickInside}>
            <div className="w-56 bg-white">
                <div className="px-10 py-4 bg-white rounded-2xl border border-zinc-300 shadow-lg">
                    <div className="checkList">
                        <div className="list-container flex flex-col gap-2">
                            {list.map((item, index) => (
                                <div key={index}>
                                    <input value={item}
                                           className="rounded-2xl text-indigo-700 focus:ring-indigo-500 dark:focus:ring-indigo-600 focus:ring-2"
                                           type="checkbox"
                                           checked={checkedElements.includes(item)}
                                           onChange={handleCheck}/>
                                    <span className="ml-5 text-zinc-800">{item}</span>
                                </div>
                            ))}
                        </div>

                    </div>
                    <div className="flex justify-start">
                        <button type="button"
                                className="bg-indigo-600 hover:bg-indigo-700 text-white my-2 py-1 px-4 rounded-lg border border-indigo-800 transition duration-100 ease-in-out hover:scale-105"
                                onClick={handleClickOnSave}>
                            Save
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default CityFilterComponent;