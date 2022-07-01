export default {
  OPERACION_USUARIO: (state, mutation) => {
    state.operacion = mutation.oper
    state.id = mutation.id
  },
}
