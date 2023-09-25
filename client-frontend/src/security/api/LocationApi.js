import { api } from '../ApiClient'

export const getLocationsApi = () => {
    return api.get("/cities")
}
