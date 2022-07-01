export default {
  getAuthTpl: state => {
    let table = 'Auth'
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
