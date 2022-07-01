export default [
  {
    path: '/app/personal/gestion-fichas',
    name: 'rt-personal-fichas',
    component: () => import('@/views/erp/personal/FichaPersonal.vue'),
    meta: {
      resource: 'rt-personal-fichas',
      contentRenderer: 'sidebar-left-detached',
      contentClass: 'ecommerce-application',
      pageTitle: 'Gestión de Fichas',
      breadcrumb: [
        {
          text: 'Personal',
        },
        {
          text: 'Gestión de Fichas',
          active: true,
        },
      ],
    },
  },
  {
    path: '/public/profile/:dni',
    name: 'rt-public-profile',
    component: () => import('@/views/erp/profile/Profile.vue'),
    meta: {
      pageTitle: 'Profile',
      resource: 'Auth',
      action: 'read',
      breadcrumb: [
        {
          text: 'Pages',
        },
        {
          text: 'Profile',
          active: true,
        },
      ],
    },
  },
]
