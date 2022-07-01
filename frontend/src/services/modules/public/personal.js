import apiCall from '../../index'

const apiService = '/api-checkpersonal/api/v1/public/personal'

const service = {
  findProfile(params) {
    const query = `/${params.idTipoDocumento}/${params.numeroDocumento}`
    return new Promise((resolve, reject) => {
      apiCall
        .get(apiService + query)
        .then(res => resolve(res.data))
        .catch(err => reject(err))
    })
  },
}

export default service
