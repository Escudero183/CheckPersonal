<template>
  <div>
    <usuario-form
      :is-add-new-user-sidebar-active.sync="isAddNewSidebarActive"
      :form-type="formType"
      :data-edit="dataEdit"
      @refetch-data="refetchData"
      @error-data="showError"
    />

    <usuario-password
      :is-open-edit-password.sync="isOpenEditPassword"
      :data-edit="dataEdit"
      @refetch-data="refetchData"
      @error-data="showError"
    />

    <!-- Table Container Card -->
    <b-card
      no-body
      class="mb-0"
    >

      <div class="m-2">
        <!-- Table Top -->
        <b-row>
          <!-- Per Page -->
          <b-col
            cols="12"
            md="6"
            class="d-flex align-items-center justify-content-start mb-1 mb-md-0"
          >
            <label>Mostrando</label>
            <v-select
              v-model="perPage"
              :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
              :options="pageOptions"
              :clearable="false"
              class="per-page-selector d-inline-block mx-50"
            />
            <label>registros</label>
          </b-col>

          <!-- Search -->
          <b-col
            cols="12"
            md="6"
          >
            <div class="d-flex align-items-center justify-content-end">
              <b-form-input
                v-model="query"
                class="d-inline-block mr-1"
                placeholder="Buscar..."
              />
              <b-button
                variant="primary"
                @click="openForm('new')"
              >
                <span class="text-nowrap">Nuevo</span>
              </b-button>
            </div>
          </b-col>
        </b-row>

      </div>

      <b-table
        ref="refUserListTable"
        class="position-relative"
        :items="items"
        responsive
        :fields="fields"
        primary-key="id"
        :sort-by.sync="sortBy"
        show-empty
        empty-text="No se encontraron registros"
        :busy="isBusy"
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

        <template #cell(fechaAlta)="data">
          <b-badge
            v-if="data.item.fechaAlta != null"
            pill
            variant="light-primary"
            class="d-block"
          >
            ALTA: {{ getDateFmt(data.item.fechaAlta) }}
          </b-badge>

          <b-badge
            v-else
            pill
            variant="light-secondary"
            class="d-block text-capitalize"
          >
            ALTA: No Registra
          </b-badge>

          <b-badge
            v-if="data.item.fechaBaja != null"
            pill
            variant="light-primary"
            class="d-block mt-50"
          >
            BAJA: {{ getDateFmt(data.item.fechaBaja) }}
          </b-badge>

          <b-badge
            v-else
            pill
            variant="light-secondary"
            class="d-block text-capitalize mt-50"
          >
            BAJA: No Registra
          </b-badge>
        </template>

        <template #cell(estadoCuenta)="data">
          <b-badge
            v-if="data.item.estadoCuenta == 'A'"
            pill
            variant="light-success"
            class="text-capitalize"
          >
            Activo
          </b-badge>

          <b-badge
            v-if="data.item.estadoCuenta == 'I'"
            pill
            variant="light-danger"
            class="text-capitalize"
          >
            Inactivo
          </b-badge>

          <b-badge
            v-if="data.item.estadoCuenta == 'S'"
            pill
            variant="light-warning"
            class="text-capitalize"
          >
            Suspendido
          </b-badge>

          <b-badge
            v-if="data.item.estadoCuenta == null"
            pill
            variant="light-secondary"
            class="text-capitalize"
          >
            No Registra
          </b-badge>
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

            <b-dropdown-item @click="getPermissions(data)">
              <feather-icon icon="UserCheckIcon" />
              <span class="align-middle ml-50">Asignar Permisos</span>
            </b-dropdown-item>

            <b-dropdown-item @click="getEditPassword(data)">
              <feather-icon icon="LockIcon" />
              <span class="align-middle ml-50">Actualizar Contraseña</span>
            </b-dropdown-item>

            <b-dropdown-item @click="getEdit(data)">
              <feather-icon icon="EditIcon" />
              <span class="align-middle ml-50">Editar</span>
            </b-dropdown-item>

            <b-dropdown-item @click="getChangeState(data,'A')">
              <feather-icon icon="ThumbsUpIcon" />
              <span class="align-middle ml-50">Activar Cuenta</span>
            </b-dropdown-item>

            <b-dropdown-item @click="getChangeState(data,'I')">
              <feather-icon icon="ThumbsDownIcon" />
              <span class="align-middle ml-50">Desactivar Cuenta</span>
            </b-dropdown-item>

            <b-dropdown-item @click="getDelete(data)">
              <feather-icon icon="TrashIcon" />
              <span class="align-middle ml-50">Eliminar</span>
            </b-dropdown-item>
          </b-dropdown>
        </template>

      </b-table>
      <div class="mx-2 mb-2">
        <b-row>

          <b-col
            cols="12"
            sm="6"
            class="d-flex align-items-center justify-content-center justify-content-sm-start"
          >
            <span class="text-muted">Mostrando {{ from }} a {{ to }} de {{ of }} registros</span>
          </b-col>
          <!-- Pagination -->
          <b-col
            cols="12"
            sm="6"
            class="d-flex align-items-center justify-content-center justify-content-sm-end"
          >

            <b-pagination
              v-model="page"
              :total-rows="totalItems"
              :per-page="perPage"
              first-number
              last-number
              class="mb-0 mt-1 mt-sm-0"
              prev-class="prev-item"
              next-class="next-item"
            >
              <template #prev-text>
                <feather-icon
                  icon="ChevronLeftIcon"
                  size="18"
                />
              </template>
              <template #next-text>
                <feather-icon
                  icon="ChevronRightIcon"
                  size="18"
                />
              </template>
            </b-pagination>

          </b-col>

        </b-row>
      </div>
    </b-card>
    <b-modal
      v-model="modalShow"
      title="Configure Perfiles de Acceso"
      ok-title="Guardar"
      cancel-variant="outline-secondary"
      no-close-on-backdrop
      no-close-on-esc
      :hide-footer="true"
    >
      <perfil-permisos
        ref="refPerfilPermisos"
        :modal-show.sync="modalShow"
        :data-usuario.sync="dataUsuario"
      />
    </b-modal>
  </div>
</template>

<script>
import {
  BCard, BRow, BCol, BFormInput, BButton, BTable, BBadge, BDropdown, BDropdownItem, BPagination, BSpinner,
} from 'bootstrap-vue'
import vSelect from 'vue-select'
import store from '@/store'
import moment from 'moment'
import { avatarText } from '@core/utils/filter'
import { showError } from '@/helpers'
import UsuarioForm from './UsuarioForm.vue'
import UsuarioPassword from './UsuarioPassword.vue'
import PerfilPermisos from '../perfil/PerfilPermisos.vue'

export default {
  components: {
    UsuarioForm,
    UsuarioPassword,
    PerfilPermisos,
    BCard,
    BRow,
    BCol,
    BFormInput,
    BButton,
    BTable,
    BBadge,
    BDropdown,
    BDropdownItem,
    BPagination,
    BSpinner,

    vSelect,
  },
  data: () => ({
    dataUsuario: {},
    modalShow: false,
    isBusy: false,
    isAddNewSidebarActive: false,
    isOpenEditPassword: false,
    dataEdit: {},
    formType: '',
    page: 1,
    perPage: 10,
    totalRows: 0,
    totalItems: 0,
    totalPage: 0,
    from: 0,
    to: 0,
    of: 0,
    pageOptions: [10, 20, 50],
    sortBy: null,
    sortDesc: false,
    sortDirection: 'asc',
    query: '',
    items: [],
    fields: [
      { key: 'idUsuario', sortable: true, thClass: 'd-none', tdClass: 'd-none' },
      { key: 'login', sortable: true },
      { key: 'fechaAlta', label: 'Fechas Registro', thClass: 'text-center', sortable: false },
      { key: 'estadoCuenta', sortable: false },
      { key: 'acciones' },
    ],
    avatarText,
  }),
  watch: {
    perPage: {
      handler() {
        this.listarRegistros()
      },
    },
    page: {
      handler() {
        this.listarRegistros()
      },
    },
    query: {
      handler() {
        this.listarRegistros()
      },
    },
  },
  mounted() {
    this.listarRegistros()
  },
  methods: {
    getPermissions(data) {
      this.modalShow = true
      this.dataUsuario = data.item
      // this.$refs.refPerfilPermisos.listarPerfiles()
    },
    listarRegistros() {
      this.isBusy = true
      store
        .dispatch('seguridad/USUARIO_FIND_ALL', {
          limit: this.perPage,
          query: this.query,
          page: this.page,
          sortBy: 'idUsuario%7CDESC',
        })
        .then(response => {
          this.items = response.items
          this.totalRows = response.totalRows
          this.totalItems = response.totalItems
          this.totalPage = response.totalPage
          this.from = this.perPage * (this.page - 1) + (this.totalRows ? 1 : 0)
          this.to = this.perPage * (this.page - 1) + this.totalRows
          this.of = this.totalItems
          this.isBusy = false
        })
        .catch(error => {
          this.showError(error)
          this.isBusy = false
        })
    },
    openForm(type) {
      this.formType = type
      this.isAddNewSidebarActive = true
    },
    getEdit(data) {
      this.formType = 'edit'
      this.isAddNewSidebarActive = true
      this.dataEdit = data.item
    },
    getEditPassword(data) {
      this.isOpenEditPassword = true
      this.dataEdit = data.item
    },
    getDelete(data) {
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
            .dispatch('seguridad/USUARIO_DELETE', {
              id: data.item.idUsuario,
            })
            .then(response => {
              this.listarRegistros()
              this.$bvToast.toast('Ha Eliminado correctamente el registro.', {
                title: 'Operación Exitosa',
                variant: 'success',
                solid: true,
              })
              return response
            })
            .catch(error => {
              this.showError(error)
            })
        }
      })
    },
    refetchData(response) {
      let message = 'El proceso se ha ejecutado satisfactoriamente.'
      if (response.message) {
        message = response.message
      }
      this.$bvToast.toast(message, {
        title: 'Operación Extitosa',
        variant: 'success',
        solid: true,
      })
      this.listarRegistros()
    },
    showError,
    getDateFmt(date) {
      return moment(String(date)).format('DD/MM/YYYY hh:mm a')
    },
  },
}
</script>

<style lang="scss" scoped>
.per-page-selector {
  width: 90px;
}
</style>

<style lang="scss">
@import '@core/scss/vue/libs/vue-select.scss';
</style>
