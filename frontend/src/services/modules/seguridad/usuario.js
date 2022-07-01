import apiCall from '../../index'

const apiService = '/api-checkpersonal/api/v1/seguridad/usuario'
const authorization = 'Authorization'
const tipoToken = 'Bearer'

const service = {
  findAll(params) {
    const query = `?limit=${params.limit}&page=${params.page}&query=${params.query}&sortBy=${params.sortBy}`
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
  create(model) {
    return new Promise((resolve, reject) => {
      apiCall
        .defaults
        .headers
        .common[authorization] = `${tipoToken} ${localStorage.getItem('accessToken')}`
      apiCall
        .post(apiService, model)
        .then(res => resolve(res.data))
        .catch(err => reject(err))
    })
  },
  update(model) {
    return new Promise((resolve, reject) => {
      apiCall
        .defaults
        .headers
        .common[authorization] = `${tipoToken} ${localStorage.getItem('accessToken')}`
      apiCall
        .put(apiService, model)
        .then(res => resolve(res.data))
        .catch(err => reject(err))
    })
  },
  resetPassword(model) {
    return new Promise((resolve, reject) => {
      apiCall
        .defaults
        .headers
        .common[authorization] = `${tipoToken} ${localStorage.getItem('accessToken')}`
      apiCall
        .put(`${apiService}/resetPassword`, model)
        .then(res => resolve(res.data))
        .catch(err => reject(err))
    })
  },
  delete(params) {
    return new Promise((resolve, reject) => {
      apiCall
        .delete(`${apiService}/${params.id}`)
        .then(res => resolve(res.data))
        .catch(err => reject(err))
    })
  },
}

export default service
