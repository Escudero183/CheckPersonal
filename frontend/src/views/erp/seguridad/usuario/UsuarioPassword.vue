<template>
  <b-sidebar
    id="edit-password-sidebar"
    :visible="isOpenEditPassword"
    bg-variant="white"
    sidebar-class="sidebar-lg"
    shadow
    backdrop
    no-header
    right
    @hidden="resetForm"
    @change="(val) => toggleSidebar(val)"
  >
    <template #default="{ hide }">
      <!-- Header -->
      <div class="d-flex justify-content-between align-items-center content-sidebar-header px-2 py-1">
        <h5 class="mb-0">
          Actualizar Contraseña
        </h5>

        <feather-icon
          class="ml-1 cursor-pointer"
          icon="XIcon"
          size="16"
          @click="hide"
        />

      </div>

      <!-- BODY -->
      <validation-observer
        #default="{ handleSubmit }"
        ref="refFormObserver"
      >
        <!-- Form -->
        <b-form
          class="p-2"
          @submit.prevent="handleSubmit(onSubmit)"
          @reset.prevent="resetForm"
        >

          <validation-provider
            #default="validationContext"
            name="Login"
            rules="required"
          >
            <b-form-group
              label="Login"
              label-for="login-edit-password"
            >
              <b-form-input
                id="login-edit-password"
                v-model="formData.login"
                autofocus
                :state="getValidationState(validationContext)"
                trim
                placeholder=""
                disabled
              />

              <b-form-invalid-feedback>
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
            #default="validationContext"
            name="Contraseña"
            rules="required"
          >
            <b-form-group
              label="Contraseña"
              label-for="password"
            >
              <b-form-input
                id="password"
                v-model="formData.password"
                :state="getValidationState(validationContext)"
                trim
                type="password"
              />

              <b-form-invalid-feedback>
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
            #default="validationContext"
            name="Repetir Contraseña"
            rules="required"
          >
            <b-form-group
              label="Repetir Contraseña"
              label-for="repassword"
            >
              <b-form-input
                id="repassword"
                v-model="formData.repassword"
                :state="getValidationState(validationContext)"
                trim
                type="password"
              />

              <b-form-invalid-feedback>
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>
          <!-- Form Actions -->
          <div class="d-flex mt-2">
            <b-button
              v-ripple.400="'rgba(255, 255, 255, 0.15)'"
              variant="primary"
              class="mr-2"
              type="submit"
            >
              Guardar
            </b-button>
            <b-button
              v-ripple.400="'rgba(186, 191, 199, 0.15)'"
              type="button"
              variant="outline-secondary"
              @click="hide"
            >
              Cancelar
            </b-button>
          </div>

        </b-form>
      </validation-observer>
    </template>
  </b-sidebar>
</template>

<script>
import {
  BSidebar, BForm, BFormGroup, BFormInput, BFormInvalidFeedback, BButton,
} from 'bootstrap-vue'
import { ValidationProvider, ValidationObserver } from 'vee-validate'
import { ref } from '@vue/composition-api'
import { required } from '@validations'
import formValidation from '@core/comp-functions/forms/form-validation'
import Ripple from 'vue-ripple-directive'
import store from '@/store'

export default {
  components: {
    BSidebar,
    BForm,
    BFormGroup,
    BFormInput,
    BFormInvalidFeedback,
    BButton,

    // Form Validation
    ValidationProvider,
    ValidationObserver,
  },
  directives: {
    Ripple,
  },
  model: {
    prop: 'isOpenEditPassword',
    event: 'update:is-open-edit-password',
  },
  props: {
    isOpenEditPassword: {
      type: Boolean,
      required: true,
    },
    dataEdit: {
      type: Object,
      required: false,
      default: null,
    },
  },
  data() {
    return {
      required,
    }
  },
  setup(props, { emit }) {
    const blankData = {
      login: '',
      password: '',
      repassword: '',
    }

    const formData = ref(JSON.parse(JSON.stringify(blankData)))
    const resetData = () => {
      formData.value = JSON.parse(JSON.stringify(blankData))
    }

    const onSubmit = () => {
      const service = 'seguridad/USUARIO_RESET_PASSWORD'
      store.dispatch(service, formData.value)
        .then(response => {
          emit('refetch-data', response)
          emit('update:is-open-edit-password', false)
        })
        .catch(error => {
          emit('error-data', error)
          emit('update:is-open-edit-password', false)
        })
    }

    const toggleSidebar = val => {
      emit('update:is-open-edit-password', val)
      formData.value = {
        idUsuario: props.dataEdit.idUsuario,
        nombres: props.dataEdit.nombres,
        apellidos: props.dataEdit.apellidos,
        email: props.dataEdit.email,
        login: props.dataEdit.login,
        password: '',
        estado: props.dataEdit.estado,
      }
    }

    const {
      refFormObserver,
      getValidationState,
      resetForm,
    } = formValidation(resetData)

    return {
      formData,
      onSubmit,
      refFormObserver,
      getValidationState,
      resetForm,
      toggleSidebar,
    }
  },
}
</script>

<style lang="scss">
@import '@core/scss/vue/libs/vue-select.scss';

#add-new-user-sidebar {
  .vs__dropdown-menu {
    max-height: 200px !important;
  }
}
</style>
