import apiCall from '../../index'

const apiService = '/api-checkpersonal/api/auth'

const service = {
  createToken(model) {
    return new Promise((resolve, reject) => {
      apiCall
        .post(apiService, model)
        .then(res => resolve(res.data))
        .catch(err => reject(err))
    })
  },
}

export default service
