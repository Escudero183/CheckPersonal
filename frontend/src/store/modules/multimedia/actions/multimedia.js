import service from '@/services/modules/multimedia/multimedia'

export default {
  MULTIMEDIA_BY_URL(_, params) {
    return new Promise((resolve, reject) => {
      service.byUrl(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  MULTIMEDIA_BY_SECCION(_, params) {
    return new Promise((resolve, reject) => {
      service.findBySeccion(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  MULTIMEDIA_CREATE(_, params) {
    return new Promise((resolve, reject) => {
      service.create(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  MULTIMEDIA_DELETE(_, params) {
    return new Promise((resolve, reject) => {
      service.delete(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  MULTIMEDIA_SORT(_, params) {
    return new Promise((resolve, reject) => {
      service.sort(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
}
