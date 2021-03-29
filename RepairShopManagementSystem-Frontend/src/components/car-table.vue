<template>
  <div class="root">
    <div class="container">
      <div class="car-row">
        <div class="plate-column title-font">PLATE NO</div>
        <div class="model-column title-font">MODEL</div>
        <div class="year-column title-font">YEAR</div>
        <div class="manufacturer-column title-font">MANUFACTURER</div>
        <div class="select-column title-font" v-if="!selecting">Update</div>
        <div class="select-column title-font" v-else>Select</div>
      </div>
      <transition name="fade" mode="out-in">
        <div v-if="this.cars.length === 0"
             style="width: 100%; height: 2em; font-size: 30px; text-align: center; margin-top: 40px">
          Sorry, you do not have any car yet.
        </div>
        <transition-group name="list-complete" tag="div" v-else>
          <div class="car-row" v-for="car in cars" :key="car.year">
            <div class="plate-column">{{ car.plateNo }}</div>
            <div class="model-column">{{ car.model }}</div>
            <div class="year-column">{{ car.year }}</div>
            <div class="manufacturer-column">{{ car.manufacturer }}</div>
            <div class="select-column select-button" @click="updateCar(car)" v-if="!selecting">
              Update
            </div>
            <transition name="fade" mode="out-in" v-else>
              <div class="select-column" v-if="isSelected(car.plateNo)">
                Selected
              </div>
              <div v-else class="select-column select-button"
                   @click="selectACar(car.plateNo); $emit('carSelected', `${car.plateNo}`);">
                Select
              </div>
            </transition>
          </div>
        </transition-group>
      </transition>
    </div>

    <slideout-panel></slideout-panel>
  </div>
</template>

<script>

export default {
  name: "appointment-table",
  data: function () {
    return {
      cars: Array,
      carSelected: "",
      panelResult: null,
    }
  },
  props: {
    customerInfo: Object,
    selecting: Boolean
  },
  created() {
    // Setting up
    this.cars = this.customerInfo.cars;
  },
  methods: {
    updateCar: function (car) {
      this.panelResult = this.$showPanel({
        component: 'car-update-form',
        openOn: "right",
        props: {
          username: this.customerInfo.username,
          plateNo: car.plateNo
        }
      })

      this.panelResult.promise.then(resp => {
        console.debug(resp.data);
        this.panelResult.hide();
      })
    },
    isSelected: function (plateNo) {
      return this.carSelected === plateNo
    },
    selectACar: function (plateNo) {
      this.carSelected = plateNo;
    }
  },
  computed: {}
}
</script>

<style scoped>
* {
  font-family: Roboto, sans-serif;
}

.container {
  width: 85%;
  height: max-content;
  margin-top: 20px;
  padding-left: 60px;
}

.car-row {
  width: 95%;
  height: 2.7em;
  display: table;
  border-bottom: gray dashed 1px;
}

.plate-column {
  display: table-cell;
  width: 20%;
  height: 100%;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;
}

.model-column {
  display: table-cell;
  width: 20%;
  height: 100%;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;
}

.year-column {
  display: table-cell;
  width: 20%;
  height: 100%;
  font-family: Roboto, sans-serif;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;
}

.manufacturer-column {
  display: table-cell;
  width: 20%;
  height: 100%;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;
}

.select-column {
  display: table-cell;
  width: 20%;
  height: 100%;
  font-family: Roboto, sans-serif;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;
}

.title-font {
  font-family: "Playfair Display SC", serif;
}

.select-button {
  color: lightblue;
  font-style: italic;
  transition: .5s ease;
}

.select-button:hover {
  color: blue;
  transition: .5s ease;
}

.list-complete-enter-from,
.list-complete-leave-to {
  opacity: 0;
}

.list-complete-leave-active {
  position: absolute;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.7s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.pay-enter-active,
.pay-leave-active {
  transition: opacity 0.7s ease;
}

.pay-enter-from,
.pay-leave-to {
  opacity: 0;
}

.delete-enter-active,
.delete-leave-active {
  transition: opacity 0.7s ease;
}

.delete-enter-from,
.delete-leave-to {
  opacity: 0;
}
</style>