<template>
  <div class="sidebar-detached sidebar-left">
    <div class="sidebar">
      <div
        class="sidebar-shop"
        :class="{'show': mqShallShowLeftSidebar}"
      >
        <b-row>
          <b-col cols="12">
            <h6 class="filter-heading d-none d-lg-block">
              Opciones de Búsqueda
            </h6>
          </b-col>
        </b-row>

        <!-- Filters' Card -->
        <b-card>
          <!-- Price Slider -->
          <div class="price-slider">
            <h6 class="filter-title mt-0">
              Rango Edad
            </h6>
            <vue-slider
              v-model="filters.edad"
              :direction="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
              @change="$emit('refetch-data')"
            />
          </div>

          <!-- Categories -->
          <div class="product-categories">
            <h6 class="filter-title">
              Habilidades
            </h6>
            <div
              v-if="!dataReady"
              class="text-center text-primary my-2"
            >
              <b-spinner
                variant="primary"
                class="align-middle"
              />
              <strong class="ml-1">Cargando...</strong>
            </div>
            <b-form-radio-group
              v-model="filters.habilidades"
              class="categories-radio-group"
              stacked
              :options="filtersOptions.habilidades"
              @change="$emit('refetch-data')"
            />
          </div>
        </b-card>
      </div>
    </div>

    <div
      class="body-content-overlay"
      :class="{'show': mqShallShowLeftSidebar}"
      @click="$emit('update:mq-shall-show-left-sidebar', false)"
    />
  </div>
</template>

<script>
import {
  BRow, BCol, BCard, BFormRadioGroup, BSpinner,
} from 'bootstrap-vue'
import VueSlider from 'vue-slider-component'

export default {
  components: {
    BRow,
    BCol,
    BFormRadioGroup,
    BCard,
    BSpinner,

    // 3rd Party
    VueSlider,
  },
  props: {
    filtersOptions: {
      type: Object,
      required: true,
    },
    dataReady: {
      type: Boolean,
      required: true,
    },
    filters: {
      type: Object,
      required: true,
    },
    mqShallShowLeftSidebar: {
      type: Boolean,
      required: true,
    },
  },
}
</script>

<style lang="scss">
@import '~@core/scss/vue/libs/vue-slider.scss';
</style>

<style lang="scss" scoped>
.categories-radio-group,
.brands-radio-group,
.price-range-defined-radio-group {
    ::v-deep > .custom-control {
    margin-bottom: 0.75rem;
  }
}
</style>
