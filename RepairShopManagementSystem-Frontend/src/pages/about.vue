<template>
  <div class="about">
    <h1>{{ msg }}</h1>
    <h3>{{getBusinessName}} </h3>
    <h3>Contact Number</h3>
    <ul>
        {{getBusinessPhoneNo}}
    </ul>
    <h3>Address</h3>
    <ul>
        {{getBusinessAddress}}
    </ul>
  </div>
</template>

<script>
export default {
  name: 'about',
  props: {
    msg: String
  },
  data: function () {
    return {
      businessInfo: Object
    }
  },
  mounted() {
    this.businessInfo = JSON.parse(localStorage.getItem('businessInfo'))
  },
  computed: {
    getBusinessName: function () {
      return this.businessInfo.businessName;
    },

    getBusinessAddress: function () {
      return this.businessInfo.businessAddress;
    },

    getBusinessPhoneNo: function () {
      return this.businessInfo.businessPhoneNo;
    },
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
