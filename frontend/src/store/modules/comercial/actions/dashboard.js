import service from '@/services/modules/comercial/dashboard'

export default {
  DASHBOARD_GET_DASHBOARD(_, params) {
    return new Promise((resolve, reject) => {
      service.getDashboard(params).then(res => {
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
}
