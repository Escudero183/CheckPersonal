<template>
  <div style="height: inherit">
    <trabajador-form
      :is-toggle-sidebar.sync="isToggleSidebar"
      :form-type="formType"
      :data-edit="dataEdit"
      @refetch-data="getTrabajadores"
      @error-data="showError"
    />
    <habilidad-form
      :is-toggle-habi-sidebar.sync="isToggleHabiSidebar"
      :data-edit="dataEdit"
      @refetch-data="getTrabajadores"
      @error-data="showError"
    />
    <capacitacion-form
      :is-toggle-capa-sidebar.sync="isToggleCapaSidebar"
      :data-edit="dataEdit"
      @refetch-data="getTrabajadores"
      @error-data="showError"
    />
    <experiencia-laboral-form
      :is-toggle-exla-sidebar.sync="isToggleExlaSidebar"
      :data-edit="dataEdit"
      @refetch-data="getTrabajadores"
      @error-data="showError"
    />
    <formacion-academica-form
      :is-toggle-foac-sidebar.sync="isToggleFoacSidebar"
      :data-edit="dataEdit"
      @refetch-data="getTrabajadores"
      @error-data="showError"
    />
    <!-- ECommerce Header -->
    <section id="ecommerce-header">
      <div class="row">
        <div class="col-sm-12">
          <div class="ecommerce-header-items">
            <div class="result-toggler">
              <feather-icon
                icon="MenuIcon"
                class="d-block d-lg-none"
                size="21"
                @click="mqShallShowLeftSidebar = true"
              />
              <div class="search-results">
                {{ totalItems }} resultados encontrados
              </div>
            </div>
            <div class="view-options d-flex">
              <b-button
                variant="primary"
                @click="openForm('new')"
              >
                <span class="text-nowrap">Agregar Personal</span>
              </b-button>

              <!-- Sort Button -->
              <b-dropdown
                v-ripple.400="'rgba(113, 102, 240, 0.15)'"
                :text="sortBy.text"
                right
                variant="outline-primary"
                class="ml-1"
              >
                <b-dropdown-item
                  v-for="sortOption in sortByOptions"
                  :key="sortOption.value"
                  @click="sortBy=sortOption"
                >
                  {{ sortOption.text }}
                </b-dropdown-item>
              </b-dropdown>

              <!-- Item View Radio Button Group  -->
              <b-form-radio-group
                v-model="itemView"
                class="ml-1 list item-view-radio-group"
                buttons
                size="sm"
                button-variant="outline-primary"
              >
                <b-form-radio
                  :key="itemViewOptions[0].value"
                  :value="itemViewOptions[0].value"
                >
                  <feather-icon
                    :icon="itemViewOptions[0].icon"
                    size="18"
                  />
                </b-form-radio>
              </b-form-radio-group>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Overlay -->
    <div class="body-content-overlay" />

    <!-- Searchbar -->
    <div class="ecommerce-searchbar mt-1">
      <b-row>
        <b-col cols="12">
          <b-input-group class="input-group-merge">
            <b-form-input
              v-model="query"
              placeholder="Buscar trabajador"
              class="search-product"
            />
            <b-input-group-append is-text>
              <feather-icon
                icon="SearchIcon"
                class="text-muted"
              />
            </b-input-group-append>
          </b-input-group>
        </b-col>
      </b-row>
    </div>

    <b-row
      class="mt-1"
    >
      <b-col cols="12">
        <b-badge
          variant="primary"
          style="margin-right: 0.5rem !important;"
        >
          Filtros
        </b-badge>
      </b-col>
    </b-row>

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

    <!-- Trabajadores -->
    <section
      :class="itemView"
      style="margin-top: 1rem;"
    >
      <b-card
        v-for="trabajador in trabajadores"
        :key="trabajador.idPersonal"
        class="ecommerce-card bg-dark"
        no-body
      >
        <div
          class="item-img text-center"
          style="padding-bottom: 0px; margin: auto;"
        >
          <b-link
            :to="{ name: 'rt-public-profile', params: { dni: trabajador.numeroDocumento } }"
            target="_blank"
          >
            <b-img
              :alt="`${trabajador.nombres}-${trabajador.idPersonal}`"
              fluid
              class="card-img-top"
              :src="`data:image/png;base64,${trabajador.foto}`"
            />
          </b-link>
        </div>

        <!-- Trabajadores Detalles -->
        <b-card-body class="bg-white">
          <h6 class="item-name mt-0">
            <b-link
              class="text-body"
              :to="{ name: 'apps-e-commerce-product-details', params: { slug: trabajador.idPersonal } }"
            >
              {{ trabajador.nombres }} {{ trabajador.apellidoPaterno }} {{ trabajador.apellidoMaterno }}
            </b-link>
            <b-card-text class="item-company">
              By <b-link class="ml-25">
                {{ trabajador.brand }}
              </b-link>
            </b-card-text>
          </h6>
          <b-card-text class="item-description">
            <div class="item-wrapper">
              <div>
                {{ trabajador.tipoDocumento.abreviatura }} {{ trabajador.numeroDocumento }}
              </div>
              <div>
                EDAD {{ getEdad(trabajador.fechaNacimiento) }}
              </div>
            </div>
          </b-card-text>
        </b-card-body>

        <!-- Trabajadores Actions -->
        <div class="item-options text-center">
          <div class="item-wrapper">
            <div class="item-cost">
              <h4 class="item-price">
                ${{ trabajador.price }}
              </h4>
            </div>
          </div>

          <b-dropdown
            v-ripple.400="'rgba(113, 102, 240, 0.15)'"
            text="Gestionar Currículo"
            block
            right
            variant="primary"
            class="btn-cart"
          >
            <b-dropdown-item @click="getEdit(trabajador)">
              Editar Datos
            </b-dropdown-item>
            <b-dropdown-divider />
            <b-dropdown-item @click="getManageOthers('capa', trabajador)">
              Capacitaciones
            </b-dropdown-item>
            <b-dropdown-item @click="getManageOthers('habi', trabajador)">
              Habilidades
            </b-dropdown-item>
            <b-dropdown-item @click="getManageOthers('exla', trabajador)">
              Experiencia Laboral
            </b-dropdown-item>
            <b-dropdown-item @click="getManageOthers('foac', trabajador)">
              Formación Académica
            </b-dropdown-item>
          </b-dropdown>
        </div>
      </b-card>
    </section>

    <!-- Pagination -->
    <section>
      <b-row
        v-if="totalItems > 0"
      >
        <b-col cols="12">
          <b-pagination
            v-model="page"
            :total-rows="totalItems"
            :per-page="perPage"
            first-number
            align="center"
            last-number
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
    </section>

    <!-- Sidebar -->
    <portal to="content-renderer-sidebar-detached-left">
      <filter-sidebar
        :filters="filters"
        :filters-options.sync="filtersOptions"
        :data-ready.sync="dataReady"
        :mq-shall-show-left-sidebar.sync="mqShallShowLeftSidebar"
        @refetch-data="getTrabajadores"
      />
    </portal>
  </div>
</template>

<script>
import store from '@/store'
import moment from 'moment'
import {
  BBadge, BDropdown, BDropdownItem, BDropdownDivider, BFormRadioGroup, BFormRadio, BRow, BCol, BInputGroup, BInputGroupAppend, BFormInput, BCard, BCardBody, BLink, BImg, BCardText, BButton, BPagination, BSpinner,
} from 'bootstrap-vue'
import Ripple from 'vue-ripple-directive'
import { useNotify } from '@/helpers/toast'
import { ref, watch } from '@vue/composition-api'
import { useResponsiveAppLeftSidebarVisibility } from '@core/comp-functions/ui/app'
import FilterSidebar from './FichaPersonalFilterSidebar.vue'
import { useShopUi } from './useECommerceShop'
import { useEcommerceUi } from './useEcommerce'
import TrabajadorForm from './TrabajadorForm.vue'
import HabilidadForm from './HabilidadForm.vue'
import CapacitacionForm from './CapacitacionForm.vue'
import ExperienciaLaboralForm from './ExperienciaLaboralForm.vue'
import FormacionAcademicaForm from './FormacionAcademicaForm.vue'

export default {
  directives: {
    Ripple,
  },
  components: {
    // BSV
    BBadge,
    BDropdown,
    BDropdownItem,
    BDropdownDivider,
    BFormRadioGroup,
    BFormRadio,
    BRow,
    BCol,
    BInputGroup,
    BInputGroupAppend,
    BFormInput,
    BCard,
    BCardBody,
    BLink,
    BImg,
    BCardText,
    BButton,
    BPagination,
    BSpinner,

    // SFC
    FilterSidebar,
    TrabajadorForm,
    HabilidadForm,
    CapacitacionForm,
    ExperienciaLaboralForm,
    FormacionAcademicaForm,
  },
  setup(props, context) {
    const { notify } = useNotify(context)
    // GSEncuesta
    const trabajadores = ref({})
    const page = ref(1)
    const query = ref('')
    const perPage = ref(12)
    const totalItems = ref(0)
    const totalPages = ref(0)
    const dataReady = ref(false)
    const isBusy = ref(false)
    const filtersOptions = ref({})
    const filtersApply = ref({})
    const isToggleSidebar = ref(false)
    const isToggleHabiSidebar = ref(false)
    const isToggleCapaSidebar = ref(false)
    const isToggleExlaSidebar = ref(false)
    const isToggleFoacSidebar = ref(false)
    const formType = ref('')
    const dataEdit = ref({})

    const filters = ref({})
    filters.value.edad = [0, 100]
    filters.value.habilidades = -1

    const sortByOptions = ref([
      {
        text: 'Todos',
        value: '-1',
      },
      {
        text: 'Hombres',
        value: 'MASCULINO',
      },
      {
        text: 'Mujeres',
        value: 'FEMENINO',
      },
    ])

    const sortBy = ref({ text: 'Todos', value: '-1' })
    //

    const getTrabajadores = async () => {
      if (filters.value.tiempoServicio) {
        [filtersApply.value.servicioMin, filtersApply.value.servicioMax] = filters.value.tiempoServicio.split('-')
      }
      if (filters.value.edad) {
        [filtersApply.value.edadMin, filtersApply.value.edadMax] = filters.value.edad
      }
      isBusy.value = true
      await store.dispatch('personal/PERSONAL_FIND_ALL', {
        query: query.value,
        page: page.value,
        limit: perPage.value,
        sortBy: 'idPersonal%7CDESC',
        tipo: 'grilla',
        sexo: sortBy.value.value,
      })
        .then(response => {
          trabajadores.value = response.items
          totalItems.value = response.totalItems
          totalPages.value = response.totalPage
        })
      isBusy.value = false
    }

    const getTotalesH = async () => {
      await store.dispatch('personal/HABILIDAD_GET_TOTALES')
        .then(response => {
          if (response) {
            filtersOptions.value.habilidades = []
            filtersOptions.value.habilidades.push(
              {
                value: -1,
                text: 'Todos',
              },
            )
            response.forEach(habi => {
              filtersOptions.value.habilidades.push(
                {
                  value: habi.habilidad,
                  text: habi.habilidad,
                  total: habi.total,
                },
              )
            })
          }
        })
    }

    const openForm = type => {
      formType.value = type
      isToggleSidebar.value = true
    }

    const getEdit = data => {
      formType.value = 'edit'
      dataEdit.value = data
      isToggleSidebar.value = true
    }

    const getManageOthers = (from, data) => {
      dataEdit.value = data
      if (from === 'habi') {
        isToggleHabiSidebar.value = true
      }
      if (from === 'capa') {
        isToggleCapaSidebar.value = true
      }
      if (from === 'exla') {
        isToggleExlaSidebar.value = true
      }
      if (from === 'foac') {
        isToggleFoacSidebar.value = true
      }
    }

    const loadData = async () => {
      dataReady.value = false
      await getTrabajadores()
      await getTotalesH()
      dataReady.value = true
    }

    loadData()

    watch([query, page, sortBy, filters], () => {
      getTrabajadores()
    })

    const showError = error => {
      let errorMessage = 'Intentelo en otro momento.'
      if (error.response) {
        if (error.response.data.message) {
          errorMessage = error.response.data.message
        }
        if (error.response.data.errorMessage) {
          errorMessage = error.response.data.errorMessage
        }
      }
      notify('Ha ocurrido un error', errorMessage, 'danger')
    }

    const { handleCartActionClick, toggleProductInWishlist } = useEcommerceUi()

    const {
      itemView, itemViewOptions, totalProducts,
    } = useShopUi()

    const { mqShallShowLeftSidebar } = useResponsiveAppLeftSidebarVisibility()

    return {
      // GSEscalafon
      trabajadores,
      page,
      query,
      perPage,
      totalItems,
      totalPages,
      dataReady,
      isBusy,
      filtersOptions,
      getTrabajadores,
      //
      // useShopFiltersSortingAndPagination
      filters,
      sortBy,
      sortByOptions,

      // useShopUi
      itemView,
      itemViewOptions,
      totalProducts,
      toggleProductInWishlist,
      handleCartActionClick,

      // mqShallShowLeftSidebar
      mqShallShowLeftSidebar,
      showError,
      isToggleSidebar,
      openForm,
      getEdit,
      formType,
      dataEdit,
      isToggleHabiSidebar,
      isToggleCapaSidebar,
      isToggleExlaSidebar,
      isToggleFoacSidebar,
      getManageOthers,
    }
  },
  methods: {
    getEdad(value) {
      const hoy = moment(new Date())
      const fechaString = moment(String(value), 'DD/MM/YYYY').format('MM/DD/YYYY')
      const fecha = moment(fechaString, 'MM/DD/YYYY')
      return `${hoy.diff(moment(String(fecha)).format('MM/DD/YYYY'), 'years')} AÑOS`
    },
    getDateFmt(fecha) {
      return moment(String(fecha)).format('DD/MM/YYYY')
    },
  },
}
</script>

<style lang="scss">
@import "~@core/scss/base/pages/app-ecommerce.scss";
</style>

<style lang="scss" scoped>
.item-view-radio-group ::v-deep {
  .btn {
    display: flex;
    align-items: center;
  }
}
.ecommerce-application .grid-view {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr !important;
    -webkit-column-gap: 2rem;
    -moz-column-gap: 2rem;
    column-gap: 2rem;
}
</style>
<style>
.grid-view .dropdown-toggle{
  border-radius: 0 !important;
}
</style>
