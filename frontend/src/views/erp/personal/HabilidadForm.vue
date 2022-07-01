<template>
  <b-sidebar
    id="manage-hability-sidebar"
    :visible="isToggleHabiSidebar"
    bg-variant="white"
    sidebar-class="sidebar-lg"
    shadow
    backdrop
    no-close-on-backdrop
    no-header
    right
    @hidden="resetForm"
    @change="(val) => toggleSidebar(val)"
  >
    <template #default="{ hide }">
      <!-- Header -->
      <div class="d-flex justify-content-between align-items-center content-sidebar-header px-2 py-1">
        <h5 class="mb-0">
          Gestionar Habilidades: {{ dataEdit.nombres }}
        </h5>

        <feather-icon
          class="ml-1 cursor-pointer"
          icon="XIcon"
          size="16"
          @click="hide"
        />

      </div>

      <!-- Form -->
      <b-form
        class="p-2"
        @reset.prevent="resetForm"
      >
        <div
          v-if="isNewRegister"
        >
          <h6
            class="text-info"
          >
            <feather-icon icon="FileTextIcon" />
            <span class="align-middle ml-25">Formulario</span>
          </h6>

          <hr style="border-top: 2px solid #00cfe8;margin-bottom: 1rem !important;margin-top: 0 !important;">

          <div
            v-if="isBusy"
            class="text-center text-primary my-2"
          >
            <b-spinner
              variant="primary"
              class="align-middle"
            />
            <strong class="ml-1">Cargando...</strong>
          </div>

          <b-row>
            <b-col
              cols="12"
              md="12"
            >
              <b-form-group
                label="Habilidad"
                label-for="habilidad"
              >
                <b-form-input
                  id="habilidad"
                  v-model="formData.habilidad"
                />
              </b-form-group>
            </b-col>
            <b-col
              cols="12"
              md="12"
            >
              <b-form-group
                label="Nivel"
                label-for="nivel"
              >
                <b-form-input
                  id="nivel"
                  v-model="formData.nivel"
                  type="number"
                />
              </b-form-group>
            </b-col>
          </b-row>

          <div class="d-flex">
            <b-button
              v-ripple.400="'rgba(255, 255, 255, 0.15)'"
              variant="primary"
              class="mr-50"
              type="button"
              @click="saveForm"
            >
              Guardar
            </b-button>
            <b-button
              v-ripple.400="'rgba(186, 191, 199, 0.15)'"
              type="button"
              variant="outline-secondary"
              @click="cancelForm"
            >
              Cancelar
            </b-button>
          </div>
        </div>

        <div
          v-if="!isNewRegister"
        >
          <h6
            class="text-info"
          >
            <feather-icon icon="ListIcon" />
            <span class="align-middle ml-25">Lista de Habilidades</span>
            <b-button
              v-ripple.400="'rgba(255, 255, 255, 0.15)'"
              variant="primary"
              class="float-right"
              type="button"
              size="sm"
              style="margin-top: -5px;"
              @click="newItem"
            >
              Nuevo
            </b-button>
          </h6>

          <hr style="border-top: 2px solid #00cfe8;margin-bottom: 1rem !important;margin-top: 0 !important;">

          <b-table
            hover
            responsive
            :items="habilidades.items"
            :fields="fields"
            show-empty
            empty-text="No se encontraron registros"
            :busy="isBusyLista"
          >
            <template #table-busy>
              <div class="text-center text-primary my-2">
                <b-spinner
                  variant="primary"
                  class="align-middle"
                />
                <strong class="ml-1">Cargando...</strong>
              </div>
            </template>
            <template #cell(habilidad)="data">
              <small class="text-muted d-block ">{{ data.item.habilidad }}</small>
            </template>
            <template #cell(nivel)="data">
              <div class="align-items-center">
                <small class="text-muted d-block mb-0">
                  {{ data.item.nivel }}%
                </small>
                <b-progress
                  :value="data.item.nivel"
                  max="100"
                  height="6px"
                  :class="`progress-bar-${resolveColor(data.item.nivel)}`"
                  :variant="resolveColor(data.item.nivel)"
                />
              </div>
            </template>
            <!-- Column: Actions -->
            <template #cell(acciones)="data">
              <b-dropdown
                variant="link"
                no-caret
                :right="$store.state.appConfig.isRTL"
              >

                <template #button-content>
                  <feather-icon
                    icon="MoreVerticalIcon"
                    size="16"
                    class="align-middle text-body"
                  />
                </template>

                <b-dropdown-item @click="getEdit(data)">
                  <feather-icon icon="EditIcon" />
                  <span class="align-middle ml-50">Editar</span>
                </b-dropdown-item>

                <b-dropdown-item
                  @click="getDelete(data)"
                >
                  <feather-icon icon="TrashIcon" />
                  <span class="align-middle ml-50">Eliminar</span>
                </b-dropdown-item>
              </b-dropdown>
            </template>
          </b-table>
        </div>

      </b-form>
    </template>
  </b-sidebar>
</template>

<script>
import Vue from 'vue'
import {
  BRow, BCol, BSidebar, BForm, BFormGroup, BFormInput, BButton, BSpinner, BTable, BProgress, BDropdown, BDropdownItem,
} from 'bootstrap-vue'
import { ref } from '@vue/composition-api'
import { required } from '@validations'
import { useNotify } from '@/helpers/toast'
import Ripple from 'vue-ripple-directive'
import store from '@/store'

export default {
  components: {
    BRow,
    BCol,
    BSidebar,
    BForm,
    BFormGroup,
    BFormInput,
    BButton,
    BSpinner,
    BTable,
    BDropdown,
    BDropdownItem,
    BProgress,
  },
  directives: {
    Ripple,
  },
  model: {
    prop: 'isToggleHabiSidebar',
    event: 'update:is-toggle-habi-sidebar',
  },
  props: {
    isToggleHabiSidebar: {
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
  setup(props, context) {
    const isBusy = ref(false)
    const isBusyLista = ref(false)
    const formData = ref({})
    const formType = ref('new')
    const fields = ref([
      { key: 'idHabilidad', sortable: true, thClass: 'd-none', tdClass: 'd-none' },
      { key: 'habilidad', sortable: true },
      { key: 'nivel', sortable: true },
      { key: 'acciones', label: '', tdClass: 'text-center', thClass: 'text-center' },
    ])
    const { notify } = useNotify(context)
    const isNewRegister = ref(false)

    const habilidades = ref([])

    const resetForm = () => {
      formData.value = {}
    }

    const newItem = () => {
      formType.value = 'new'
      resetForm()
      isNewRegister.value = true
    }

    const cancelForm = () => {
      isNewRegister.value = false
    }

    const getRegistros = async () => {
      isBusyLista.value = true
      await store.dispatch('personal/HABILIDAD_FIND_ALL', {
        idPersonal: props.dataEdit.idPersonal,
        query: '',
        page: -1,
        limit: -1,
        tipo: 'grilla',
        sortBy: '',
      })
        .then(response => {
          if (response) {
            habilidades.value = response
          }
        })
      isBusyLista.value = false
    }

    const getEdit = data => {
      isNewRegister.value = true
      formType.value = 'edit'
      formData.value = data.item
    }

    const getDelete = data => {
      Vue.swal({
        title: 'Está seguro?',
        text: 'Va eliminar un registro!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sí',
        cancelButtonText: 'No',
        customClass: {
          confirmButton: 'btn btn-danger',
          cancelButton: 'btn btn-primary ml-1',
        },
        buttonsStyling: false,
      }).then(async result => {
        if (result.value) {
          await store
            .dispatch('personal/HABILIDAD_DELETE', {
              id: data.item.idHabilidad,
            })
            .then(() => {
              notify('Operación Exitosa', 'El proceso se ha ejecutado satisfactoriamente.', 'success')
              getRegistros()
            })
            .catch(error => {
              let errorMessage = 'Intentelo en otro momento.'
              if (error.response) {
                errorMessage = error.response.data.message
              }
              notify('Ha ocurrido un error', errorMessage, 'danger')
            })
        }
      })
    }

    const resolveColor = porc => {
      if (porc <= 30) return 'danger'
      if (porc > 30 && porc <= 60) return 'warning'
      if (porc > 60) return 'success'
      return 'info'
    }

    const saveForm = async () => {
      isBusy.value = true
      // Validar datos antes
      let service = 'personal/HABILIDAD_CREATE'
      if (formType.value === 'edit') {
        service = 'personal/HABILIDAD_UPDATE'
      }
      formData.value.idPersonal = props.dataEdit.idPersonal

      await store.dispatch(service, formData.value)
        .then(async response => {
          notify('Operación Exitosa', response.message, 'success')
          await getRegistros()
          resetForm()
          // context.emit('refetch-data', response)
          // emit('update:is-toggle-habi-sidebar', false)
        })
        .catch(error => {
          context.emit('error-data', error)
          context.emit('update:is-toggle-habi-sidebar', false)
        })
      isBusy.value = false
      isNewRegister.value = false
    }

    const toggleSidebar = async val => {
      context.emit('update:is-toggle-habi-sidebar', val)
      await getRegistros()
    }

    return {
      isBusy,
      isBusyLista,
      formData,
      saveForm,
      toggleSidebar,
      resetForm,
      getRegistros,
      habilidades,
      fields,
      getEdit,
      getDelete,
      resolveColor,
      isNewRegister,
      newItem,
      cancelForm,
    }
  },
}
</script>

<style lang="scss">
@import '@core/scss/vue/libs/vue-select.scss';
@import '@core/scss/vue/libs/vue-flatpicker.scss';
@import '@core/scss/vue/libs/quill.scss';

#manage-hability-sidebar {
  .vs__dropdown-menu {
    max-height: 200px !important;
  }
}
#quil-content ::v-deep {
  > .ql-container {
    border-bottom: 0;
  }

  + #quill-toolbar {
    border-top-left-radius: 0;
    border-top-right-radius: 0;
    border-bottom-left-radius: $border-radius;
    border-bottom-right-radius: $border-radius;
  }
}
</style>
