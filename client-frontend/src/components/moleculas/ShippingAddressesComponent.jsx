import {RadioGroup} from '@headlessui/react'
import AddressComponent from "./AddressComponent";
import ButtonSmallWithIcon from "../atoms/button/ButtonSmallWithIcon";

const ShippingAddressesComponent = ({shippingAddresses, selectedShippingAddress, onAddressSelected, toggleModal, onAddAddress}) => {

    return (
        <>
            <RadioGroup value={selectedShippingAddress} onChange={onAddressSelected}>
                <RadioGroup.Label className="flex justify-between items-end">
                    <div className="text-xl font-bold">
                        Addresses
                    </div>

                    <ButtonSmallWithIcon text="Add Address" onClick={onAddAddress}>
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-6 h-6">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M12 6v12m6-6H6" />
                        </svg>
                    </ButtonSmallWithIcon>

                </RadioGroup.Label>
                {shippingAddresses.map((item) => (
                    <RadioGroup.Option key={item.id} value={item} className="mt-6">
                        {({ checked }) => (
                            <AddressComponent item={item} checked={checked} toggleModal={toggleModal}/>
                        )}
                    </RadioGroup.Option>
                ))}
            </RadioGroup>
        </>
    )
}

export default ShippingAddressesComponent