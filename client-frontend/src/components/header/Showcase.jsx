

const ProductItem = () => {
 //todo
}


const PriceRangeFilter = ({priceFrom, priceTo, onPriceFromChanged, onPriceToChanged}) => {
    <input onChange={() => onPriceFromChanged()}>{priceFrom}</input>
    <input onChange={() => onPriceToChanged()}>{priceTo}</input>
}

const FilterItem = ({label, children, etc}) => {
    <labe>{label}</labe>
    <icon></icon>

    <div>
        {children}
    </div>
}

class FilterOption {
    filterKey;
    filterValue;
}

const ProductsFilterHeader = ({ onFilterChanged, onSortingChanged}) => {

    filterOptions = usestate();

    [priceFrom, setPriceFrom] = userState();
    priceTo = userState();

    changedFromPrice(val) {
        setPriceFrom(val);
        notifyFilterChanged();
    }

    changeToPrice() {

    }

    notifyFilterChanged() {
        const filterOptions = [];

        if(priceFrom != null) {
            filterOptions.push({filterKey: 'priceFrom', filterValue: priceFrom} )
        }
        if(priceFrom != null) {
            filterOptions.push({filterKey: 'priceTo', filterValue: priceTo} )
        }

        onFilterChanged(filterOptions);
    }

    <div>
        <FilterItem LabelParam={price}>
            <div>
               <PriceRangeFilter
                   priceTo={priceTo}
                   priceFrom={priceFrom}
                   onPriceFromChanged={() => changedFromPrice()} onPriceToChanged={() => changedFromPrice()}  />
            </div>
        </FilterItem>
        <FilterItem>
            <div>
                todo implement price range filter
            </div>
        </FilterItem>
        <FilterItem>
            <div>
                todo 1233
            </div>
        </FilterItem>
    </div>

    <div>
        todo search bar
    </div>

    <div>
        todo sorting
    </div>

    <div>
        if(priceFrom) {
        <FilterTag tagName="PriceFrom"} onRemove={() => changedFromPrice(null)}></FilterTag>
        }

        if(priceTo) {
            <FilterTag tagName="PriceTo"} onRemove={() => changedToPrice(null)}></FilterTag>
        }
    </div>
}


const ProductPage = () => {

    filterProducts(filterOptions) {
        ///calll API and refresh products...
    }

    return (
        <ProductsFilterHeader onFilterChanged={val}/>

        for(product in products) {
            <div>
                <ProductItem/>
            </div>
        }

    )
}