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
             style="width: 100%; height: 2em; font-size: 30px; text-align: center; margin-top: 40px" key="no-car">
          Sorry, you do not have any car yet.
        </div>
        <div v-else key="has-car">
          <transition-group name="slide-fade" tag="div">
            <div class="car-row" v-for="car of cars" :key="car.plateNo">
              <div class="plate-column">{{ car.plateNo }}</div>
              <div class="model-column">{{ car.model }}</div>
              <div class="year-column">{{ car.year }}</div>
              <div class="manufacturer-column">{{ car.manufacturer }}</div>
              <div class="select-column select-button" @click="updateCar(car)" v-if="!selecting">
                Update
              </div>
              <transition name="fade" mode="out-in" v-else>
                <div class="select-column" v-if="isSelected(car.plateNo)" key="selected">
                  Selected
                </div>
                <div v-else class="select-column select-button"
                     @click="selectACar(car.plateNo); $emit('carSelected', `${car.plateNo}`);" key="select">
                  Select
                </div>
              </transition>
            </div>
          </transition-group>
        </div>
      </transition>
    </div>

    <slideout-panel v-on:updated="carUpdated"></slideout-panel>
  </div>
</template>

<script>

export default {
  name: "appointment-table",
  data: function () {
    return {
      carSelected: "",
      panelResult: null,
    }
  },
  props: {
    customerInfo: Object,
    selecting: Boolean,
  },
  methods: {
    updateCar: function (car) {

      this.panelResult = this.$showPanel({
        component: 'car-update-form',
        openOn: "right",
        width: '350px',
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
    carUpdated: function (event) {
      console.log(event);
    },
    selectACar: function (plateNo) {
      this.carSelected = plateNo;
    }
  },
  computed: {
    cars: function () {
      return this.customerInfo.cars;
    }
  }
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

  transition: ease .4s;
}

.year-column {
  display: table-cell;
  width: 20%;
  height: 100%;
  font-family: Roboto, sans-serif;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;

  transition: ease .4s;
}

.manufacturer-column {
  display: table-cell;
  width: 20%;
  height: 100%;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;

  transition: ease .4s;
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
</style>