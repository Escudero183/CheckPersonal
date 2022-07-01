<template>
  <div class="misc-wrapper">
    <b-link class="brand-logo">
      <b-img
        :src="appLogoImage"
        alt="logo"
        height="100"
      />
    </b-link>

    <div class="misc-inner p-2 p-sm-3">
      <div class="w-100 text-center">
        <h2 class="mb-1">
          No estás autorizado! 🔐
        </h2>
        <p class="mb-2">
          No tienes permiso para acceder a esta página. Ir al Inicio!!
        </p>
        <b-button
          variant="primary"
          class="mb-1 btn-sm-block"
          :to="loginRoute()"
        >Regresar al Inicio</b-button>
        <b-img
          fluid
          :src="imgUrl"
          alt="Not authorized page"
        />
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable global-require */
import { BLink, BImg, BButton } from 'bootstrap-vue'
import store from '@/store/index'

export default {
  components: {
    BLink, BImg, BButton,
  },
  data() {
    return {
      downImg: require('@/assets/images/pages/not-authorized.svg'),
      appLogoImage: require('@/assets/images/erp/logo-empresa.png'),
    }
  },
  computed: {
    imgUrl() {
      if (store.state.appConfig.layout.skin === 'dark') {
        // eslint-disable-next-line vue/no-side-effects-in-computed-properties
        this.downImg = require('@/assets/images/pages/not-authorized-dark.svg')
        return this.downImg
      }
      return this.downImg
    },
  },
  methods: {
    loginRoute() {
      return '/'
    },
  },
}
</script>

<style lang="scss">
@import '@core/scss/vue/pages/page-misc.scss';
</style>
