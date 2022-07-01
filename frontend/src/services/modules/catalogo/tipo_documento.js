import apiCall from '../../index'

const apiService = '/api-checkpersonal/api/v1/catalogo/tipo_documento'
const authorization = 'Authorization'
const tipoToken = 'Bearer'

const service = {
  findAll() {
    return new Promise((resolve, reject) => {
      apiCall
        .defaults
        .headers
        .common[authorization] = `${tipoToken} ${localStorage.getItem('accessToken')}`
      apiCall
        .get(apiService)
        .then(res => resolve(res.data))
        .catch(err => reject(err))
    })
  },
}

export default service
