import {PencilSquareIcon} from "@heroicons/react/24/outline";

function AddressComponent({item, checked, toggleModal}){
    return(
        <div
            className={` ${!!checked ? 'border-blue-500 ring-1 ring-blue-500': 'dark:border-none border-gray-100 hover:border-gray-200'} border flex items-center justify-between rounded-2xl bg-white dark:bg-[#192235] p-4 text-sm font-medium shadow-sm`}
        >
            <div className="flex items-center gap-2">

                <svg viewBox="0 0 24 24" className="h-5 w-5" xmlns="http://www.w3.org/2000/svg">
                    <g fill="#2563EB">
                        <path d="m12 2a10 10 0 1 0 10 10 10 10 0 0 0 -10-10zm0 18a8 8 0 1 1 8-8 8 8 0 0 1 -8 8z"/>
                        {!!checked &&
                            <path d="m12 7a5 5 0 1 0 5 5 5 5 0 0 0 -5-5z"/>
                        }
                    </g>
                </svg>

                <div className="flex flex-col ml-4">
                    <p className="">{item.address.city}</p>
                    <p className="">{item.address.addressLine1}</p>
                    <p className="">{item.address.addressLine2}</p>
                    <p className="">{item.firstName} {item.lastName} {item.telephone}</p>
                </div>
            </div>

            <button onClick={()=>toggleModal(item)} className="">
                <PencilSquareIcon className="w-6 h-6"/>
            </button>
        </div>
    )
}

export default AddressComponent