<template>
  <b-sidebar
    id="add-new-user-sidebar"
    :visible="isAddEditSistemaSidebar"
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
            name="Path Api"
            rules="required"
          >
            <b-form-group
              label="Path Api"
              label-for="pathApi"
            >
              <b-form-input
                id="pathApi"
                v-model="formData.pathApi"
                :state="getValidationState(validationContext)"
                trim
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
              label-for="icono"
            >
              <b-form-input
                id="icono"
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
    prop: 'isAddEditSistemaSidebar',
    event: 'update:is-add-edit-sistema-sidebar',
  },
  props: {
    isAddEditSistemaSidebar: {
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
      nombre: '',
      pathApi: '',
      icono: '',
    }

    const formData = ref(JSON.parse(JSON.stringify(blankData)))
    const resetData = () => {
      formData.value = JSON.parse(JSON.stringify(blankData))
    }

    const onSubmit = () => {
      let service = 'seguridad/SISTEMA_CREATE'
      if (props.formType === 'edit') {
        service = 'seguridad/SISTEMA_UPDATE'
      }
      store.dispatch(service, formData.value)
        .then(response => {
          emit('refetch-data', response)
          emit('update:is-add-edit-sistema-sidebar', false)
        })
        .catch(error => {
          emit('error-data', error)
          emit('update:is-add-edit-sistema-sidebar', false)
        })
    }

    const toggleSidebar = val => {
      emit('update:is-add-edit-sistema-sidebar', val)
      if (props.formType === 'new') {
        titleForm.value = 'Agregar Sistema'
        resetData()
      }
      if (props.formType === 'edit') {
        formData.value = {
          idSistema: props.dataEdit.idSistema,
          nombre: props.dataEdit.nombre,
          pathApi: props.dataEdit.pathApi,
          icono: props.dataEdit.icono,
          estado: props.dataEdit.estado,
        }
        titleForm.value = 'Editar Sistema'
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
