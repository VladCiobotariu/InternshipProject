import {Fragment, useRef, useState} from 'react'
import {Link} from 'react-router-dom'
import { useAuth } from '../../security/AuthContext'

import { Dialog, Disclosure, Popover, Transition } from '@headlessui/react'
import {
    Bars3Icon,
    XMarkIcon,
    UserCircleIcon,
    ClipboardDocumentListIcon,
    Cog6ToothIcon,
    ArrowLeftOnRectangleIcon,
    ShoppingBagIcon,
    HeartIcon,
} from '@heroicons/react/24/outline'
import { ChevronDownIcon } from '@heroicons/react/20/solid'

import {ReactComponent as TomatoSvg} from "./svg/tomato-svgrepo-com.svg";
import {ReactComponent as FruitSvg} from "./svg/fruits-pear-svgrepo-com.svg";
import {ReactComponent as MilkSvg} from "./svg/milk-bottle-svgrepo-com.svg";
import {ReactComponent as NutSvg} from "./svg/almond-svgrepo-com.svg";
import {ReactComponent as HoneySvg} from "./svg/honey-2-svgrepo-com.svg";


const products = [
    { name: 'Fruits', href: '/products/categories/fruits', icon: FruitSvg },
    { name: 'Vegetables', href: '/products/categories/vegetables', icon: TomatoSvg },
    { name: 'Dairy', href: '/products/categories/dairy', icon: MilkSvg },
    { name: 'Nuts', href: '/products/categories/nuts', icon: NutSvg },
    { name: 'Honey', href: '/products/categories/honey', icon: HoneySvg },
]

const accountData = [
    { name: 'Orders', href: '/account/orders', icon: ClipboardDocumentListIcon },
    { name: 'Settings', href: '/account/settings', icon: Cog6ToothIcon },
]

const callsToAction = [
    { name: 'See all', href: '/products/categories' },
]

function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
}

export default function Header() {
    const [mobileMenuOpen, setMobileMenuOpen] = useState(false)

    const {isAuthenticated} = useAuth()
    const auth = useAuth()

    const buttonRef = useRef();

    return (
        <header className="bg-transparent dark:bg-transparent shadow-md dark:shadow-sm dark:shadow-black">
            <nav className="mx-auto flex max-w-7xl items-center justify-between p-6 md:px-8 lg:px-8 xl:px-8 2xl:px-8" aria-label="Global">
                <div className="flex md:flex-1 lg:flex-1 xl:flex-1 2xl:flex-1">
                    <Link to='/' className="-m-1.5 p-1.5">
                        <picture>
                            <source
                                srcSet="./icon-dark.svg"
                                media="(prefers-color-scheme: dark)"
                                className="h-8 w-auto"
                            />
                            <img className="h-8 w-auto"
                                 src="./icon.svg"
                                 alt="" />
                        </picture>
                    </Link>
                </div>
                <div className="flex md:hidden lg:hidden xl:hidden 2xl:hidden">
                    <Link to='/cart' className="relative mr-6">
                        <ShoppingBagIcon className="h-6 w-6 text-gray-900 dark:text-inherit"/>
                        <div className="absolute
                             inline-flex items-center justify-center
                             w-4 h-4
                             text-xxs font-bold text-white bg-red-500 border-0 border-white rounded-full
                             -top-2 -right-2
                             dark:border-gray-900">
                                3
                        </div>
                    </Link>
                    <button
                        type="button"
                        className="-m-2.5 inline-flex items-center justify-center rounded-md p-2.5 text-inherit"
                        onClick={() => setMobileMenuOpen(true)}
                    >
                        <span className="sr-only">Open main menu</span>
                        <Bars3Icon className="h-6 w-6" aria-hidden="true" />
                    </button>
                </div>
                <Popover.Group className="hidden md:flex md:gap-x-12 lg:flex lg:gap-x-12 xl:flex xl:gap-x-12 2xl:flex 2xl:gap-x-12 text-gray-900 dark:text-gray-100">
                    <Popover className="relative">
                        <Popover.Button ref={buttonRef} className="flex items-center gap-x-1 text-sm font-semibold leading-6">
                            Categories
                            <ChevronDownIcon className="h-5 w-5 flex-none text-gray-400 dark:text-inherit" aria-hidden="true" />
                        </Popover.Button>

                        <Transition className="text-inherit dark:text-inherit"
                            as={Fragment}
                            enter="transition ease-out duration-200"
                            enterFrom="opacity-0 translate-y-1"
                            enterTo="opacity-100 translate-y-0"
                            leave="transition ease-in duration-150"
                            leaveFrom="opacity-100 translate-y-0"
                            leaveTo="opacity-0 translate-y-1"
                        >
                            <Popover.Panel className="absolute -left-8 top-full z-10 mt-3 w-auto max-w-md overflow-hidden rounded-3xl bg-white dark:bg-zinc-800 shadow-lg ring-1 ring-gray-900/5
                            dark:bg-opacity-70 dark:backdrop-blur-md bg-opacity-70 backdrop-blur-md">
                                <div className="p-2">
                                    {products.map((item) => (
                                        <div
                                            key={item.name}
                                            className="group relative flex items-center gap-x-4 rounded-lg p-3 text-sm leading-6 hover:bg-gray-50 dark:hover:bg-zinc-900"
                                        >
                                            <div className="flex h-11 w-11 flex-none items-center justify-center rounded-lg bg-gray-50 group-hover:bg-white dark:bg-transparent dark:group-hover:bg-transparent dark:bg-zinc-800">
                                                <item.icon className="h-6 w-6 fill-gray-700 dark:fill-white group-hover:text-indigo-600 dark:group-hover:text-indigo-100" aria-hidden="true" />
                                            </div>
                                            <div className="flex-auto">
                                                <Link
                                                    onClick={() => buttonRef.current?.click()}
                                                    to={item.href} className="block font-semibold text-inherit dark:text-inherit">
                                                    {item.name}
                                                    <span className="absolute inset-0" />
                                                </Link>
                                            </div>
                                        </div>
                                    ))}
                                </div>
                                <div className="grid grid-cols divide-gray-900/5 bg-gray-50 dark:bg-zinc-800">
                                    {callsToAction.map((item) => (
                                        <Link
                                            key={item.name}
                                            to={item.href}
                                            className="flex items-center justify-center gap-x-2.5 p-3 text-xs font-semibold leading-6 text-blue-500 hover:bg-gray-100 dark:hover:bg-zinc-900"
                                        >
                                            {item.name}
                                        </Link>
                                    ))}
                                </div>
                            </Popover.Panel>
                        </Transition>
                    </Popover>

                    <Link to="/products" className="text-sm font-semibold leading-6 text-inherit dark:text-inherit">
                        Shop
                    </Link>
                    <Link to="/sellers" className="text-sm font-semibold leading-6 text-inherit dark:text-inherit">
                        Sellers
                    </Link>
                </Popover.Group>
                <div className="hidden md:flex md:flex-1 md:justify-end lg:flex lg:flex-1 lg:justify-end xl:flex xl:flex-1 xl:justify-end 2xl:flex 2xl:flex-1 2xl:justify-end text-inherit dark:text-inherit">

                    {isAuthenticated &&
                        <Link to="/favorites" className="inline-flex relative mr-8">
                            <HeartIcon strokeWidth="2" className="h-6 w-6 text-gray-900 dark:text-inherit"/>
                            <div className="absolute inline-flex items-center justify-center
                         w-4 h-4
                         text-xxs font-bold text-white bg-red-500 border-0 border-white rounded-full
                         -top-2 -left-2
                         dark:border-gray-900">
                                3
                            </div>
                        </Link>
                    }

                    <Link to='/cart' className="inline-flex relative mr-6">
                        <ShoppingBagIcon className="h-6 w-6 text-gray-900 dark:text-inherit"/>
                        <div className="absolute inline-flex items-center justify-center
                         w-4 h-4
                         text-xxs font-bold text-white bg-red-500 border-0 border-white rounded-full
                         -top-2 -left-2
                         dark:border-gray-900">
                            3
                        </div>
                    </Link>

                    {isAuthenticated &&
                        <Popover className="relative mr-5 ">
                            <Popover.Button ref={buttonRef}
                                className="inline-flex items-center gap-x-0 text-sm font-semibold leading-6 text-gray-900 dark:text-inherit">
                                <UserCircleIcon className="h-6 w-6" aria-hidden="true" />
                                <ChevronDownIcon className="h-5 w-5" aria-hidden="true" />
                            </Popover.Button>

                            <Transition
                                as={Fragment}
                                enter="transition ease-out duration-200"
                                enterFrom="opacity-0 translate-y-1"
                                enterTo="opacity-100 translate-y-0"
                                leave="transition ease-in duration-150"
                                leaveFrom="opacity-100 translate-y-0"
                                leaveTo="opacity-0 translate-y-1"
                            >

                                <Popover.Panel className="absolute -left-20 top-full z-10 mt-3 w-auto max-w-2xl overflow-hidden rounded-3xl bg-white dark:bg-zinc-800 shadow-lg ring-1 ring-gray-900/5
                                dark:bg-opacity-70 dark:backdrop-blur-md bg-opacity-70 backdrop-blur-md">
                                    <div className="p-2">
                                        {accountData.map((item) => (
                                            <div
                                                key={item.name}
                                                className="group relative flex items-center gap-x-6 rounded-lg p-2 text-sm leading-6 hover:bg-gray-50 dark:hover:bg-zinc-900"
                                            >
                                                <div className="flex h-11 w-11 flex-none items-center justify-center rounded-lg bg-gray-50 group-hover:bg-transparent dark:bg-zinc-800">
                                                    <item.icon className="h-6 w-6 text-gray-600 dark:text-gray-100 group-hover:text-indigo-600 dark:group-hover:text-indigo-100" aria-hidden="true" />
                                                </div>
                                                <div className="flex-auto">
                                                    <Link onClick={() => buttonRef.current?.click()}
                                                        to={item.href}
                                                          className="block font-semibold text-gray-900 dark:text-inherit">
                                                        {item.name}
                                                        <span className="absolute inset-0" />
                                                    </Link>
                                                </div>
                                            </div>
                                        ))}

                                        <hr
                                            className="my-1 h-0.5 border-t-0 bg-neutral-100 dark:bg-neutral-700 opacity-100 dark:opacity-50" />

                                        <div
                                            className="group relative flex items-center gap-x-6 rounded-lg p-2 text-sm leading-6 hover:bg-gray-50 dark:hover:bg-zinc-900"
                                        >
                                            <div className="flex h-11 w-11 flex-none items-center justify-center rounded-lg bg-transparent group-hover:bg-transparent dark:bg-zinc-800">
                                                <ArrowLeftOnRectangleIcon className="h-6 w-6 text-gray-600 dark:text-gray-100 group-hover:text-indigo-600 dark:group-hover:text-indigo-100" aria-hidden="true" />
                                            </div>
                                            <div className="flex-auto">
                                                <button
                                                    onClick={() => {
                                                            buttonRef.current?.click()
                                                            auth.logout()
                                                        }
                                                    }
                                                    className="block font-semibold text-gray-900 dark:text-inherit">
                                                    Log out
                                                    <span className="absolute inset-0" />
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </Popover.Panel>

                            </Transition>
                        </Popover>
                    }

                    {!isAuthenticated &&
                        <Link to="/login" className="text-sm font-semibold leading-6 text-inherit dark:text-inherit">
                            Log in <span aria-hidden="true">&rarr;</span>
                        </Link>
                    }
                </div>
            </nav>

            {/*TODO transition now working on dialog*/}
            <Transition show={mobileMenuOpen}
                        as={Fragment}
            >
                <Dialog as="div" className="lg:hidden xl:hidden 2xl:hidden" open={mobileMenuOpen} onClose={setMobileMenuOpen}>
                    <div className="fixed inset-0 z-10" />
                    <Dialog.Panel className="
                        fixed inset-y-0 right-0 z-10 w-full overflow-y-auto
                        bg-white dark:bg-zinc-800 dark:bg-opacity-90 dark:backdrop-blur-md bg-opacity-90 backdrop-blur-md
                        px-6 py-6
                        sm:max-w-sm sm:ring-1 sm:ring-gray-900/10
                        text-gray-900 dark:text-gray-100">

                        <div className="flex justify-end text-inherit dark:text-inherit">
                            <button
                                type="button"
                                className="-m-2.5 rounded-md p-2.5 pt-3.5 text-gray-700 dark:text-inherit"
                                onClick={() => setMobileMenuOpen(false)}
                            >
                                <span className="sr-only">Close menu</span>
                                <XMarkIcon className="h-6 w-6" aria-hidden="true" />
                            </button>
                        </div>

                        <div className="mt-6 flow-root text-inherit dark:text-inherit">
                            <div className="-my-6 divide-y divide-gray-500/10 text-inherit dark:text-inherit">
                                <div className="space-y-2 py-6 text-inherit dark:text-inherit">
                                    <Disclosure as="div" className="-mx-3">
                                        {({ open }) => (
                                            <>
                                                <Disclosure.Button className="flex w-full items-center justify-between rounded-lg py-2 pl-3 pr-3.5 text-base font-semibold leading-7 text-inherit dark:text-inherit hover:bg-gray-50 dark:hover:bg-zinc-900">
                                                    Categories
                                                    <ChevronDownIcon
                                                        className={classNames(open ? 'rotate-180' : '', 'h-5 w-5 flex-none')}
                                                        aria-hidden="true"
                                                    />
                                                </Disclosure.Button>
                                                <Disclosure.Panel className="mt-2 space-y-2 text-inherit dark:text-inherit">
                                                    {[...products].map((item) => (
                                                        <Link
                                                            onClick={()=>setMobileMenuOpen(false)}
                                                            key={item.name}
                                                            to={item.href}
                                                            className="block rounded-lg py-2 pl-6 pr-3 text-sm font-semibold leading-7 text-inherit dark:text-inherit hover:bg-gray-50 dark:hover:bg-zinc-900"
                                                        >
                                                            {item.name}
                                                        </Link>
                                                    ))}
                                                    {[...callsToAction].map((item) => (
                                                        <Link
                                                            onClick={()=>setMobileMenuOpen(false)}
                                                            key={item.name}
                                                            to={item.href}
                                                            className="block rounded-lg py-2 pl-6 pr-3 text-sm font-semibold leading-7 text-blue-500 hover:bg-gray-50 dark:hover:bg-zinc-900"
                                                        >
                                                            {item.name}
                                                        </Link>
                                                    ))}
                                                </Disclosure.Panel>
                                            </>
                                        )}
                                    </Disclosure>
                                    <Link
                                        to="/products"
                                        onClick={()=>setMobileMenuOpen(false)}
                                        className="-mx-3 block rounded-lg px-3 py-2 text-base font-semibold leading-7 text-inherit dark:text-inherit hover:bg-gray-50 dark:hover:bg-zinc-900"
                                    >
                                        Shop
                                    </Link>
                                    <Link
                                        to="/sellers"
                                        onClick={()=>setMobileMenuOpen(false)}
                                        className="-mx-3 block rounded-lg px-3 py-2 text-base font-semibold leading-7 text-inherit dark:text-inherit hover:bg-gray-50 dark:hover:bg-zinc-900"
                                    >
                                        Sellers
                                    </Link>
                                </div>
                                <div className="py-6">

                                    {isAuthenticated &&
                                        <Link
                                            onClick={()=>setMobileMenuOpen(false)}
                                            to="/account/favorites"
                                            className="-mx-3 block rounded-lg px-3 py-2.5 text-base font-semibold leading-7 text-inherit dark:text-inherit hover:bg-gray-50 dark:hover:bg-zinc-900"
                                        >
                                            Favorites
                                        </Link>
                                    }

                                    {!isAuthenticated &&
                                        <Link
                                            onClick={()=>setMobileMenuOpen(false)}
                                            to="/login"
                                            className="-mx-3 block rounded-lg px-3 py-2.5 text-base font-semibold leading-7 text-inherit dark:text-inherit hover:bg-gray-50 dark:hover:bg-zinc-900"
                                        >
                                            Log in
                                        </Link>
                                    }

                                    {isAuthenticated &&
                                        <Disclosure as="div" className="-mx-3 text-inherit dark:text-inherit">
                                            {({ open }) => (
                                                <>
                                                    <Disclosure.Button className="flex w-full items-center justify-between rounded-lg py-2 pl-3 pr-3.5 text-base font-semibold leading-7 text-inherit dark:text-inherit hover:bg-gray-50 dark:hover:bg-zinc-900">
                                                        Account
                                                        <ChevronDownIcon
                                                            className={classNames(open ? 'rotate-180' : '', 'h-5 w-5 flex-none')}
                                                            aria-hidden="true"
                                                        />
                                                    </Disclosure.Button>
                                                    <Disclosure.Panel className="mt-2 space-y-2">
                                                        {[...accountData].map((item) => (
                                                            <Link
                                                                onClick={()=>setMobileMenuOpen(false)}
                                                                key={item.name}
                                                                to={item.href}
                                                                className="block rounded-lg py-2 pl-6 pr-3 text-sm font-semibold leading-7 text-inherit dark:text-inherit hover:bg-gray-50 dark:hover:bg-zinc-900"
                                                            >
                                                                {item.name}
                                                            </Link>
                                                        ))}

                                                        <hr
                                                            className="my-1 h-0.5 border-t-0 bg-neutral-100 dark:bg-neutral-700 opacity-100 dark:opacity-50" />

                                                        <button
                                                            onClick={()=>{
                                                                    setMobileMenuOpen(false)
                                                                    auth.logout()
                                                                }
                                                            }
                                                            className="block rounded-lg py-2 pl-6 pr-3 text-sm font-semibold leading-7 text-inherit dark:text-inherit hover:bg-gray-50 dark:hover:bg-zinc-900"
                                                        >
                                                            Logout
                                                        </button>

                                                    </Disclosure.Panel>
                                                </>
                                            )}
                                        </Disclosure>
                                    }
                                </div>
                            </div>
                        </div>
                    </Dialog.Panel>
                </Dialog>
            </Transition>
        </header>
    )
}
