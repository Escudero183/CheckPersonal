<template>
  <b-sidebar
    id="new-edit-sidebar"
    :visible="isNewEditSidebarActive"
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
            label="Sistema"
            label-for="sistema"
          >
            <b-form-input
              id="sistema"
              v-model="dataSistema.nombre"
              disabled
            />
          </b-form-group>

          <!-- Nombre -->
          <validation-provider
            #default="validationContext"
            name="Nombre"
            rules="required"
          >
            <b-form-group
              label="Nombre"
              label-for="nombre"
            >
              <b-form-input
                id="nombre"
                v-model="formData.nombre"
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
            name="Icono"
            rules="required"
          >
            <b-form-group
              label="Icono"
              label-for="icono_perfil"
            >
              <b-form-input
                id="icono_perfil"
                v-model="formData.icono"
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
    prop: 'isNewEditSidebarActive',
    event: 'update:is-new-edit-sidebar-active',
  },
  props: {
    isNewEditSidebarActive: {
      type: Boolean,
      required: true,
    },
    dataSistema: {
      type: Object,
      required: false,
      default: null,
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
      nombre: '',
      icono: '',
    }

    const formData = ref(JSON.parse(JSON.stringify(blankData)))
    const resetData = () => {
      formData.value = JSON.parse(JSON.stringify(blankData))
    }

    const onSubmit = () => {
      let service = 'seguridad/PERFIL_CREATE'
      if (props.formType === 'edit') {
        service = 'seguridad/PERFIL_UPDATE'
      }
      formData.value.sistema = props.dataSistema
      store.dispatch(service, formData.value)
        .then(response => {
          emit('refetch-data', response)
          emit('update:is-new-edit-sidebar-active', false)
        })
        .catch(error => {
          emit('error-data', error)
          emit('update:is-new-edit-sidebar-active', false)
        })
    }

    const toggleSidebar = val => {
      emit('update:is-new-edit-sidebar-active', val)
      if (props.formType === 'new') {
        titleForm.value = 'Agregar Perfil'
      }
      if (props.formType === 'edit') {
        formData.value = {
          idPerfil: props.dataEdit.idPerfil,
          nombre: props.dataEdit.nombre,
          sistema: props.dataEdit.sistema,
          icono: props.dataEdit.icono,
          estado: props.dataEdit.estado,
        }
        titleForm.value = 'Editar Perfil'
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
