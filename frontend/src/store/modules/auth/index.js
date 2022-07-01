// import actions
import authAction from './actions/auth'

// import getters
import authGetter from './getters/auth'

// import mutations
import authMutation from './mutations/auth'

// import states
import authState from './states/auth'

const actions = {
  ...authAction,
}

const getters = {
  ...authGetter,
}

const mutations = {
  ...authMutation,
}

const state = {
  ...authState,
}

export default {
  namespaced: true,
  actions,
  state,
  getters,
  mutations,
}
