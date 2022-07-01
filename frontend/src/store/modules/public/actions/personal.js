import service from '@/services/modules/public/personal'

export default {
  PUBLIC_PERSONAL_FIND_PROFILE(_, params) {
    return new Promise((resolve, reject) => {
      service.findProfile(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
}
