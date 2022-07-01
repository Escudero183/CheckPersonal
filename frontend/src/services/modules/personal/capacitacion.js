import apiCall from '../../index'

const apiService = '/api-checkpersonal/api/v1/personal/capacitacion'
const authorization = 'Authorization'
const tipoToken = 'Bearer'

const service = {
  findAll(params) {
    const query = `?idPersonal=${params.idPersonal}&tipo=${params.tipo}&limit=${params.limit}&page=${params.page}&query=${params.query}&sortBy=${params.sortBy}`
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
  findById(params) {
    const query = `/${params.id}`
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
