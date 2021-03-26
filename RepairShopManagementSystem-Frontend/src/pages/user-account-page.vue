<template>
  <div class="root">
    <div class="container">
      <div class="section">
        <section-title title="View Basic Info" sub-title="You looks great!"></section-title>
        <div class="view-info-row">
          <div class="view-info-row-description">Username:</div>
          <div class="view-info-row-information">{{ getUsername }}</div>
        </div>
        <div class="view-info-row">
          <div class="view-info-row-description">Password:</div>
          <transition name="fade" mode="out-in">
            <div class="view-info-row-information" v-if="!isUpdatingBasicInformation" key="display">{{
                getPassword
              }}
            </div>
            <div class="view-info-row-information" v-else>
              <input class="form-input" v-model="updatedPassword" key="input">
            </div>
          </transition>
        </div>
        <div class="view-info-row">
          <div class="view-info-row-description">Name:</div>
          <transition name="fade" mode="out-in">
            <div class="view-info-row-information" v-if="!isUpdatingBasicInformation" key="display">{{
                getName
              }}
            </div>
            <div class="view-info-row-information" v-else>
              <input class="form-input" v-model="updatedName" key="input">
            </div>
          </transition>
        </div>
        <div class="view-info-row">
          <div class="view-info-row-description">Address:</div>
          <transition name="fade" mode="out-in">
            <div class="view-info-row-information" v-if="!isUpdatingBasicInformation" key="display">{{
                getAddress
              }}
            </div>
            <div class="view-info-row-information" v-else>
              <input class="form-input" v-model="updatedAddress" key="input">
            </div>
          </transition>
        </div>
        <div class="view-info-row">
          <div class="view-info-row-description">Phone No:</div>
          <transition name="fade" mode="out-in">
            <div class="view-info-row-information" v-if="!isUpdatingBasicInformation" key="display">{{
                getPhoneNo
              }}
            </div>
            <div class="view-info-row-information" v-else>
              <input class="form-input" v-model="updatedPhoneNo" key="input">
            </div>
          </transition>
        </div>
        <div class="view-info-row">
          <div class="view-info-row-description">Email:</div>
          <transition name="fade" mode="out-in">
            <div class="view-info-row-information" v-if="!isUpdatingBasicInformation" key="display">{{
                getEmail
              }}
            </div>
            <div class="view-info-row-information" v-else>
              <input class="form-input" v-model="updatedEmail" key="input">
            </div>
          </transition>
        </div>
        <div
            style="display: flex; width: 60%; height: 100px; flex-direction: row; align-items: center; justify-content: space-around; margin-top: 30px; margin-left: 15%">
          <div style="width: max-content">
            <action-button background-color="black" :text="getEditInfoButtonText"
                           v-on:clicked="isUpdatingBasicInformation=!isUpdatingBasicInformation"
                           style="width: 150px"></action-button>
          </div>
          <div style="width: max-content">
            <action-button background-color="black" text="Update" style="width: 150px" v-on:clicked="updateUserInfomationClicked"></action-button>
          </div>
        </div>
      </div>
      <div class="section">
        <section-title title="Appointments" sub-title="see booked appointments"></section-title>
        <appointment-table v-bind:appointments="getAppointments"></appointment-table>
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
  name: "user-account-page",
  data: function () {
    return {
      customerInfo: Object,
      isUpdatingBasicInformation: false,
      updatedPassword: "",
      updatedEmail: "",
      updatedPhoneNo: "",
      updatedAddress: "",
      updatedName: "",
    }
  },
  methods:{
    updateUserInfomationClicked:function(){
     let password=this.updatedPassword;
     let name=this.updatedName;
     let address=this.updatedAddress;
     let phoneNo=this.updatedPhoneNo;
     let email=this.email;
      let response = Object
      AXIOS.post("users/customers/update_info",
          {
           //request body
            username: this.getUsername,
            password: this.getPassword,
            name: this.getName,
            phoneNo: this.getPhoneNo,
            homeAddress: this.getAddress,
            email: this.getEmail,
          },
          {
            params:{
            newUsername:this.getUsername,
              newPassword:password,
              newName:name,
              newPhoneNo:phoneNo,
              newAddress:address,
              newEmail:email,




            }
      }

      ).then(resp => {
        response = resp;
        console.log(response)
      }).catch(e => {
        console.error(e.toString())
      })


    }
  },

  mounted() {
    // Load user info from local storage.
    this.customerInfo = JSON.parse(localStorage.getItem('userInformation'));
  },
  computed: {
    getUsername: function () {
      return this.customerInfo.username;
    },

    getPassword: function () {
      return this.customerInfo.password;
    },

    getName: function () {
      return this.customerInfo.name;
    },

    getAddress: function () {
      return this.customerInfo.address;
    },

    getEmail: function () {
      return this.customerInfo.email;
    },

    getPhoneNo: function () {
      return this.customerInfo.phoneNo;
    },

    getAppointments: function () {
      return this.customerInfo.appointments;
    },

    getEditInfoButtonText: function () {
      return this.isUpdatingBasicInformation ? "Abort" : "Edit";
    }
  }
}
</script>

<style scoped>
.root {
  width: 100%;
  height: max-content;
  margin-top: 40px;
}

.container {
  width: 90%;
  height: max-content;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  margin-left: auto;
  margin-right: auto;
}

.section {
  width: 90%;
  margin-top: 50px;
  margin-bottom: 20px;
}

.view-info-row {
  width: 100%;
  height: 2.7em;
  margin-top: 10px;
  margin-bottom: 10px;
  display: table;
}

.view-info-row-description {
  height: 100%;
  display: table-cell;
  text-align: right;
  line-height: 2em;
  font-size: 20px;
  font-family: Roboto, sans-serif;
}

.view-info-row-information {
  width: 60%;
  height: 100%;
  padding-left: 10px;
  display: table-cell;
  text-align: left;
  line-height: 2em;
  font-size: 20px;
  font-family: "Times New Roman", serif;
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

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s ease-in-out;
}

.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */
{
  opacity: 0;
}


</style>