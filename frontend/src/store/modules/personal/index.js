// import actions
import personalAction from './actions/personal'
import formacionAcademicaAction from './actions/formacion_academica'
import experienciaLaboralAction from './actions/experiencia_laboral'
import habilidad from './actions/habilidad'
import capacitacion from './actions/capacitacion'

const actions = {
  ...personalAction, ...habilidad, ...capacitacion, ...experienciaLaboralAction, ...formacionAcademicaAction,
}

export default {
  namespaced: true,
  actions,
}
