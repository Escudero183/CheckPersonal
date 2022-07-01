<template>
  <div
    v-if="Object.keys(dataProfile).length"
    id="user-profile"
  >
    <profile-header :header-data="dataProfile.data_personal" />
    <!-- profile info  -->
    <section id="profile-info">
      <b-row>
        <!-- about suggested page and twitter feed -->
        <b-col
          lg="3"
          cols="12"
          order="2"
          order-lg="1"
        >
          <profile-about :about-data="dataProfile.data_personal" />
          <profile-suggested-pages :pages-data="dataProfile.data_ha" />
        </b-col>
        <!--/ about suggested page and twitter feed -->

        <!-- post -->
        <b-col
          lg="6"
          cols="12"
          order="1"
          order-lg="2"
        >
          <profile-post :posts="dataProfile.data_ca" />
        </b-col>
        <!-- post -->

        <!-- latest photos suggestion and polls -->
        <b-col
          lg="3"
          cols="12"
          order="3"
        >
          <profile-suggestion :suggestions="dataProfile.data_fa" />
        </b-col>
        <!--/ latest photos suggestion and polls -->

        <!-- load more  -->
        <b-col
          cols="12"
          order="4"
        >
          <profile-bottom />
        </b-col>
        <!--/ load more  -->
      </b-row>
    </section>
    <!--/ profile info  -->
  </div>
</template>

<script>
import { BRow, BCol } from 'bootstrap-vue'
import store from '@/store'

import ProfileHeader from './ProfileHeader.vue'
import ProfileAbout from './ProfileAbout.vue'
import ProfileSuggestedPages from './ProfileSuggestedPages.vue'
import ProfilePost from './ProfilePost.vue'
import ProfileSuggestion from './ProfileSuggestion.vue'
import profileBottom from './profileBottom.vue'

/* eslint-disable global-require */
export default {
  components: {
    BRow,
    BCol,

    ProfileHeader,
    ProfileAbout,
    ProfileSuggestedPages,
    ProfilePost,
    ProfileSuggestion,
    profileBottom,
  },
  data() {
    return {
      menuHidden: this.$store.state.appConfig.layout.menu.hidden,
      profileData: { },
      dataProfile: {},
      isLoad: false,
    }
  },
  created() {
    this.$store.commit('appConfig/UPDATE_NAV_MENU_HIDDEN', true)
    this.$http.get('/profile/data').then(res => { this.profileData = res.data })
  },
  destroyed() {
    this.$store.commit('appConfig/UPDATE_NAV_MENU_HIDDEN', this.menuHidden)
  },
  mounted() {
    this.getDatos()
  },
  methods: {
    async getDatos() {
      this.isLoad = false
      await store
        .dispatch('public/PUBLIC_PERSONAL_FIND_PROFILE', {
          numeroDocumento: this.$route.params.dni,
          idTipoDocumento: 1,
        })
        .then(response => {
          this.dataProfile = response
          this.isLoad = true
        })
        .catch(error => {
          this.$bvToast.toast(error, {
            title: 'Ha ocurrido un error',
            variant: 'danger',
            solid: true,
          })
        })
    },
  },
}
/* eslint-disable global-require */
</script>

<style lang="scss" >
@import '@core/scss/vue/pages/page-profile.scss';
</style>
