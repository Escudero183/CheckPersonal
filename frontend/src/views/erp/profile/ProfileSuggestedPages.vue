<template>
  <b-card>
    <h5 class="mb-2">
      Habilidades Técnicas
    </h5>

    <!-- suggested pages -->
    <div
      v-for="(data,index) in pagesData"
      :key="index"
      class="d-flex justify-content-start align-items-center mt-1"
    >
      <div class="mr-1">
        <b-avatar
          size="40"
          :src="data.avatar"
        />
      </div>
      <div class="profile-user-info">
        <h6 class="mb-0">
          {{ data.habilidad }}
        </h6>
        <small class="text-muted">{{ data.nivel }}%</small>
      </div>
      <div class="profile-star ml-auto">
        <feather-icon
          icon="StarIcon"
          size="18"
          class="profile-favorite"
        />
      </div>
    </div>
    <!--/ suggested pages -->
  </b-card>
</template>

<script>
import { BCard, BAvatar } from 'bootstrap-vue'

export default {
  components: {
    BCard,
    BAvatar,
  },
  props: {
    pagesData: {
      type: Array,
      default: () => [],
    },
  },
}
</script>
