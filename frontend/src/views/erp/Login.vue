<template>
  <div class="auth-wrapper auth-v1 px-2 bg-login">
    <div class="overlay" />
    <div class="auth-inner py-2">

      <!-- Login v1 -->
      <b-card class="mb-0">
        <b-link class="brand-logo mb-1 mt-0">
          <b-img
            :src="require('@/assets/images/erp/logo-empresa.png')"
            width="350"
          />
        </b-link>

        <b-card-title class="mb-0">
          <center>Bienvenido, ingrese sus credenciales!</center>
        </b-card-title>

        <!-- form -->
        <validation-observer
          ref="loginForm"
          #default="{invalid}"
        >
          <b-form
            class="auth-login-form mt-2"
            @submit.prevent="login"
          >

            <!-- email -->
            <b-form-group
              label-for="usuario"
              label="Usuario"
            >
              <validation-provider
                #default="{ errors }"
                name="Usuario"
                rules="required"
              >
                <b-form-input
                  id="usuario"
                  v-model="userEmail"
                  name="login-usuario"
                  :state="errors.length > 0 ? false:null"
                  placeholder="Usuario"
                  autofocus
                />
                <small class="text-danger">{{ errors[0] }}</small>
              </validation-provider>
            </b-form-group>

            <!-- password -->
            <b-form-group>
              <div class="d-flex justify-content-between">
                <label for="password">Password</label>
                <b-link :to="{name:'auth-forgot-password-v1'}">
                  <small>Forgot Password?</small>
                </b-link>
              </div>
              <validation-provider
                #default="{ errors }"
                name="Password"
                rules="required"
              >
                <b-input-group
                  class="input-group-merge"
                  :class="errors.length > 0 ? 'is-invalid':null"
                >
                  <b-form-input
                    id="password"
                    v-model="password"
                    :type="passwordFieldType"
                    class="form-control-merge"
                    :state="errors.length > 0 ? false:null"
                    name="login-password"
                    placeholder="Password"
                  />

                  <b-input-group-append is-text>
                    <feather-icon
                      class="cursor-pointer"
                      :icon="passwordToggleIcon"
                      @click="togglePasswordVisibility"
                    />
                  </b-input-group-append>
                </b-input-group>
                <small class="text-danger">{{ errors[0] }}</small>
              </validation-provider>
            </b-form-group>

            <!-- checkbox -->
            <b-form-group>
              <b-form-checkbox
                id="remember-me"
                v-model="status"
                name="checkbox-1"
              >
                Recordarme
              </b-form-checkbox>
            </b-form-group>

            <!-- submit button -->
            <b-button
              variant="primary"
              type="submit"
              block
              :disabled="invalid"
            >
              Ingresar
            </b-button>
          </b-form>
        </validation-observer>
      </b-card>
      <!-- /Login v1 -->
    </div>
    <!-- Form Selected Sistemas -->
    <perfil-select
      :is-busy-modal.sync="isBusyModal"
      :modal-show.sync="modalShow"
      :sistemas.sync="sistemas"
      :token.sync="token"
      :user-data.sync="userData"
    />
  </div>
</template>

<script>
/* eslint-disable global-require */
import { ValidationProvider, ValidationObserver } from 'vee-validate'
import {
  BCard, BLink, BFormGroup, BFormInput, BInputGroupAppend, BInputGroup, BFormCheckbox, BCardTitle, BImg, BForm, BButton, VBTooltip,
} from 'bootstrap-vue'
import useJwt from '@/auth/jwt/useJwt'
import { required, email } from '@validations'
import store from '@/store/index'
import { togglePasswordVisibility } from '@core/mixins/ui/forms'
import { getHomeRouteForLoggedInUser } from '@/auth/utils'

import ToastificationContent from '@core/components/toastification/ToastificationContent.vue'
import PerfilSelect from './seguridad/perfil/PerfilSelect.vue'

export default {
  directives: {
    'b-tooltip': VBTooltip,
  },
  components: {
    BCard,
    BLink,
    BFormGroup,
    BFormInput,
    BInputGroupAppend,
    BInputGroup,
    BFormCheckbox,
    BCardTitle,
    BImg,
    BForm,
    BButton,
    ValidationProvider,
    ValidationObserver,
    PerfilSelect,
  },
  mixins: [togglePasswordVisibility],
  data() {
    return {
      status: '',
      password: '',
      userEmail: '',
      sideImg: require('@/assets/images/pages/login-v2.svg'),

      // validation rules
      required,
      email,
      sistemaSel: null,
      modalShow: false,
      isBusyModal: false,
      sistemas: [],
      token: '',
      userData: {},
    }
  },
  computed: {
    passwordToggleIcon() {
      return this.passwordFieldType === 'password' ? 'EyeIcon' : 'EyeOffIcon'
    },
    imgUrl() {
      return this.sideImg
    },
  },
  methods: {
    login() {
      store.dispatch('auth/AUTH_CREATE_TOKEN', {
        username: this.userEmail,
        password: this.password,
      })
        .then(response => {
          if (response.sistemas) {
            this.modalShow = true
            this.token = response.token
            this.userData = response.user
            this.sistemas = response.sistemas
          } else {
            this.$toast({
              component: ToastificationContent,
              position: 'top-right',
              props: {
                title: 'Acceso no Autorizado',
                icon: 'LockIcon',
                variant: 'danger',
                text: 'Lo sentimos no tienes ningún perfil asignado para acceder a la plataforma.',
              },
            })
          }

          if (this.sistemaSel != null) {
            const { user } = response
            useJwt.setToken(response.token)
            useJwt.setRefreshToken(response.token)
            localStorage.setItem('userData', JSON.stringify(user))
            this.$ability.update([{ action: 'manage', subject: 'all' }])

            // ? This is just for demo purpose as well.
            // ? Because we are showing eCommerce app's cart items count in navbar
            this.$store.commit('app-ecommerce/UPDATE_CART_ITEMS_COUNT', 5)

            this.$router.push(getHomeRouteForLoggedInUser('admin'))
              .then(() => {
                this.$toast({
                  component: ToastificationContent,
                  position: 'top-right',
                  props: {
                    title: `Welcome ${user.nombreCompleto}`,
                    icon: 'CoffeeIcon',
                    variant: 'success',
                    text: `You have successfully logged in as ${user.role}. Now you can start to explore!`,
                  },
                })
              })
              .catch(error => {
                this.$refs.loginForm.setErrors(error.response.data.error)
              })
          }
        })
        .catch(error => {
          let errorMessage = 'Intentelo en otro momento.'
          if (error.response) {
            errorMessage = error.response.data.errorMessage
          }
          this.$toast({
            component: ToastificationContent,
            position: 'top-right',
            props: {
              title: 'Ha ocurrido un error',
              icon: 'AlertOctagonIcon',
              variant: 'danger',
              text: errorMessage,
            },
          })
        })
    },
  },
}
</script>

<style lang="scss">
@import '@core/scss/vue/pages/page-auth.scss';
.bg-login{
  background-image: url('~@/assets/images/erp/bglogin.jpg');
  background-repeat: no-repeat;
  background-position: bottom center;
}
.overlay{
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 10;
  background-color: rgba(255,255,255,0.8); /*dim the background*/
}
.auth-inner{
  z-index: 100;
}
</style>
