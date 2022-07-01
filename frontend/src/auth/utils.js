import useJwt from '@/auth/jwt/useJwt'

/**
 * Return if user is logged in
 * This is completely up to you and how you want to store the token in your frontend application
 * e.g. If you are using cookies to store the application please update this function
 */
// eslint-disable-next-line arrow-body-style
export const isUserLoggedIn = () => {
  return localStorage.getItem('userData') && localStorage.getItem(useJwt.jwtConfig.storageTokenKeyName)
}

export const getUserData = () => JSON.parse(localStorage.getItem('userData'))

export const getUserProfile = () => JSON.parse(localStorage.getItem('userProfile'))

/**
 * This function is used for demo purpose route navigation
 * In real app you won't need this function because your app will navigate to same route for each users regardless of ability
 * Please note role field is just for showing purpose it's not used by anything in frontend
 * We are checking role just for ease
 * NOTE: If you have different pages to navigate based on user ability then this function can be useful. However, you need to update it.
 * @param {String} userRole Role of user
 */
export const getHomeRouteForLoggedInUser = userRole => {
  if (userRole === 'SuperAdmin') return { name: 'rt-personal-fichas' }
  return { name: 'auth-login' }
}

export const getAbilityByRol = userRole => {
  const abilitys = [
    {
      action: 'manage',
      subject: 'Auth',
    },
    {
      action: 'manage',
      subject: 'rt-home',
    },
  ]
  if (userRole === 'UserTopico') {
    abilitys.push({
      action: 'manage',
      subject: 'rt-topico',
    })
    abilitys.push({
      action: 'manage',
      subject: 'rt-topico-historiaclinica',
    })
    abilitys.push({
      action: 'manage',
      subject: 'rt-topico-gestionhistoria',
    })
    abilitys.push({
      action: 'manage',
      subject: 'rt-topico-seleccionpersonal',
    })
    abilitys.push({
      action: 'manage',
      subject: 'rt-topico-gestionpersonal',
    })

    return abilitys
  }
  if (userRole === 'Coordinador Reclutamiento' || userRole === 'Reclutador' || userRole === 'Asistente Reclutamiento' || userRole === 'Seguridad' || userRole === 'Induccion RRHH' || userRole === 'Induccion Serv. Salud' || userRole === 'Induccion B. Social') {
    abilitys.push({
      action: 'manage',
      subject: 'rt-reclutamiento',
    })
    if (userRole === 'Coordinador Reclutamiento' || userRole === 'Reclutador' || userRole === 'Asistente Reclutamiento') {
      abilitys.push({
        action: 'manage',
        subject: 'rt-reclutamiento-fichas',
      })
    }
    if (userRole === 'Coordinador Reclutamiento' || userRole === 'Seguridad' || userRole === 'Induccion RRHH' || userRole === 'Induccion Serv. Salud' || userRole === 'Induccion B. Social') {
      abilitys.push({
        action: 'manage',
        subject: 'rt-reclutamiento-control',
      })
    }
    return abilitys
  }
  if (userRole === 'SuperAdmin') {
    return [
      {
        action: 'manage',
        subject: 'all',
      },
    ]
  }
  return [
    {
      action: 'read',
      subject: 'Auth',
    },
  ]
}
