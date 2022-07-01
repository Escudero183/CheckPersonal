import service from '@/services/modules/helpers/reniec'

export default {
  RENIEC_FIND_CIUDADANO(_, params) {
    return new Promise((resolve, reject) => {
      service.findCiudadano(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
}
