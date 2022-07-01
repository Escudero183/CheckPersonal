<template>
  <div>
    <b-card
      v-for="(data) in posts"
      :key="data.avatar"
    >
      <div
        class="d-flex justify-content-start align-items-center mb-1"
      >
        <!-- avatar -->
        <b-avatar
          :src="data.avatar"
          size="50"
          class="mr-1"
        />
        <!--/ avatar -->
        <div class="profile-user-info">
          <h6 class="mb-0">
            {{ data.capacitacion }}
          </h6>
          <small class="text-muted">{{ data.fechaEmision }}</small>
        </div>
      </div>

      <b-button
        v-ripple.400="'rgba(255, 255, 255, 0.15)'"
        size="sm"
        variant="primary"
      >
        {{ data.centroCertifica }}
      </b-button>
    </b-card>
  </div>
</template>

<script>
import {
  BAvatar, BCard, VBTooltip, BButton,
} from 'bootstrap-vue'
import Ripple from 'vue-ripple-directive'
import { kFormatter } from '@core/utils/filter'

export default {
  components: {
    BAvatar,
    BCard,
    BButton,
  },
  directives: {
    'b-tooltip': VBTooltip,
    Ripple,
  },
  props: {
    posts: {
      type: Array,
      default: () => [],
    },
  },
  methods: {
    kFormatter,
  },
}
</script>
