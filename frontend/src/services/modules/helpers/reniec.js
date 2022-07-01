import apiCall from '../../index'

const apiService = '/api-checkpersonal/api/v1/helper/reniec/'
const authorization = 'Authorization'
const tipoToken = 'Bearer'

const service = {
  findCiudadano(params) {
    const query = `getDatosCiudadano/${params.dni}`
    return new Promise((resolve, reject) => {
      apiCall
        .defaults
        .headers
        .common[authorization] = `${tipoToken} ${localStorage.getItem('accessToken')}`
      apiCall
        .get(apiService + query)
        .then(res => resolve(res.data))
        .catch(err => reject(err))
    })
  },
}

export default service
