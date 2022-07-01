export default {
  OPERACION_MODULO: (state, mutation) => {
    state.operacion = mutation.oper
    state.id = mutation.id
  },
}
