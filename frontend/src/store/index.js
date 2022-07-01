import Vue from 'vue'
import Vuex from 'vuex'

// Modules
import ecommerceStoreModule from '@/views/apps/e-commerce/eCommerceStoreModule'
import app from './app'
import appConfig from './app-config'
import verticalMenu from './vertical-menu'
import Auth from './modules/auth/index'
import Personal from './modules/personal'
import Public from './modules/public'
import Catalogo from './modules/catalogo'
import Seguridad from './modules/seguridad'
import Helpers from './modules/helpers'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    app,
    appConfig,
    verticalMenu,
    'app-ecommerce': ecommerceStoreModule,
    auth: Auth,
    personal: Personal,
    public: Public,
    catalogo: Catalogo,
    seguridad: Seguridad,
    helpers: Helpers,
  },
  strict: process.env.DEV,
})
