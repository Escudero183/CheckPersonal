<template>
  <b-sidebar
    id="add-new-user-sidebar"
    :visible="isToggleSidebar"
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
          {{ titleForm }}
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
        <h6
          class="text-info"
        >
          <feather-icon icon="AlertCircleIcon" />
          <span class="align-middle ml-25">Datos Generales</span>
        </h6>

        <hr style="border-top: 2px solid #00cfe8;margin-bottom: 1rem !important;margin-top: 0 !important;">

        <b-form-group
          label="Documento de Identidad"
          label-for="nroDoc"
        >
          <b-input-group class="input-group-merge">
            <v-select
              id="frmTipoDoc"
              v-model="tipoDocSel"
              label="abreviatura"
              :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
              :options="tiposDocument"
              @input="isReadOnly()"
            />
            <b-form-input
              id="search-nro-doc"
              v-model="formData.numeroDocumento"
              style="padding: 0.438rem 1rem;"
            />
            <b-button
              variant="info"
              @click="searchTrabajador"
            >
              <feather-icon icon="SearchIcon" />
            </b-button>
          </b-input-group>
        </b-form-group>

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

        <b-media class="mb-0">
          <template #aside>
            <b-avatar
              ref="previewEl"
              :src="`data:image/jpeg;base64,${foto}`"
              size="132px"
              rounded
              style="width: 132px; height: 208px;"
            />
          </template>

          <b-row>
            <b-col
              cols="12"
              md="12"
            >
              <b-form-group
                label="Nombres"
                label-for="nombres"
              >
                <b-form-input
                  id="nombres"
                  v-model="formData.nombres"
                  :readonly="isReadOnly()"
                />
              </b-form-group>
            </b-col>
          </b-row>
          <b-row>
            <b-col
              cols="12"
              md="12"
            >
              <b-form-group
                label="Apellido Paterno"
                label-for="apellido_paterno"
              >
                <b-form-input
                  id="apellido_paterno"
                  v-model="formData.apellidoPaterno"
                  :readonly="isReadOnly()"
                />
              </b-form-group>
            </b-col>
            <b-col
              cols="12"
              md="12"
            >
              <b-form-group
                label="Apellido Materno"
                label-for="apellido_materno"
              >
                <b-form-input
                  id="apellido_materno"
                  v-model="formData.apellidoMaterno"
                  :readonly="isReadOnly()"
                />
              </b-form-group>
            </b-col>
          </b-row>
        </b-media>

        <b-row>
          <b-col
            cols="12"
            md="5"
          >
            <b-form-group
              label="Fecha Nacimiento"
              label-for="fecha_nacimiento"
            >
              <flat-pickr
                v-model="formData.fechaNacimiento"
                class="form-control"
                :config="configDP"
              />
            </b-form-group>
          </b-col>
          <b-col
            cols="12"
            md="7"
          >
            <b-form-group
              label="Sexo"
              label-for="sexo"
            >
              <v-select
                id="sexo"
                v-model="sexoSel"
                :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
                :options="sexos"
              />
            </b-form-group>
          </b-col>
        </b-row>

        <h6
          class="text-info"
        >
          <feather-icon icon="MapPinIcon" />
          <span class="align-middle ml-25">Ubicación</span>
        </h6>

        <hr style="border-top: 2px solid #00cfe8;margin-bottom: 1rem !important;margin-top: 0 !important;">

        <b-form-group
          label="Seleccione Departamento"
          label-for="dpto"
        >
          <v-select
            v-model="dptoSel"
            :reduce="m => m.idUbigeo"
            label="descripcion"
            :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
            :options="dptos"
            @input="getUbigeos('prov', dptoSel.slice(0, 2))"
          />
        </b-form-group>
        <b-form-group
          label="Seleccione Provincia"
          label-for="prov"
        >
          <v-select
            v-model="provSel"
            :reduce="m => m.idUbigeo"
            label="descripcion"
            :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
            :options="provs"
            @input="getUbigeos('dist', provSel.slice(0, 4))"
          />
        </b-form-group>
        <b-form-group
          label="Seleccione Distrito"
          label-for="dist"
        >
          <v-select
            v-model="distSel"
            :reduce="m => m.idUbigeo"
            label="descripcion"
            :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
            :options="dists"
          />
        </b-form-group>

        <b-form-group
          label="Dirección"
          label-for="direccion"
        >
          <b-form-input
            id="direccion"
            v-model="formData.direccion"
          />
        </b-form-group>

        <h6
          class="text-info"
        >
          <feather-icon icon="PhoneCallIcon" />
          <span class="align-middle ml-25">Datos de Contacto</span>
        </h6>

        <hr style="border-top: 2px solid #00cfe8;margin-bottom: 1rem !important;margin-top: 0 !important;">

        <b-row>
          <b-col
            cols="12"
            md="12"
          >
            <b-form-group
              label="Celular"
              label-for="celular"
            >
              <b-form-input
                id="celular"
                v-model="formData.celular"
                type="number"
              />
            </b-form-group>
          </b-col>
          <b-col
            cols="12"
            md="12"
          >
            <b-form-group
              label="Correo"
              label-for="correo"
            >
              <b-form-input
                id="correo"
                v-model="formData.correo"
                type="email"
              />
            </b-form-group>
          </b-col>
        </b-row>

        <h6
          class="text-info"
        >
          <feather-icon icon="BriefcaseIcon" />
          <span class="align-middle ml-25">Otros Datos</span>
        </h6>

        <hr style="border-top: 2px solid #00cfe8;margin-bottom: 1rem !important;margin-top: 0 !important;">
        <!-- Presentación -->
        <b-form-group
          label="Presentación"
          label-for="presentacion"
        >
          <quill-editor
            id="quil-content"
            v-model="formData.presentacion"
            :options="editorOption"
            class="border-bottom-0"
          />
        </b-form-group>

        <b-form-group
          label="Banner (1400 x 470)"
          label-for="banner"
        >
          <b-row class="container">
            <b-col md="4">
              <b-media class="mb-2 text-center">
                <template #aside>
                  <b-avatar
                    ref="previewElBanner"
                    :src="imgBase64Banner"
                    size="100px"
                    rounded=""
                  />
                </template>
              </b-media>
            </b-col>
            <b-col md="8">
              <b-row class="mt-1">
                <b-col md="12">
                  <b-button
                    variant="info"
                    class="btn-block btn-sm"
                    @click="$refs.refInputElBanner.$el.click()"
                  >
                    <b-form-file
                      id="file"
                      ref="refInputElBanner"
                      v-model="fileBanner"
                      accept=".jpg, .png, .gif"
                      :hidden="true"
                      plain
                      @change="uploadBanner"
                    />
                    <span class="d-none d-sm-inline">Seleccionar Banner</span>
                    <feather-icon
                      icon="EditIcon"
                      class="d-inline d-sm-none"
                    />
                  </b-button>
                </b-col>
              </b-row>
              <b-row style="padding-top:10px">
                <b-col md="12">
                  <b-button
                    variant="light"
                    class="btn-block btn-sm"
                    @click="onDeleteBanner"
                  >
                    <span class="d-none d-sm-inline">Eliminar Banner</span>
                    <feather-icon
                      icon="TrashIcon"
                      class="d-inline d-sm-none"
                    />
                  </b-button>
                </b-col>
              </b-row>
            </b-col>
          </b-row>
        </b-form-group>

        <!-- Form Actions -->
        <div class="d-flex mt-2">
          <b-button
            v-ripple.400="'rgba(255, 255, 255, 0.15)'"
            variant="primary"
            class="mr-2"
            type="button"
            @click="saveForm"
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
    </template>
  </b-sidebar>
</template>

<script>
import {
  BRow, BCol, BAvatar, BMedia, BSidebar, BForm, BFormGroup, BFormInput, BInputGroup, BButton, BSpinner, BFormFile,
} from 'bootstrap-vue'
import flatPickr from 'vue-flatpickr-component'
import vSelect from 'vue-select'
import { useNotify } from '@/helpers/toast'
import { ref } from '@vue/composition-api'
import { required } from '@validations'
import { quillEditor } from 'vue-quill-editor'
import config from '@/services/config'
import Ripple from 'vue-ripple-directive'
import store from '@/store'

export default {
  components: {
    BRow,
    BCol,
    BAvatar,
    BMedia,
    BSidebar,
    BForm,
    BFormGroup,
    BFormInput,
    BInputGroup,
    BButton,
    flatPickr,
    vSelect,
    BSpinner,
    quillEditor,
    BFormFile,
  },
  directives: {
    Ripple,
  },
  model: {
    prop: 'isToggleSidebar',
    event: 'update:is-toggle-sidebar',
  },
  props: {
    isToggleSidebar: {
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
  setup(props, context) {
    const { notify } = useNotify(context)
    const { baseURL } = config
    const pathMultimedia = ref(`${baseURL}api-checkpersonal/api/v1/multimedia/`)
    const titleForm = ref('')
    const isBusy = ref(false)
    const tiposDocument = ref([])
    const tipoDocSel = ref(null)
    const formData = ref({})
    const foto = ref('')
    const readonlyFrm = ref(false)
    const dptoSel = ref('220000')
    const provSel = ref(null)
    const distSel = ref(null)
    const dptos = ref([])
    const provs = ref([])
    const dists = ref([])
    const sexos = ref(['MASCULINO', 'FEMENINO'])
    const sexoSel = ref('MASCULINO')
    const toolbarOptions = ref([
      ['bold', 'italic', 'underline', 'strike'],
      [{ list: 'ordered' }, { list: 'bullet' }],
      ['link', 'image'],
      [{ align: [] }],
    ])
    const editorOption = ref({
      modules: {
        toolbar: toolbarOptions,
      },
      placeholder: 'Ingrese presentación del profesional',
    })

    const configDP = ref(
      {
        enableTime: false,
        dateFormat: 'd/m/Y',
        altInput: true,
        altFormat: 'd/m/Y',
      },
    )

    const imgBase64Banner = ref(null)
    const refInputElBanner = ref(null)
    const fileBanner = ref(null)
    const previewElBanner = ref(null)

    const onDeleteBanner = () => {
      imgBase64Banner.value = null
    }
    const uploadBanner = e => {
      const fileTemp = e.target.files[0]
      fileBanner.value = fileTemp
      imgBase64Banner.value = URL.createObjectURL(fileBanner.value)
    }

    const resetForm = () => {
      formData.value = {}
      foto.value = ''
    }

    const getTiposDocumento = async () => {
      await store.dispatch('catalogo/TIPO_DOCUMENTO_FIND_ALL')
        .then(response => {
          if (response) {
            tiposDocument.value = response
            tipoDocSel.value = tiposDocument.value.find(id => id.idTipoDocumento === 1)
          }
        })
    }

    const searchTrabajador = async () => {
      isBusy.value = true
      await store
        .dispatch('helpers/RENIEC_FIND_CIUDADANO', {
          dni: formData.value.numeroDocumento,
        })
        .then(response => {
          if (response.datosPersona) {
            formData.value.nombres = response.datosPersona.prenombres
            formData.value.apellidoPaterno = response.datosPersona.apPrimer
            formData.value.apellidoMaterno = response.datosPersona.apSegundo
            formData.value.apellidoMaterno = response.datosPersona.apSegundo
            formData.value.foto = response.datosPersona.foto
            formData.value.dirReniecnombres = response.datosPersona.direccion
            formData.value.dirReniecUbigeo = response.datosPersona.ubigeo
            foto.value = response.datosPersona.foto
          }
        })
        .catch(error => {
          context.emit('error-data', error)
        })

      isBusy.value = false
    }

    const saveForm = async () => {
      // Validar datos antes
      let service = 'personal/PERSONAL_CREATE2'
      if (props.formType === 'edit') {
        service = 'personal/PERSONAL_UPDATE2'
      }
      formData.value.idTipoDocumento = tipoDocSel.value.idTipoDocumento
      formData.value.sexo = sexoSel.value
      formData.value.banner = (fileBanner.value !== null) ? fileBanner.value : null

      formData.value.idUbigeo = distSel.value

      await store.dispatch(service, formData.value)
        .then(response => {
          notify('Operación Exitosa', response.message, 'success')
          context.emit('refetch-data', response)
          context.emit('update:is-toggle-sidebar', false)
        })
        .catch(error => {
          context.emit('error-data', error)
          context.emit('update:is-toggle-sidebar', false)
        })
    }

    const isReadOnly = () => {
      if (tipoDocSel.value && tipoDocSel.value.id === 1) return true
      return false
    }

    const getUbigeos = async (type, codUbigeo) => {
      await store
        .dispatch('catalogo/UBIGEO_FIND_ALL', {
          get: type,
          codUbigeo,
        })
        .then(response => {
          if (type === 'dpto') {
            dptos.value = response
            provSel.value = null
            distSel.value = null
            provs.value = []
            dists.value = []
          }
          if (type === 'prov') {
            provs.value = response
            provSel.value = null
            distSel.value = null
            dists.value = []
          }
          if (type === 'dist') {
            dists.value = response
          }
        })
        .catch(error => {
          context.emit('error-data', error)
        })
    }

    const toggleSidebar = async val => {
      context.emit('update:is-toggle-sidebar', val)
      await getTiposDocumento()
      if (props.formType === 'new') {
        titleForm.value = 'Agregar Personal'
        await getUbigeos('dpto', '')
        await getUbigeos('prov', dptoSel.value.slice(0, 2))
      }
      if (props.formType === 'edit') {
        formData.value = props.dataEdit
        tipoDocSel.value = props.dataEdit.tipoDocumento
        foto.value = props.dataEdit.foto
        dptoSel.value = `${props.dataEdit.ubigeo.idUbigeo.slice(0, 2)}0000`
        await getUbigeos('dpto', '')
        await getUbigeos('prov', props.dataEdit.ubigeo.idUbigeo.slice(0, 2))
        provSel.value = `${props.dataEdit.ubigeo.idUbigeo.slice(0, 4)}00`
        await getUbigeos('dist', props.dataEdit.ubigeo.idUbigeo.slice(0, 4))
        distSel.value = props.dataEdit.ubigeo.idUbigeo
        titleForm.value = 'Editar Personal'
      }
    }

    return {
      isBusy,
      tiposDocument,
      tipoDocSel,
      formData,
      searchTrabajador,
      foto,
      saveForm,
      toggleSidebar,
      isReadOnly,
      resetForm,
      readonlyFrm,
      configDP,
      dptoSel,
      provSel,
      distSel,
      dptos,
      provs,
      dists,
      getUbigeos,
      titleForm,
      sexoSel,
      sexos,
      toolbarOptions,
      editorOption,
      pathMultimedia,
      onDeleteBanner,
      uploadBanner,
      imgBase64Banner,
      refInputElBanner,
      fileBanner,
      previewElBanner,
    }
  },
}
</script>

<style lang="scss">
@import '@core/scss/vue/libs/vue-select.scss';
@import '@core/scss/vue/libs/vue-flatpicker.scss';
@import '@core/scss/vue/libs/quill.scss';

#add-new-user-sidebar {
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
