import apiCall from '../../index'

const apiService = '/api-checkpersonal/api/v1/helper/ruc/'
const authorization = 'Authorization'
const tipoToken = 'Bearer'

const service = {
  getDatosByRuc(params) {
    return new Promise((resolve, reject) => {
      apiCall
        .defaults
        .headers
        .common[authorization] = `${tipoToken} ${localStorage.getItem('accessToken')}`
      apiCall
        .get(apiService + params.dni)
        .then(res => resolve(res.data))
        .catch(err => reject(err))
    })
  },
}

export default service
