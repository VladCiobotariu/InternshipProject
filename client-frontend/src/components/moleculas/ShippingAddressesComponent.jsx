import {RadioGroup} from '@headlessui/react'
import AddressComponent from "./AddressComponent";


const ShippingAddressesComponent = ({shippingAddresses, selectedShippingAddress, onAddressSelected}) => {

    return (
        <>
            <RadioGroup value={selectedShippingAddress} onChange={onAddressSelected}>
                <RadioGroup.Label className="text-xl font-bold">Addresses</RadioGroup.Label>
                {shippingAddresses.map((item) => (
                    <RadioGroup.Option key={item.id} value={item} className="mt-4">
                        {({ checked }) => (
                            <AddressComponent item={item} checked={checked} editFunction={()=>console.log("pressed")}/>
                        )}
                    </RadioGroup.Option>
                ))}
            </RadioGroup>
        </>
    )
}

export default ShippingAddressesComponent