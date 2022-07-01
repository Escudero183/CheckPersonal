// import actions
import reniecAction from './actions/reniec'
import rucAction from './actions/ruc'

const actions = {
  ...reniecAction, ...rucAction,
}

export default {
  namespaced: true,
  actions,
}
