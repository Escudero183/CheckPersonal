<template>
  <div>
    <div class="business-card">
      <div class="business-items">
        <div
          v-for="perfil in perfiles"
          :key="perfil.idPerfil"
          class="business-item"
        >
          <div class="d-flex align-items-center justify-content-between">
            <b-form-checkbox checked="true">
              {{ perfil.nombre }}
            </b-form-checkbox>
            <b-badge variant="primary">
              {{ perfil.sistema.nombre }}
            </b-badge>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { BFormCheckbox, BBadge } from 'bootstrap-vue'
import store from '@/store'
import Ripple from 'vue-ripple-directive'

export default {
  components: {
    BFormCheckbox,
    BBadge,
  },
  directives: {
    Ripple,
  },
  emits: ['update:modal-show'],
  props: {
    dataUsuario: {
      type: Object,
      required: true,
    },
  },
  data: () => ({
    perfiles: [],
    isBusy: false,
    businessItems: [
      {
        option: 'Perfil #1', money: 'Sistema Reclutamiento', badgeColor: 'light-success', checked: false,
      },
      {
        option: 'Perfil #2', money: 'Sistema Reclutamiento', badgeColor: 'light-primary', checked: true,
      },
      {
        option: 'Perfil #3', money: 'Sistema Reclutamiento', badgeColor: 'light-success', checked: false,
      },
      {
        option: 'Perfil #4', money: 'Sistema Reclutamiento', badgeColor: 'light-success', checked: false,
      },
    ],
  }),
  mounted() {
    this.listarPerfiles()
  },
  methods: {
    listarPerfiles() {
      this.isBusy = true
      this.perfiles = []
      store
        .dispatch('seguridad/PERFIL_FIND_ALL', {
          idSistema: -1,
          limit: 100,
          query: '',
          page: 1,
          sortBy: 'idPerfil%7CDESC',
        })
        .then(response => {
          this.perfiles = response.items
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
    },
  },
}
</script>
