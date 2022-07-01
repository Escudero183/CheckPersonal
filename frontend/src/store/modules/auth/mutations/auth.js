export default {
  OPERACION_AUTH: (state, mutation) => {
    state.operacion = mutation.oper
    state.id = mutation.id
  },
}
