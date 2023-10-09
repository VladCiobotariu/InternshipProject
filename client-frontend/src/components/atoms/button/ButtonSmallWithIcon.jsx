function ButtonSmallWithIcon({text, onClick, children}){

    return(
        <button type="button" onClick={onClick} className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-xs px-4 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 gap-2 flex flex-row">
            <div>
                {children}
            </div>
            <div>
                {text}
            </div>
        </button>
    )
}

export default ButtonSmallWithIcon