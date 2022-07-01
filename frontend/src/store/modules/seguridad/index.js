// import actions
import moduloAction from './actions/modulo'
import perfilAction from './actions/perfil'
import sistemaAction from './actions/sistema'
import usuarioAction from './actions/usuario'

// import getters
import moduloGetter from './getters/modulo'
import perfilGetter from './getters/perfil'
import sistemaGetter from './getters/sistema'
import usuarioGetter from './getters/usuario'

// import mutations
import moduloMutation from './mutations/modulo'
import perfilMutation from './mutations/perfil'
import sistemaMutation from './mutations/sistema'
import usuarioMutation from './mutations/usuario'

// import states
import moduloState from './states/modulo'
import perfilState from './states/perfil'
import sistemaState from './states/sistema'
import usuarioState from './states/usuario'

const actions = {
  ...moduloAction,
  ...perfilAction,
  ...sistemaAction,
  ...usuarioAction,
}

const getters = {
  ...moduloGetter,
  ...perfilGetter,
  ...sistemaGetter,
  ...usuarioGetter,
}

const mutations = {
  ...moduloMutation,
  ...perfilMutation,
  ...sistemaMutation,
  ...usuarioMutation,
}

const state = {
  ...moduloState,
  ...perfilState,
  ...sistemaState,
  ...usuarioState,
}

export default {
  namespaced: true,
  actions,
  state,
  getters,
  mutations,
}
