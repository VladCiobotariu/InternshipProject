import { api } from '../auth/ApiClient'

export const getLocationsApi = () => {
    return api.get("/cities")
}
