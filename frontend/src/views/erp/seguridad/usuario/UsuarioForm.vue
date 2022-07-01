<template>
  <b-sidebar
    id="add-new-user-sidebar"
    :visible="isAddNewUserSidebarActive"
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
          {{ titleForm }}
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
          <b-form-group
            label="Trabajador"
            label-for="trabajador"
          >
            <v-select
              id="trabajador"
              v-model="trabajadorSel"
              label="nombreCompleto"
              :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
              :options="trabajadores"
            />
          </b-form-group>

          <validation-provider
            #default="validationContext"
            name="Login"
            rules="required"
          >
            <b-form-group
              label="Login"
              label-for="login"
            >
              <b-form-input
                id="login"
                v-model="formData.login"
                autofocus
                :state="getValidationState(validationContext)"
                trim
                placeholder=""
              />

              <b-form-invalid-feedback>
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
            #default="validationContext"
            name="Email"
            rules="required|email"
          >
            <b-form-group
              label="Email"
              label-for="email"
            >
              <b-form-input
                id="email"
                v-model="formData.email"
                :state="getValidationState(validationContext)"
                trim
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
import vSelect from 'vue-select'
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
    vSelect,
  },
  directives: {
    Ripple,
  },
  model: {
    prop: 'isAddNewUserSidebarActive',
    event: 'update:is-add-new-user-sidebar-active',
  },
  props: {
    isAddNewUserSidebarActive: {
      type: Boolean,
      required: true,
    },
    dataEdit: {
      type: Object,
      required: false,
      default: null,
    },
    formType: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      required,
    }
  },
  setup(props, { emit }) {
    const titleForm = ref('')
    const blankData = {
      nombres: '',
      apellidos: '',
      email: '',
      login: '',
    }
    const trabajadores = ref([])
    const trabajadorSel = ref({})

    const formData = ref(JSON.parse(JSON.stringify(blankData)))
    const resetData = () => {
      formData.value = JSON.parse(JSON.stringify(blankData))
    }

    const onSubmit = () => {
      // Validar datos antes
      if (trabajadorSel.value === null) {
        emit('error-data', { response: { data: { message: 'Debe seleccionar Trabajador' } } })
        return
      }
      formData.value.trabajador = trabajadorSel.value
      let service = 'seguridad/USUARIO_CREATE'
      if (props.formType === 'edit') {
        service = 'seguridad/USUARIO_UPDATE'
      }
      store.dispatch(service, formData.value)
        .then(response => {
          emit('refetch-data', response)
          emit('update:is-add-new-user-sidebar-active', false)
        })
        .catch(error => {
          emit('error-data', error)
          emit('update:is-add-new-user-sidebar-active', false)
        })
    }

    const getTrabajadores = async () => {
      await store.dispatch('rrhh/TRABAJADOR_FIND_ALL', {
        query: '',
        page: -1,
        limit: -1,
        tipo: 'cmb',
        sortBy: 'nombre%7CASC',
        cargo: '',
      })
        .then(response => {
          if (response) {
            trabajadores.value = response
          }
        })
    }

    const getNombre = item => {
      const nombreFull = `${item.nombre} ${item.apellidoPaterno} ${item.apellidoMaterno}`
      return nombreFull
    }

    const toggleSidebar = val => {
      emit('update:is-add-new-user-sidebar-active', val)
      getTrabajadores()
      if (props.formType === 'new') {
        titleForm.value = 'Agregar Usuario'
        resetData()
      }
      if (props.formType === 'edit') {
        formData.value = {
          idUsuario: props.dataEdit.idUsuario,
          email: props.dataEdit.email,
          login: props.dataEdit.login,
          password: props.dataEdit.login,
          estado: props.dataEdit.estado,
        }
        trabajadorSel.value = props.dataEdit.trabajador
        titleForm.value = 'Editar Usuario'
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
      titleForm,
      trabajadores,
      trabajadorSel,
      getTrabajadores,
      getNombre,
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
