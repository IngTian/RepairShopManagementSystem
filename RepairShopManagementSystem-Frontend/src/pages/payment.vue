<template>
  <div class="root" style="height: 1000px; width: 100%; position: relative;">
    <div class="pay-form">
      <div class="PayPalPay-CreditCardPay-selector">
        <div class="selector" v-bind:style="getCreditCardPaySelectorStyle" @click="CreditCardPaySelectorClicked">
          <div class="selector-box">Credit Card</div>
        </div>
        <div class="selector" v-bind:style="getPayPalPaySelectorStyle" @click="PayPalPaySelectorClicked">
          <div class="selector-box">PayPal</div>
        </div>
      </div>
      <transition name="fade" mode="out-in">
        <div v-if="displayCreditCard" class="form-container" key="creditCardForm">
          <div class="title">
            Total: {{getPrice}}  $
          </div>
          <input class="form-input" v-model="cardNum" placeholder="Card Number" style="width: 76%; margin-bottom: 20px">
          <div style="height: 3em; width: 80%; margin-bottom: 10px">
            <input class="form-input" v-model="expiration" placeholder="Expiration(MM/YY)" style="float: left; width: 150px">
            <input class="form-input" v-model="securityCode" placeholder="Card Security Code" style="float: right; width: 150px">
          </div>
          <action-button background-color="black" text="Confirm"
                         style="width: 200px; height: 60px" v-on:clicked="paymentClicked"></action-button>
        </div>
        <div v-else class="form-container" key="payPalForm">
          <div class="title">
            Total: {{getPrice}}  $
          </div>
          <input class="form-input" v-model="usernamePayPal" placeholder="username" style="width: 76%; margin-bottom: 20px">
          <input class="form-input" v-model="passwordPayPal" placeholder="password" style="width: 76%; margin-bottom: 20px">
          <action-button background-color="black" text="Confirm"
                         style="width: 200px; height: 60px"></action-button>
        </div>
      </transition>

      <div style="display: table">
        <div style="display: table-cell"></div>
        <div style="display: table-cell"></div>
      </div>

    </div>
  </div>
</template>

<script>
import axios from "axios"
var AXIOS = axios.create({
  baseURL: "http://localhost:8080",
})
export default {
  name: "payment",
  data: function () {
    return {
      displayCreditCard: true,
      displayPayPal: false,
      cardNum: "",
      expiration: "",
      securityCode: "",
      usernamePayPal: "",
      passwordPayPal: "",
      customer: Object,
      selectedStyle: {
        backgroundColor: "black"
      },
    }
  },
  mounted() {
    this.customer = JSON.parse(localStorage.getItem('userInformation'))
  },
  computed: {
    getPrice: function (appointmentNum) {
      var i = 0;
      var price = 0;
      var len = this.customer.appointments.length;
      for (; i < len;) {
        if (this.customer.appointments[i].appointmentId == appointmentNum) {
          return this.customer.appointments[i].price;
        }
        i++;
      }
      return price;
    },
    getCreditCardPaySelectorStyle: function () {
      return this.displayCreditCard ? this.selectedStyle : null;
    },
    getPayPalPaySelectorStyle: function () {
      return this.displayPayPal ? this.selectedStyle : null;
    }
  },
  methods: {
    CreditCardPaySelectorClicked: function () {
      this.displayPayPal = false;
      this.displayCreditCard = true;
    },
    PayPalPaySelectorClicked: function () {
      this.displayPayPal = true;
      this.displayCreditCard = false;
    },
    paymentClicked: function () {
      var i = 0;
      var len = this.customer.appointments.length;
      for (; i < len;) {
        if (this.customer.appointments[i].appointmentNum == this.appointmentNum) {
          this.customer.appointments[i].isPaid == true;
        }
        i++;
      }

    }
  }
}
</script>
<style scoped>
.pay-form {
  width: 40em;
  height: max-content;
  padding-bottom: 40px;
  background-color: whitesmoke;
  margin-left: auto;
  margin-right: auto;
  margin-top: 100px;
  display: flex;
  flex-direction: column;
  align-items: center;
  align-content: space-around;
  justify-content: flex-start;
}
.PayPalPay-CreditCardPay-selector {
  width: 80%;
  height: 5em;
  margin-top: 50px;
  margin-left: auto;
  margin-right: auto;
  display: table;
  vertical-align: center;
}
.selector {
  display: table-cell;
  vertical-align: middle;
  background-color: silver;
  color: white;
  width: 50%;
}
.selector-box {
  height: 100%;
  width: 100%;
  text-align: center;
  line-height: 100px;
  font-size: 1.5em;
  font-family: Roboto, sans-serif;
  transition: ease .5s;
}
.selector-box:hover {
  background-color: darkgray;
}
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s ease-in-out;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */
{
  opacity: 0;
}
.title {
  width: 80%;
  height: 80px;
  margin-top: 30px;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
  line-height: 80px;
  font-size: 40px;
  font-family: Roboto, sans-serif;
}
.form-container {
  width: 80%;
  height: max-content;
  margin-top: 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
}
.form-input {
  display: block;
  height: 1.5em;
  padding: 5px 10px;
  background-color: white;
  outline: none;
  border: gray solid 1px;
  font-family: sans-serif;
  font-size: 18px;
  text-decoration: none;
  transition: border-color .4s ease, box-shadow .4s ease;
}
.form-input:focus {
  border: red 1px solid;
  transition: border-color .4s ease, box-shadow .4s ease;
}
</style>