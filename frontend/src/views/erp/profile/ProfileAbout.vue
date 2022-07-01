<template>
  <b-card>
    <!-- about -->
    <div>
      <h5 class="text-capitalize mb-75">
        Presentación
      </h5>
      <b-card-text>
        <span v-html="aboutData.presentacion" />
      </b-card-text>

      <h5 class="mt-2 mb-75">
        Fecha de Nacimiento
      </h5>
      <b-card-text>
        {{ aboutData.fechaNacimiento }}
      </b-card-text>

      <h5 class="mt-2 mb-75">
        Reside en
      </h5>
      <b-card-text>
        {{ aboutData.ubigeoAll.dist }}
      </b-card-text>

      <h5 class="mt-2 mb-75">
        Celular
      </h5>
      <b-card-text>
        {{ aboutData.celular }}
      </b-card-text>

      <h5 class="mt-2 mb-75">
        Correo
      </h5>
      <b-card-text>
        {{ aboutData.correo }}
      </b-card-text>
    </div>
  </b-card>
</template>

<script>
import { BCard, BCardText } from 'bootstrap-vue'

export default {
  components: {
    BCard,
    BCardText,
  },
  props: {
    aboutData: {
      type: Object,
      default: () => {},
    },
  },
}
</script>
