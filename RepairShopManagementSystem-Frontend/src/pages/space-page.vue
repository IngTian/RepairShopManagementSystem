<template>
  <div class="root">
    <div class="container">
      <div class="section">
        <section-title title="Create a space" sub-title="after all, you need somewhere to work"></section-title>
        <div class="view-info-row">
          <div class="view-info-row-description">Maximum weight load:</div>
          <div class="view-info-row-information">
            <input class="form-input" v-model="weightload" key="input">
          </div>
        </div>





        <div
            style="display: flex; width: 60%; height: 100px; flex-direction: row; align-items: center; justify-content: space-around; margin-top: 30px; margin-left: 15%">
          <div style="width: max-content">
            <action-button background-color="black" text="Add"
                           v-on:clicked="addSpaceClicked"
                           style="width: 150px"></action-button>
          </div>

        </div>
      </div>

    </div>
  </div>
</template>

<script>

export default {
  name: "space-page",
  data: function () {
    return {
      customerInfo: Object,
      isUpdatingBasicInformation: false,
      weightload:"",


    }
  },
  methods :{
    
    addSpaceClicked :function(){

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