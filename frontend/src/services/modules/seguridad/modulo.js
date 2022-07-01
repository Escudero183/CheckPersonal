import apiCall from '../../index'

const apiService = '/api-checkpersonal/api/v1/seguridad/modulo'

const service = {
  findAll(params) {
    const query = `?tipo=${params.tipo}&idSistema=${params.idSistema}&limit=${params.limit}&page=${params.page}&query=${params.query}&sortBy=${params.sortBy}`
    return new Promise((resolve, reject) => {
      apiCall
        .get(apiService + query)
        .then(res => resolve(res.data))
        .catch(err => reject(err))
    })
  },
  create(model) {
    return new Promise((resolve, reject) => {
      apiCall
        .post(apiService, model)
        .then(res => resolve(res.data))
        .catch(err => reject(err))
    })
  },
  update(model) {
    return new Promise((resolve, reject) => {
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
