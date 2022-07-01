export default {
  OPERACION_SISTEMA: (state, mutation) => {
    state.operacion = mutation.oper
    state.id = mutation.id
  },
}
