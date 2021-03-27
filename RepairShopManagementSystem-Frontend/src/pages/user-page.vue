<template>
  <div class="root">
    <user-navigation-panel></user-navigation-panel>
    <transition name="fade" mode="out-in">
      <router-view class="content"></router-view>
    </transition>
  </div>
</template>

<script>
export default {
  name: "user-page",
  data:function(){
    return{
      username:"",
      name:"",
      password:"",
      address:"",
      phoneNo:"",
      email:"",

    }
  },
  mounted() {

    let userInformation = {
      name: localStorage.getItem('userInformation').name,
      username: localStorage.getItem('userInformation').username,
      password: localStorage.getItem('userInformation').password,
      address: localStorage.getItem('userInformation').address,
      phoneNo: localStorage.getItem('userInformation').phoneNo,
      email: localStorage.getItem('userInformation').email,

      // Some appointments
      appointments: [
        {
          date: "2021-03-25",
          startTime: "09:25",
          endTime: "10:38",
          serviceType: "Study arts",
          price: "$100",
          isPaid: false
        },
        {
          date: "2021-03-24",
          startTime: "09:25",
          endTime: "10:38",
          serviceType: "Study arts",
          price: "$100",
          isPaid: false
        }
      ]
    }
    if (!localStorage.getItem('userInformation'))
      localStorage.setItem('userInformation', JSON.stringify(userInformation))
  }
}
</script>

<style scoped>
.root {
  width: 100%;
  height: 100%;

  display: grid;
  grid-template-columns: auto 1fr;
  grid-template-rows: auto;
  grid-template-areas:
    "nav content"
}

.content {
  grid-area: content;
  grid-template: none;
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