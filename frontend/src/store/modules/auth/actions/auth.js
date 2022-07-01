import service from '@/services/modules/auth/auth'

export default {
  AUTH_CREATE_TOKEN(_, params) {
    return new Promise((resolve, reject) => {
      service.createToken(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
}
