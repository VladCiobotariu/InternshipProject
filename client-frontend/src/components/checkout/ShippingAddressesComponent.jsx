import { useState } from 'react'
import { RadioGroup } from '@headlessui/react'

const plans = [
    { id: 1, name: 'Startup' },
    { id: 2, name: 'Business' },
    { id: 3, name: 'Enterprise' },
]

const ShippingAddressesComponent = () => {
    const [plan, setPlan] = useState(plans[0])

    return (
        <>
            <RadioGroup value={plan} onChange={setPlan}>
                <RadioGroup.Label className="text-xl font-bold">Addresses</RadioGroup.Label>
                {plans.map((plan) => (
                    <RadioGroup.Option key={plan.id} value={plan} className="mt-4">
                        {({ checked }) => (
                            <div
                                className={` ${!!checked ? 'border-blue-500 ring-1 ring-blue-500': 'dark:border-none border-gray-100 hover:border-gray-200'} border flex items-center justify-between rounded-lg bg-white dark:bg-[#192235] p-4 text-sm font-medium shadow-sm`}
                            >
                                <div className="flex items-center gap-2">

                                    {!!checked &&
                                        <svg
                                            className="h-5 w-5 text-blue-600"
                                            xmlns="http://www.w3.org/2000/svg"
                                            viewBox="0 0 20 20"
                                            fill="currentColor"
                                        >
                                            <path
                                                fillRule="evenodd"
                                                d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                                                clipRule="evenodd"
                                            />
                                        </svg>
                                    }

                                    <p className="">{plan.name}</p>
                                </div>

                                <button onClick={()=>console.log("pressed")} className="">Free</button>
                            </div>
                        )}
                    </RadioGroup.Option>
                ))}
            </RadioGroup>
        </>
    )
}

export default ShippingAddressesComponent