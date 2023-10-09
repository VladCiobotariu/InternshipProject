
function BigContainer({children}){

    return(
        <div className="mx-auto max-w-5xl px-6 mt-16 md:flex md:space-x-6 xl:px-0 lg:flex lg:space-x-6 xl:flex xl:space-x-8 2xl:flex 2xl:space-x-8">
            {children}
        </div>
    )
}

export default BigContainer