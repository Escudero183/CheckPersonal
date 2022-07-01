import service from '@/services/modules/comercial/tipo_cambio'

export default {
  TIPO_CAMBIO_FIND_ALL(_, params) {
    return new Promise((resolve, reject) => {
      service.findAll(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  TIPO_CAMBIO_SIMULADOR(_, params) {
    return new Promise((resolve, reject) => {
      service.simulador(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  TIPO_CAMBIO_FIND_BY_FECHA(_, params) {
    return new Promise((resolve, reject) => {
      service.findByFecha(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  TIPO_CAMBIO_FIND_BY_FECHA_VIGENTES(_, params) {
    return new Promise((resolve, reject) => {
      service.findByFechaVigentes(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  TIPO_CAMBIO_FIND_BY_ID(_, params) {
    return new Promise((resolve, reject) => {
      service.findById(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  TIPO_CAMBIO_CREATE(_, params) {
    return new Promise((resolve, reject) => {
      service.create(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  TIPO_CAMBIO_UPDATE(_, params) {
    return new Promise((resolve, reject) => {
      service.update(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  TIPO_CAMBIO_DELETE(_, params) {
    return new Promise((resolve, reject) => {
      service.delete(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
}
