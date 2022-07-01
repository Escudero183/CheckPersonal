<template>
  <div>
    <b-modal
      v-model="modalShow"
      title="Seleccione Perfil"
      ok-title="Seleccionar"
      cancel-variant="outline-secondary"
      no-close-on-backdrop
      no-close-on-esc
      size="xl"
      :hide-footer="true"
      :busy="isBusyModal"
    >
      <b-overlay
        :show="isBusyModal"
        rounded="sm"
      >
        <b-row class="match-height">
          <b-col
            v-for="(sistema, index) in sistemas"
            :key="index"
            xl="4"
            md="4"
            sm="6"
          >
            <b-card class="text-center">
              <b-avatar
                class="mb-1"
                variant="light-primary"
                size="45"
              >
                <feather-icon
                  size="21"
                  :icon="sistema.sistema.icono"
                />
              </b-avatar>
              <div class="truncate">
                <h3 class="mb-25 font-weight-bolder">
                  {{ sistema.perfil.nombre }}
                </h3>
                <span class="d-block">{{ sistema.sistema.nombre }}</span>
                <b-button
                  v-ripple.400="'rgba(255, 255, 255, 0.15)'"
                  variant="outline-primary"
                  class="mt-2"
                  @click="selectedPerfil(index)"
                >
                  Seleccionar
                </b-button>
              </div>
            </b-card>
          </b-col>
        </b-row>
      </b-overlay>
    </b-modal>
  </div>
</template>

<script>
import { BButton, BCard, BAvatar, BRow, BCol, BOverlay } from 'bootstrap-vue'
import Ripple from 'vue-ripple-directive'
import useJwt from '@/auth/jwt/useJwt'
import { getHomeRouteForLoggedInUser, getAbilityByRol } from '@/auth/utils'

import ToastificationContent from '@core/components/toastification/ToastificationContent.vue'

export default {
  components: {
    BButton,
    BCard,
    BAvatar,
    BRow,
    BCol,
    BOverlay,
  },
  directives: {
    Ripple,
  },
  props: {
    isBusyModal: {
      type: Boolean,
      required: true,
    },
    modalShow: {
      type: Boolean,
      required: true,
    },
    sistemas: {
      type: Array,
      required: true,
    },
    token: {
      type: String,
      required: true,
    },
    userData: {
      type: Object,
      required: true,
    },
  },
  methods: {
    selectedPerfil(index) {
      useJwt.setToken(this.token)
      useJwt.setRefreshToken(this.token)
      this.userData.ability = getAbilityByRol(this.sistemas[index].perfil.nombre)
      this.userData.empresa = { idEmpresa: 18 }
      localStorage.setItem('userData', JSON.stringify(this.userData))
      localStorage.setItem('userProfile', JSON.stringify(this.sistemas[index].perfil))
      // localStorage.setItem('empresa', JSON.stringify({ idEmpresa: 3 }))
      // console.log('empresa >>>', JSON.parse(localStorage.getItem('empresa')))
      this.$ability.update(this.userData.ability)

      // ? This is just for demo purpose as well.
      // ? Because we are showing eCommerce app's cart items count in navbar
      this.$store.commit('app-ecommerce/UPDATE_CART_ITEMS_COUNT', 5)

      this.$router.push(getHomeRouteForLoggedInUser(this.sistemas[index].perfil.nombre))
        .then(() => {
          this.$toast({
            component: ToastificationContent,
            position: 'top-right',
            props: {
              title: `Bienvenido ${this.userData.nombreCompleto}`,
              icon: 'CoffeeIcon',
              variant: 'success',
              text: `Has iniciado sesión con éxito como ${this.sistemas[index].perfil.nombre}. Ahora puedes empezar a trabajar!`,
            },
          })
        })
        .catch(error => {
          this.$refs.loginForm.setErrors(error.response.data.error)
        })
    },
  },
}
</script>
