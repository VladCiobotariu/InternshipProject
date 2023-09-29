import BaseModal from "../../atoms/BaseModal";
import {useState} from "react";

function ShippingAddressModal({address}){

    const [isModalOpen, setIsModalOpen] = useState(true) //todo modify from parent

    return(
        <div>
            <BaseModal isModalOpen={isModalOpen} toggleModal={setIsModalOpen(false)}>

            </BaseModal>
        </div>
    )
}

export default ShippingAddressModal