export function showToast(title, msg, type) {
  this.$bvToast.toast(msg, {
    title,
    variant: type,
    solid: true,
  })
}

export function showError(error) {
  let errorMessage = 'Intentelo en otro momento.'
  if (error.response) {
    errorMessage = error.response.data.message
  }
  this.$bvToast.toast(errorMessage, {
    title: 'Ha ocurrido un error',
    variant: 'danger',
    solid: true,
  })
}

export function getValidationState({
  dirty,
  validated,
  valid = null,
}) {
  return dirty || validated ? valid : null
}

export function onlyLetters(e) {
  const key = e.keyCode || e.which
  const tecla = String.fromCharCode(key).toLowerCase()
  const letras = ' áéíóúabcdefghijklmnñopqrstuvwxyz'
  const especiales = [8, 9, 37, 39, 46]
  let teclaEspecial = false
  especiales.forEach(valor => {
    if (key === valor) {
      teclaEspecial = true
    }
  })
  if (letras.indexOf(tecla) === -1 && !teclaEspecial) {
    e.preventDefault()
    return false
  }
  return true
}
export function onlyNumbers($event) {
  if ($event.charCode === 0 || /\d/.test(String.fromCharCode($event.charCode))) {
    return true
  }
  $event.preventDefault()
  return false
}
export function validDecimals($evt, refId, decimals, $refs) {
  const inp = $refs[`est_${refId}`].localValue
  console.log($refs)
  console.log($refs[`est_${refId}`])
  const texto = inp
  const punto = texto.indexOf('.')
  if ($evt.keyCode <= 13 || ($evt.keyCode >= 48 && $evt.keyCode <= 57)) {
    if ((punto !== -1) && (texto.length - (punto + 1)) >= decimals) {
      $evt.preventDefault()
      return false
    }
  } else if ($evt.keyCode === 46 && texto.length >= 1) {
    if (punto !== -1 && texto.indexOf('.', punto) !== -1) {
      $evt.preventDefault()
      return false
    }
  } else {
    $evt.preventDefault()
    return false
  }
  return true
}
