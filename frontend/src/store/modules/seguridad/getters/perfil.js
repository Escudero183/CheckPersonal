export default {
  getPerfilTpl: state => {
    let table = 'Perfil'
    switch (state.operacion) {
      case 'edit':
      case 'new':
        table = `${table}Form`
        break
      default:
        table = `${table}List`
        break
    }
    return table
  },
}
