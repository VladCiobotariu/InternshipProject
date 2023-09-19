import React, {useState} from 'react';

const CityFilterComponent = ({onClickInside, toggleCityFilter, handleCityChanged, getCityNames}) => {

    const cityList = ["Timisoara", "Cluj-Napoca", "Baia Mare", "Bucuresti", "Iasi", "Arad"];

    const [checkedCities, setCheckedCities] = useState(getCityNames || []);

    console.log(checkedCities)

    const handleCheck = (e) => {
        const city = e.target.value;
        let updatedList = [...checkedCities];
        if(e.target.checked && !checkedCities.i) {
            updatedList = [...checkedCities, city];
        } else {
            updatedList = updatedList.filter((item) => item !== city);
        }
        setCheckedCities(updatedList);
    }

    const handleClickOnSave = () => {
        handleCityChanged("cityName", checkedCities);
        toggleCityFilter();
    }

    return (
        <div onClick={onClickInside}>
            <div className="w-56 rounded border border-zinc-300 bg-white">
                <div className="px-2 pt-2 pb-2 bg-white rounded-md shadow-lg dark-mode:bg-gray-700">
                    <div className="checkList">
                        <div className="list-container flex flex-col gap-2">
                            {cityList.map((item, index) => (
                                <div key={index}>
                                    <input value={item}
                                           type="checkbox"
                                           checked={checkedCities.includes(item)}
                                           onChange={handleCheck}/>
                                    <span className="ml-5">{item}</span>
                                </div>
                                ))}
                        </div>

                    </div>
                    <button type="button"
                            className="focus:outline-none text-white bg-purple-700 hover:bg-purple-800 focus:ring-4 focus:ring-purple-300 font-medium rounded-lg text-sm px-5 py-2.5 mb-2 dark:bg-purple-600 dark:hover:bg-purple-700 dark:focus:ring-purple-900"
                            onClick={handleClickOnSave}>
                        Save
                    </button>
                </div>
            </div>
        </div>
    );
}

export default CityFilterComponent;