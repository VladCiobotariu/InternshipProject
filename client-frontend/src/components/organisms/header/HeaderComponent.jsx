import React, { Fragment, useRef, useState, useEffect } from 'react';
import {Link, useLocation, useNavigate} from 'react-router-dom';
import { useAuth } from '../../../auth/AuthContext';
import { getAllCategoriesApi } from "../../../api/CategoryApi";
import { baseURL } from "../../../auth/ApiClient";
import '../../../styles/Header.css'

import { Dialog, Disclosure, Popover, Transition } from '@headlessui/react';
import {
    Bars3Icon,
    XMarkIcon,
    UserCircleIcon,
    ClipboardDocumentListIcon,
    Cog6ToothIcon,
    ArrowLeftOnRectangleIcon,
    ShoppingBagIcon,
    HeartIcon,
} from '@heroicons/react/24/outline';
import { ChevronDownIcon } from '@heroicons/react/20/solid';
import {ReactComponent as Logo} from "./icon.svg";
import {getCartItems} from "../../../api/CartApi";
import {useFavorite} from "../../../contexts/FavoriteContext";

const accountData = [
    { name: 'Orders', href: '/account/orders', icon: ClipboardDocumentListIcon },
    { name: 'Settings', href: '/account/settings', icon: Cog6ToothIcon },
];

const callsToAction = [
    { name: 'See all', href: '/products/categories' },
];

function classNames(...classes) {
    return classes.filter(Boolean).join(' ');
}

export default function HeaderComponent() {
    const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

    const [categories, setCategories] = useState([]);

    const[numberOfCartItems, setNumberOfCartItems] = useState(0)

    const { isAuthenticated, username } = useAuth();
    const auth = useAuth();

    const location = useLocation()
    const buttonRef = useRef();

    const navigate = useNavigate()
    const {allFavorites, numberOfFavorites, removeFromFavorite} = useFavorite();

    const getCategoryList = () => {
        getAllCategoriesApi()
            .then((res) => {
                setCategories(res.data.data);
            })
            .catch((err) => console.log(err));
    };

    useEffect(() => {
        if(username){
            getCartItemsList()
        }
        getCategoryList()
    }, [location, username]);

    function getCartItemsList(){
        getCartItems()
            .then(
                (response) => setNumberOfCartItems(response.data.cartItems.length)
            )
            .catch(
                (err) => {
                    console.log(err)
                }
            )
    }

    const favoriteDeleteButton = (productId) => {
        removeFromFavorite(productId)
    }

    const createQueryParam = (categoryName) => {
        const queryParams = new URLSearchParams()
        queryParams.set("categoryName", categoryName);
        const newSearch = queryParams.toString();
        navigate(`/products?${newSearch}`);
    }

    //todo modify in product page so it wont be a z index this big here! or ask
    return (
        <header className="bg-white dark:bg-[#0F172A] shadow-md dark:shadow-sm dark:shadow-black sticky top-0 z-[1]">
            <nav className="mx-auto flex max-w-7xl items-center justify-between p-6 md:px-8 lg:px-8 xl:px-8 2xl:px-8" aria-label="Global">
                <div className="flex md:flex-1 lg:flex-1 xl:flex-1 2xl:flex-1">
                    <Link to='/' className="-m-1.5 p-1.5">
                        <Logo className="h-8 w-auto"/>
                    </Link>
                </div>
                <div className="flex md:hidden lg:hidden xl:hidden 2xl:hidden">
                    {!!username &&
                        <Link to='/account/cart' className="relative mr-6">
                            <ShoppingBagIcon className="h-6 w-6 text-gray-900 dark:text-inherit"/>
                            {numberOfCartItems!==0 &&
                                <div className="absolute
                                 inline-flex items-center justify-center
                                 w-4 h-4
                                 text-xxs font-bold text-white bg-red-500 border-0 border-white rounded-full
                                 -top-2 -right-2
                                 dark:border-gray-900">
                                    {numberOfCartItems}
                                </div>
                            }
                        </Link>
                    }

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

                        <Transition
                            className="text-inherit dark:text-inherit"
                            as={Fragment}
                            enter="transition ease-out duration-200"
                            enterFrom="opacity-0 translate-y-1"
                            enterTo="opacity-100 translate-y-0"
                            leave="transition ease-in duration-150"
                            leaveFrom="opacity-100 translate-y-0"
                            leaveTo="opacity-0 translate-y-1"
                        >
                            <Popover.Panel className="absolute -left-8 top-full z-10 mt-3 w-auto max-w-md overflow-hidden rounded-3xl bg-white dark:bg-zinc-800 shadow-lg ring-1 ring-gray-900/5 dark:bg-opacity-70 dark:backdrop-blur-md bg-opacity-70 backdrop-blur-md">
                                <div className="p-2">
                                    {categories.map((item) => (
                                        <div
                                            key={item.id}
                                            className="group relative flex items-center gap-x-4 rounded-lg p-3 text-sm leading-6 hover:bg-gray-50 dark:hover:bg-zinc-900"
                                        >
                                            <div className="flex h-11 w-11 flex-none items-center justify-center rounded-lg bg-gray-50 group-hover:bg-white dark:bg-transparent dark:group-hover:bg-transparent dark:bg-zinc-800">
                                                <img
                                                    src={`${baseURL}${item.imageName}`}
                                                    alt={item.name}
                                                    className="h-6 w-6 group-hover:text-indigo-600 dark:group-hover:text-indigo-100 dark:invert" aria-hidden="true"
                                                />
                                            </div>
                                            <div className="flex-auto">
                                                <div
                                                    onClick={() => {
                                                        buttonRef.current?.click();
                                                        createQueryParam(item.name);
                                                    }}
                                                    className="block font-semibold text-inherit dark:text-inherit"
                                                >
                                                    {item.name}
                                                    <span className="absolute inset-0" />
                                                </div>
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
                        <Popover className="relative mr-6">
                            <Popover.Button
                                            className="inline-flex items-center gap-x-0 text-sm font-semibold leading-6 text-gray-900 dark:text-inherit"
                            >
                                <HeartIcon strokeWidth="2" className="h-6 w-6 text-gray-900 dark:text-inherit" aria-disabled="true"/>
                                {numberOfFavorites!==0 &&
                                    <div className="absolute inline-flex items-center justify-center
                                             w-4 h-4
                                             text-xxs font-bold text-white bg-red-500 border-0 border-white rounded-full
                                             -top-2 -left-2
                                             dark:border-gray-900">
                                        {numberOfFavorites}
                                    </div>
                                }
                            </Popover.Button>

                            {numberOfFavorites!==0 &&
                                <Transition
                                    as={Fragment}
                                    enter="transition ease-out duration-900"
                                    enterFrom="opacity-0 translate-y-1"
                                    enterTo="opacity-100 translate-y-0"
                                    leave="transition ease-in duration-150"
                                    leaveFrom="opacity-100 translate-y-0"
                                    leaveTo="opacity-0 translate-y-1"
                                >

                                    <Popover.Panel className="
                                       absolute -left-20 mt-2 top-full z-10 rounded-3xl bg-white
                                       dark:bg-zinc-800 shadow-lg ring-1 ring-gray-900/5
                                        dark:bg-opacity-70 dark:backdrop-blur-md bg-opacity-70 backdrop-blur-md"
                                    >
                                        <div className="p-2">
                                            {allFavorites.map((item) => (

                                                /**
                                                 * @param {{
                                                 *     seller:{
                                                 *         alias
                                                 *     },
                                                 *     name,
                                                 *     imageName
                                                 * }} item
                                                 */

                                                <div
                                                    key={item.id}
                                                    className="relative flex items-center justify-between gap-x-2 rounded-lg text-sm leading-6"
                                                >
                                                    <Link to={`/${item.seller.alias}/products/${item.name}`}
                                                        className="flex items-center gap-x-6 hover:bg-gray-50 dark:hover:bg-zinc-900 rounded-lg p-2">
                                                        <div className="flex h-11 w-11 flex-none items-center justify-center rounded-lg bg-gray-50 group-hover:bg-transparent dark:bg-zinc-800">
                                                            <img className="h-6 w-6"
                                                                 alt=""
                                                                 src={`${baseURL}${item.imageName}`}
                                                            />
                                                        </div>

                                                        <div className="flex-auto max-w-[118px] w-max">
                                                            <div className="block font-semibold text-gray-900 dark:text-inherit truncate">
                                                                {item.name}
                                                                <div>{item.price} RON</div>
                                                            </div>
                                                        </div>
                                                    </Link>

                                                    <button className="flex h-11 w-11 flex-none items-center justify-center rounded-lg
                                                            text-gray-600 transition hover:text-red-600 hover:bg-gray-50 dark:hover:bg-zinc-900"
                                                    onClick={()=>favoriteDeleteButton(item.id)}>
                                                        <svg
                                                            xmlns="http://www.w3.org/2000/svg"
                                                            fill="none"
                                                            viewBox="0 0 24 24"
                                                            strokeWidth="1.5"
                                                            stroke="currentColor"
                                                            className="h-5 w-5"
                                                        >
                                                            <path
                                                                strokeLinecap="round"
                                                                strokeLinejoin="round"
                                                                d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0"
                                                            />
                                                        </svg>
                                                    </button>
                                                </div>

                                            ))}
                                        </div>
                                    </Popover.Panel>

                                </Transition>
                            }
                        </Popover>
                    }

                    {!!username &&
                        <Link to='/account/cart' className="inline-flex relative mr-6">
                            <ShoppingBagIcon className="h-6 w-6 text-gray-900 dark:text-inherit"/>
                            {numberOfCartItems!==0 &&
                                <div className="absolute inline-flex items-center justify-center
                                 w-4 h-4
                                 text-xxs font-bold text-white bg-red-500 border-0 border-white rounded-full
                                 -top-2 -left-2
                                 dark:border-gray-900">
                                    {numberOfCartItems}
                                </div>
                            }
                        </Link>
                    }

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
                                                    {[...categories].map((item) => (
                                                        <div
                                                            onClick={() => {
                                                                setMobileMenuOpen(false);
                                                                createQueryParam(item.name);
                                                            }}
                                                            key={item.name}
                                                            className="block rounded-lg py-2 pl-6 pr-3 text-sm font-semibold leading-7 text-inherit dark:text-inherit hover:bg-gray-50 dark:hover:bg-zinc-900"
                                                        >
                                                            {item.name}
                                                        </div>
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
                                                            className="flex w-full rounded-lg py-2 pl-6 pr-3 text-sm font-semibold leading-7 text-inherit dark:text-inherit hover:bg-gray-50 dark:hover:bg-zinc-900"
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
