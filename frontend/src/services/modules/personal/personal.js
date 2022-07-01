import apiCall from '../../index'

const apiService = '/api-checkpersonal/api/v1/personal/personal'
const authorization = 'Authorization'
const tipoToken = 'Bearer'

const service = {
  findAll(params) {
    const query = `?sexo=${params.sexo}&tipo=${params.tipo}&limit=${params.limit}&page=${params.page}&query=${params.query}&sortBy=${params.sortBy}`
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
  create2(model) {
    const formData = new FormData()
    formData.append('numeroDocumento', model.numeroDocumento)
    formData.append('nombres', model.nombres)
    formData.append('apellidoPaterno', model.apellidoPaterno)
    formData.append('apellidoMaterno', model.apellidoMaterno)
    formData.append('direccion', model.direccion)
    formData.append('sexo', model.sexo)
    formData.append('celular', model.celular)
    formData.append('correo', model.correo)
    formData.append('presentacion', model.presentacion)
    formData.append('idTipoDocumento', model.idTipoDocumento)
    formData.append('idUbigeo', model.idUbigeo)
    formData.append('foto', model.foto)
    if (model.fechaNacimiento) formData.append('fechaNacimiento', model.fechaNacimiento)
    if (model.banner !== null) formData.append('banner', model.banner)
    return new Promise((resolve, reject) => {
      apiCall
        .defaults
        .headers
        .common[authorization] = `${tipoToken} ${localStorage.getItem('accessToken')}`
      apiCall
        .post(`${apiService}2`, formData)
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
  update2(model) {
    const formData = new FormData()
    formData.append('idPersonal', model.idPersonal)
    formData.append('numeroDocumento', model.numeroDocumento)
    formData.append('nombres', model.nombres)
    formData.append('apellidoPaterno', model.apellidoPaterno)
    formData.append('apellidoMaterno', model.apellidoMaterno)
    formData.append('direccion', model.direccion)
    formData.append('sexo', model.sexo)
    formData.append('celular', model.celular)
    formData.append('correo', model.correo)
    formData.append('presentacion', model.presentacion)
    formData.append('idTipoDocumento', model.idTipoDocumento)
    formData.append('idUbigeo', model.idUbigeo)
    formData.append('foto', model.foto)
    if (model.fechaNacimiento) formData.append('fechaNacimiento', model.fechaNacimiento)
    if (model.banner !== null) formData.append('banner', model.banner)
    return new Promise((resolve, reject) => {
      apiCall
        .defaults
        .headers
        .common[authorization] = `${tipoToken} ${localStorage.getItem('accessToken')}`
      apiCall
        .put(`${apiService}2`, formData)
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
