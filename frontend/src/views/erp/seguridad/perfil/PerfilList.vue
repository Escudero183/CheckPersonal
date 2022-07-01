<template>
  <section>
    <perfil-form
      :is-new-edit-sidebar-active.sync="isNewEditSidebarActive"
      :form-type="formType"
      :data-sistema="dataSistema"
      :data-edit="dataEdit"
      @refetch-data="refetchData"
      @error-data="showError"
    />

    <!-- Table Container Card -->
    <b-card
      no-body
      class="card-transaction"
    >
      <b-card-header>
        <b-col
          cols="12"
          md="6"
          class="d-flex align-items-center justify-content-start mb-1 mb-md-0"
        >
          <b-card-title>Perfiles del {{ dataSistema.nombre }}</b-card-title>
        </b-col>

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
      </b-card-header>

      <b-card-body>
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
          <!-- Column: Status -->
          <template #cell(estado)="data">
            <b-badge
              v-if="data"
              pill
              variant="light-success"
              class="text-capitalize"
            >
              Activo
            </b-badge>

            <b-badge
              v-else
              pill
              variant="light-danger"
              class="text-capitalize"
            >
              Inactivo
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

              <b-dropdown-item @click="getEdit(data)">
                <feather-icon icon="EditIcon" />
                <span class="align-middle ml-50">Editar</span>
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
      </b-card-body>
    </b-card>
  </section>
</template>

<script>
import {
  BCard, BCardHeader, BCardTitle, BCardBody, BRow, BCol, BFormInput, BButton, BTable, BBadge, BDropdown, BDropdownItem, BPagination, BSpinner,
} from 'bootstrap-vue'
import store from '@/store'
import PerfilForm from './PerfilForm.vue'

export default {
  components: {
    PerfilForm,
    BCard,
    BCardHeader,
    BCardTitle,
    BCardBody,
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
  },
  props: {
    dataSistema: {
      type: Object,
      required: true,
    },
  },
  data: () => ({
    isNewEditSidebarActive: false,
    dataEdit: {},
    formType: '',
    isBusy: false,
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
      { key: 'idPerfil', sortable: true, tdClass: 'd-none', thClass: 'd-none' },
      { key: 'nombre', sortable: true },
      { key: 'icono', sortable: true },
      { key: 'estado', sortable: true },
      { key: 'acciones' },
    ],
  }),
  watch: {
    dataSistema: {
      handler() {
        this.listarPerfiles()
      },
    },
    perPage: {
      handler() {
        this.listarPerfiles()
      },
    },
    page: {
      handler() {
        this.listarPerfiles()
      },
    },
    query: {
      handler() {
        this.listarPerfiles()
      },
    },
  },
  methods: {
    listarPerfiles() {
      this.isBusy = true
      if (this.dataSistema.idSistema !== undefined) {
        store
          .dispatch('seguridad/PERFIL_FIND_ALL', {
            idSistema: this.dataSistema.idSistema,
            limit: this.perPage,
            query: this.query,
            page: this.page,
            sortBy: 'idPerfil%7CDESC',
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
            this.$bvToast.toast(error, {
              title: 'Ha ocurrido un error',
              variant: 'danger',
              solid: true,
            })
            this.isBusy = false
          })
      }
    },
    openForm(type) {
      this.formType = type
      this.isNewEditSidebarActive = true
    },
    getEdit(data) {
      this.formType = 'edit'
      this.isNewEditSidebarActive = true
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
            .dispatch('seguridad/PERFIL_DELETE', {
              id: data.item.idModulo,
            })
            .then(response => {
              this.listarModulos()
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
      this.listarPerfiles()
    },
    showError(error) {
      let errorMessage = 'Intentelo en otro momento.'
      if (error.response) {
        errorMessage = error.response.data.message
      }
      this.$bvToast.toast(errorMessage, {
        title: 'Ha ocurrido un error',
        variant: 'danger',
        solid: true,
      })
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
