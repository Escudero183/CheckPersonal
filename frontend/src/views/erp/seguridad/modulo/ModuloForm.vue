<template>
  <b-sidebar
    id="add-new-sidebar"
    :visible="isAddNewSidebarActive"
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

          <validation-provider
            #default="validationContext"
            name="Nombre"
            rules="required"
          >
            <b-form-group
              label="Nombre Módulo"
              label-for="nombre_modulo"
            >
              <b-form-input
                id="nombre_modulo"
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
            name="Abreviatura"
            rules="required"
          >
            <b-form-group
              label="Abreviatura"
              label-for="abreviatura"
            >
              <b-form-input
                id="abreviatura"
                v-model="formData.abreviatura"
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
            name="ModuloPadre"
            rules="required"
          >
            <b-form-group
              label="Depende de?"
              label-for="moduloPadre"
              :state="getValidationState(validationContext)"
            >
              <v-select
                v-model="formData.moduloPadre"
                placeholder=".: Seleccione Módulo Padre :."
                :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
                :options="padres"
                :reduce="m => m.idModulo"
                label="nombre"
                :clearable="false"
                input-id="moduloPadre"
              />
              <b-form-invalid-feedback :state="getValidationState(validationContext)">
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
            #default="validationContext"
            name="Url"
            rules="required"
          >
            <b-form-group
              label="Url"
              label-for="url"
            >
              <b-form-input
                id="url"
                v-model="formData.url"
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
              label-for="icono_modulo"
            >
              <b-form-input
                id="icono_modulo"
                v-model="formData.icono"
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
            name="Orden"
            rules="required"
          >
            <b-form-group
              label="Orden"
              label-for="nroOrden"
            >
              <b-form-input
                id="nroOrden"
                v-model="formData.nroOrden"
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
import vSelect from 'vue-select'
import store from '@/store'

export default {
  components: {
    BSidebar,
    BForm,
    BFormGroup,
    BFormInput,
    BFormInvalidFeedback,
    BButton,
    vSelect,

    // Form Validation
    ValidationProvider,
    ValidationObserver,
  },
  directives: {
    Ripple,
  },
  model: {
    prop: 'isAddNewSidebarActive',
    event: 'update:is-add-new-sidebar-active',
  },
  props: {
    isAddNewSidebarActive: {
      type: Boolean,
      required: true,
    },
    dataEdit: {
      type: Object,
      required: false,
      default: null,
    },
    dataSistema: {
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
    const padres = ref([])
    const blankData = {
      nombre: '',
      descripcion: '',
      icono: '',
      abreviatura: '',
      moduloPadre: 0,
      url: '',
      esquema: '',
      nroOrden: 0,
    }

    const formData = ref(JSON.parse(JSON.stringify(blankData)))
    const resetData = () => {
      formData.value = JSON.parse(JSON.stringify(blankData))
    }

    const onSubmit = () => {
      let service = 'seguridad/MODULO_CREATE'
      if (props.formType === 'edit') {
        service = 'seguridad/MODULO_UPDATE'
      }
      formData.value.sistema = props.dataSistema
      store.dispatch(service, formData.value)
        .then(response => {
          emit('refetch-data', response)
          emit('update:is-add-new-sidebar-active', false)
        })
        .catch(error => {
          emit('error-data', error)
          emit('update:is-add-new-sidebar-active', false)
        })
    }

    const loadPadres = () => {
      const moduleDefault = ref({
        idModulo: 0,
        nombre: 'Ninguno',
      })
      padres.value.push(moduleDefault.value)
      store
        .dispatch('seguridad/MODULO_FIND_ALL', {
          tipo: 'cmb',
          idSistema: props.dataSistema.idSistema,
          limit: -1,
          query: '',
          page: -1,
          sortBy: 'idModulo%7CDESC',
        })
        .then(response => {
          if (response !== undefined) {
            padres.value = response
            padres.value.unshift(moduleDefault.value)
          }
        })
        .catch(error => {
          emit('error-data', error)
        })
    }

    const toggleSidebar = val => {
      loadPadres()
      emit('update:is-add-new-sidebar-active', val)
      if (props.formType === 'new') {
        titleForm.value = 'Agregar Módulo'
      }
      if (props.formType === 'edit') {
        formData.value = {
          idModulo: props.dataEdit.idModulo,
          nombre: props.dataEdit.nombre,
          abreviatura: props.dataEdit.abreviatura,
          url: props.dataEdit.url,
          sistema: props.dataEdit.sistema,
          icono: props.dataEdit.icono,
          moduloPadre: props.dataEdit.moduloPadre,
          nroOrden: props.dataEdit.nroOrden,
          estado: props.dataEdit.estado,
        }
        titleForm.value = 'Editar Módulo'
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
      loadPadres,
      padres,
      toggleSidebar,
      titleForm,
    }
  },
}
</script>

<style lang="scss">
@import '@core/scss/vue/libs/vue-select.scss';

#add-new-sidebar {
  .vs__dropdown-menu {
    max-height: 200px !important;
  }
}
</style>
