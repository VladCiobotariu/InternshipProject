import { api } from '../ApiClient'

export const getProductsApi = (page, itemsPerPage, sortSpecs, filterSpecs) => {

    return api.get(`/products`, {
        params: {
            page: page,
            itemsPerPage: itemsPerPage,
            sort: JSON.stringify(sortSpecs),
            filter: JSON.stringify(filterSpecs)
        }
    });
}
// export const getProductsApi = (page, itemsPerPage, sortSpecs, filterSpecs) => {
//
//     return api.get(`/products`, {
//         paramsSerializer: function({page,itemsPerPage,sortSpecs, filterSpecs}) {
//
//             let encodedFilterSpecs = encodeURIComponent(filterSpecs);
//             let encodedSortSpecs = encodeURIComponent(sortSpecs);
//             let result = {
//                 page: page,
//                 itemsPerPage: itemsPerPage,
//                 sort: encodedSortSpecs,
//                 filterSpecs: encodedFilterSpecs
//             }
//
//             return result;
//         }
//
//     });
// }