export default {
  getSistemaTpl: state => {
    let table = 'Sistema'
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
