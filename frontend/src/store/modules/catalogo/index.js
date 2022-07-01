// import actions
import tipoDocumentoAction from './actions/tipo_documento'
import ubigeoAction from './actions/ubigeo'

const actions = { ...tipoDocumentoAction, ...ubigeoAction }

export default {
  namespaced: true,
  actions,
}
