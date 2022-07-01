import service from '@/services/modules/helpers/ruc'

export default {
  GET_DATOS_BY_RUC(_, params) {
    return new Promise((resolve, reject) => {
      service.getDatosByRuc(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
}
