import service from '@/services/modules/personal/experiencia_laboral'

export default {
  EXPERIENCIA_LABORAL_FIND_ALL(_, params) {
    return new Promise((resolve, reject) => {
      service.findAll(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  EXPERIENCIA_LABORAL_FIND_BY_ID(_, params) {
    return new Promise((resolve, reject) => {
      service.findById(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  EXPERIENCIA_LABORAL_CREATE(_, params) {
    return new Promise((resolve, reject) => {
      service.create(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  EXPERIENCIA_LABORAL_UPDATE(_, params) {
    return new Promise((resolve, reject) => {
      service.update(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  EXPERIENCIA_LABORAL_DELETE(_, params) {
    return new Promise((resolve, reject) => {
      service.delete(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
}
