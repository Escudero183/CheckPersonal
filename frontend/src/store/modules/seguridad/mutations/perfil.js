export default {
  OPERACION_PERFIL: (state, mutation) => {
    state.operacion = mutation.oper
    state.id = mutation.id
  },
}
