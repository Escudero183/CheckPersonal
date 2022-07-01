import service from '@/services/modules/catalogo/tipo_documento'

export default {
  TIPO_DOCUMENTO_FIND_ALL(_, params) {
    return new Promise((resolve, reject) => {
      service.findAll(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
}
