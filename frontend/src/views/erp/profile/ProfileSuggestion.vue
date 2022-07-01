<template>
  <b-card>
    <h5>Formación Académica</h5>

    <!-- user suggestion  -->
    <div
      v-for="(data,index) in suggestions"
      :key="data.idFormacionAcademica"
      class="d-flex justify-content-start align-items-center"
      :class="index == 0 ? 'mt-2' : 'mt-1'"
    >
      <div class="user-page-info">
        <h6 class="mb-0">
          {{ data.centroEstudio }}
        </h6>
        <small class="text-muted">{{ data.tipo }}</small>
      </div>
      <b-button
        v-ripple.400="'rgba(186, 191, 199, 0.15)'"
        variant="primary"
        class="btn-icon ml-auto"
        size="sm"
      >
        <small>{{ data.anio }}</small>
      </b-button>
    </div>
    <!--/ user suggestion  -->
  </b-card>
</template>

<script>
import {
  BCard, BButton,
} from 'bootstrap-vue'
import Ripple from 'vue-ripple-directive'

export default {
  components: {
    BCard,
    BButton,
  },
  directives: {
    Ripple,
  },
  props: {
    suggestions: {
      type: Array,
      default: () => [],
    },
  },
}
</script>
