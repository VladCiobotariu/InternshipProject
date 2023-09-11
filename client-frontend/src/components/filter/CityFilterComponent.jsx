import React from 'react'

const CityFilterComponent = ({onClickInside}) =>{

    return (
        <div onClick={onClickInside}>
            <div className="w-56 rounded border border-zinc-300 bg-white">
                <div className="px-2 pt-2 pb-2 bg-white rounded-md shadow-lg dark-mode:bg-gray-700">
                    City comp
                </div>
            </div>
        </div>
    );
}

export default CityFilterComponent;