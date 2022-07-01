import apiCall from '../../index'

const apiService = '/api-checkpersonal/api/v1/catalogo/ubigeo'
const authorization = 'Authorization'
const tipoToken = 'Bearer'

const service = {
  findAll(params) {
    const query = `?codUbigeo=${params.codUbigeo}&get=${params.get}`
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
