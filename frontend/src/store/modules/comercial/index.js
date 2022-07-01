// import actions
import clienteAction from './actions/cliente'
import dashboardAction from './actions/dashboard'
import tipoCambioAction from './actions/tipo_cambio'
import transaccionAction from './actions/transaccion'

// import getters

// import mutations

// import states

const actions = { ...tipoCambioAction, ...clienteAction, ...transaccionAction, ...dashboardAction }

const getters = { }

const mutations = { }

const state = { }

export default {
  namespaced: true,
  actions,
  state,
  getters,
  mutations,
}
