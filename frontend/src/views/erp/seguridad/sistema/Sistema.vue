<template>
  <section>
    <sistema-form
      :is-add-edit-sistema-sidebar.sync="isAddEditSistemaSidebar"
      :form-type="formTypeS"
      :data-edit="dataEdit"
      @refetch-data="refetchDataS"
      @error-data="showError"
    />
    <b-row class="match-height">
      <b-col
        lg="4"
        md="6"
      >
        <b-card
          class="card-transaction"
          no-body
        >
          <b-card-header>
            <b-card-title>Sistemas</b-card-title>

            <b-button
              variant="primary"
              @click="openFormS('new')"
            >
              <span class="text-nowrap">Nuevo</span>
            </b-button>
          </b-card-header>

          <b-card-body>
            <div
              v-if="isBusyS"
              class="text-center text-primary my-2"
            >
              <b-spinner
                variant="primary"
                class="align-middle"
              />
              <strong class="ml-1">Cargando...</strong>
            </div>
            <div
              v-for="(sistema, index) in itemsS"
              :key="index"
              class="transaction-item"
            >
              <b-media no-body>
                <b-media-aside>
                  <b-avatar
                    rounded
                    size="42"
                    :variant="getClassColor(sistema.idSistema)"
                  >
                    <feather-icon
                      size="18"
                      :icon="sistema.icono"
                    />
                  </b-avatar>
                </b-media-aside>
                <b-media-body>
                  <h6 class="transaction-title">
                    {{ sistema.nombre }}
                  </h6>
                  <small>{{ sistema.pathApi }}</small>
                </b-media-body>
              </b-media>
              <b-dropdown
                variant="link"
                no-caret
                class="chart-dropdown"
                toggle-class="p-0"
                right
              >
                <template #button-content>
                  <feather-icon
                    icon="MoreVerticalIcon"
                    size="18"
                    class="text-body cursor-pointer"
                  />
                </template>
                <b-dropdown-item @click="getModules(index , 'click')">
                  <feather-icon icon="ServerIcon" />
                  <span class="align-middle ml-50">Gestionar Módulos</span>
                </b-dropdown-item>
                <b-dropdown-item @click="getPerfiles(index)">
                  <feather-icon icon="ShieldIcon" />
                  <span class="align-middle ml-50">Gestionar Perfiles</span>
                </b-dropdown-item>
                <b-dropdown-item @click="getEditS(index)">
                  <feather-icon icon="EditIcon" />
                  <span class="align-middle ml-50">Editar</span>
                </b-dropdown-item>
                <b-dropdown-item @click="getDeleteS(index)">
                  <feather-icon icon="TrashIcon" />
                  <span class="align-middle ml-50">Eliminar</span>
                </b-dropdown-item>
              </b-dropdown>
            </div>
          </b-card-body>
        </b-card>
      </b-col>
      <b-col lg="8">
        <modulo-list
          v-show="showModules"
          ref="listModules"
          :is-get-modules.sync="isGetModules"
          :data-sistema="sistemaSel"
        />
        <perfil-list
          v-show="showPerfiles"
          ref="listPerfiles"
          :data-sistema="sistemaSel"
        />
      </b-col>
    </b-row>
  </section>
</template>

<script>
import { BButton, BCard, BCardHeader, BCardTitle, BCardBody, BMediaBody, BMedia, BMediaAside, BAvatar, BDropdown, BDropdownItem, BRow, BCol, BSpinner } from 'bootstrap-vue'
import store from '@/store'
import { showError } from '@/helpers'
import SistemaForm from './SistemaForm.vue'
import ModuloList from '../modulo/ModuloList.vue'
import PerfilList from '../perfil/PerfilList.vue'

export default {
  components: {
    BButton,
    BCard,
    BCardHeader,
    BCardTitle,
    BCardBody,
    BMediaBody,
    BMedia,
    BMediaAside,
    BAvatar,
    BDropdown,
    BDropdownItem,
    BRow,
    BCol,
    BSpinner,

    SistemaForm,
    ModuloList,
    PerfilList,
  },
  data() {
    return {
      classColors: ['success', 'primary', 'danger', 'warning', 'info', 'secondary'],
      itemsS: [],
      itemsM: [],
      itemsP: [],
      dataEdit: {},
      isBusyS: false,
      isBusyM: false,
      isBusyP: false,
      formTypeS: '',
      isAddEditSistemaSidebar: false,
      isGetModules: false,
      showModules: true,
      showPerfiles: false,
      isGetPerfiles: false,
      sistemaSel: {},
    }
  },
  async mounted() {
    await this.listarSistemas()
    if (this.itemsS.length > 0) {
      this.getModules(0, 'init')
    }
  },
  methods: {
    getModules(index, process) {
      this.showModules = true
      this.showPerfiles = false
      this.sistemaSel = this.itemsS[index]
      this.isGetModules = true
      if (process !== 'init') {
        this.$refs.listModules.listarModulos()
      }
    },
    getPerfiles(index) {
      this.showModules = false
      this.showPerfiles = true
      this.sistemaSel = this.itemsS[index]
      this.isGetPerfiles = true
      this.$refs.listPerfiles.listarPerfiles()
    },
    async listarSistemas() {
      this.isBusyS = true
      await store
        .dispatch('seguridad/SISTEMA_FIND_ALL', {
          limit: 50,
          query: '',
          page: 1,
          sortBy: 'idSistema%7CDESC',
        })
        .then(response => {
          this.itemsS = response.items
          this.isBusyS = false
        })
        .catch(error => {
          this.showError(error)
          this.isBusyS = false
        })
    },
    openFormS(type) {
      this.formTypeS = type
      this.isAddEditSistemaSidebar = true
    },
    getEditS(index) {
      this.formTypeS = 'edit'
      this.isAddEditSistemaSidebar = true
      this.dataEdit = this.itemsS[index]
    },
    getDeleteS(index) {
      this.$swal({
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
      }).then(result => {
        if (result.value) {
          store
            .dispatch('seguridad/SISTEMA_DELETE', {
              id: this.itemsS[index].idSistema,
            })
            .then(response => {
              this.listarSistemas()
              this.$bvToast.toast('Ha Eliminado correctamente el registro.', {
                title: 'Operación Exitosa',
                variant: 'success',
                solid: true,
              })
              return response
            })
            .catch(error => {
              this.$bvToast.toast(error, {
                title: 'Ha ocurrido un error',
                variant: 'danger',
                solid: true,
              })
            })
        }
      })
    },
    refetchDataS(response) {
      let message = 'El proceso se ha ejecutado satisfactoriamente.'
      if (response.message) {
        message = response.message
      }
      this.$bvToast.toast(message, {
        title: 'Operación Extitosa',
        variant: 'success',
        solid: true,
      })
      this.listarSistemas()
    },
    showError,
    getClassColor(index) {
      return this.classColors[index - 1]
    },
  },
}
</script>

<style lang="scss">
@import '@core/scss/vue/pages/dashboard-ecommerce.scss';
</style>
