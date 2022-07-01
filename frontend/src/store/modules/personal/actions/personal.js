import service from '@/services/modules/personal/personal'

export default {
  PERSONAL_FIND_ALL(_, params) {
    return new Promise((resolve, reject) => {
      service.findAll(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  PERSONAL_FIND_BY_ID(_, params) {
    return new Promise((resolve, reject) => {
      service.findById(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  PERSONAL_CREATE(_, params) {
    return new Promise((resolve, reject) => {
      service.create(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  PERSONAL_CREATE2(_, params) {
    return new Promise((resolve, reject) => {
      service.create2(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  PERSONAL_UPDATE(_, params) {
    return new Promise((resolve, reject) => {
      service.update(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  PERSONAL_UPDATE2(_, params) {
    return new Promise((resolve, reject) => {
      service.update2(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  PERSONAL_DELETE(_, params) {
    return new Promise((resolve, reject) => {
      service.delete(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
}
