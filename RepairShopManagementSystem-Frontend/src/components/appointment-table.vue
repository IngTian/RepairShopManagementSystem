<template>
  <div class="root">
    <div class="container">
      <div class="shift-row">
        <div class="date-column title-font">DATE</div>
        <div class="start-time-column title-font">START TIME</div>
        <div class="end-time-column title-font">END TIME</div>
        <div class="service-type-column title-font">SERVICE TYPE</div>
        <div class="price-column title-font">PRICE</div>
        <div class="select-column title-font">PAY</div>
      </div>

      <transition-group name="list-complete" tag="div">
        <div class="shift-row" v-for="appointment in this.appointments" :key="getAppointmentDate(appointment)">
          <div class="date-column">{{ getAppointmentDate(appointment) }}</div>
          <div class="start-time-column">{{ getAppointmentStartTime(appointment) }}</div>
          <div class="end-time-column">{{ getAppointmentEndTime(appointment) }}</div>
          <div class="service-type-column">{{ getAppointmentServiceType(appointment) }}</div>
          <div class="price-column">{{ getAppointmentPrice(appointment) }}</div>
          <transition name="fade" mode="out-in">
            <div class="select-column select-button" v-if="!getAppointmentIsPaid(appointment)"
                 @click="makePayment(appointment)">
              Pay
            </div>
            <div class="select-column" v-else>
              Paid
            </div>
          </transition>
        </div>
      </transition-group>
    </div>
  </div>
</template>

<script>
export default {
  name: "appointment-table",
  props: {
    appointments: Array
  },
  methods: {
    makePayment: function (appointment) {
      console.log(appointment)
      appointment.isPaid = true
    }
  },
  computed: {
    getAppointmentDate: function (appointment) {
      let shift = appointment.shift;
      return shift.date;
    },
    getAppointmentStartTime: function (appointment) {
      let shift = appointment.shift;
      return shift.startTime;
    },
    getAppointmentEndTime: function (appointment) {
      let shift = appointment.shift;
      return shift.endTime;
    },
    getAppointmentServiceType: function (appointment) {
      let service = appointment.service;
      return service.serviceType;
    },
    getAppointmentPrice: function (appointment) {
      let totalBillPrice = 0;
      let bills = appointment.bill;
      for (let i = 0; i < bills.length; i++)
        if (!bills[i].isPaid)
          totalBillPrice += bills[i].price;
      return totalBillPrice;
    },
    getAppointmentIsPaid: function (appointment) {
      let bills = appointment.bill;
      for (let i = 0; i < bills.length; i++)
        if (!bills[i].isPaid)
          return false;
      return true;
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

.shift-row {
  width: 95%;
  height: 2.7em;
  display: table;
  border-bottom: gray dashed 1px;
}

.date-column {
  display: table-cell;
  width: 20%;
  height: 100%;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;
}

.start-time-column {
  display: table-cell;
  width: 20%;
  height: 100%;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;
}

.end-time-column {
  display: table-cell;
  width: 20%;
  height: 100%;
  font-family: Roboto, sans-serif;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;
}

.service-type-column {
  display: table-cell;
  width: 20%;
  height: 100%;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;
}

.price-column {
  display: table-cell;
  width: 10%;
  height: 100%;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;
}

.select-column {
  display: table-cell;
  width: 10%;
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
  transition: opacity 0.4s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>