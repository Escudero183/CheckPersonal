export default [
  {
    path: '/app/seguridad/sistemas',
    name: 'rt-seguridad-sistemas',
    component: () => import('@/views/erp/seguridad/sistema/Sistema.vue'),
    meta: {
      resource: 'rt-seguridad-sistemas',
      pageTitle: 'Sistemas / Módulos / Perfiles',
      breadcrumb: [
        {
          text: 'Seguridad',
        },
        {
          text: 'Sistemas',
          active: true,
        },
      ],
    },
  },
  {
    path: '/app/seguridad/usuarios',
    name: 'rt-seguridad-usuarios',
    component: () => import('@/views/erp/seguridad/usuario/UsuarioList.vue'),
    meta: {
      resource: 'rt-seguridad-usuarios',
      pageTitle: 'Usuarios',
      breadcrumb: [
        {
          text: 'Seguridad',
        },
        {
          text: 'Usuarios',
          active: true,
        },
      ],
    },
  },
]
