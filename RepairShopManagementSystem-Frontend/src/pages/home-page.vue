<template>
  <div class="root">
    <div class="main-info-section">
      <div class="blur">
        <div class="shade"></div>
        <div class="motto">
          <h1>Trust us with your car!!</h1>
        </div>
      </div>
    </div>


    <div class="Shop-Information">
      <section-title title="Mcgill Repair Shop" sub-title="Service provided"></section-title>
      <horizontal-gallery :images="shopImages"></horizontal-gallery>
      <horizontal-gallery :images="shopImagessub"></horizontal-gallery>
    </div>

    <section-title title="Discount" sub-title="Take care of your love car with amazing discount!!"></section-title>
    <horizontal-gallery :images="galleryImages"></horizontal-gallery>
  </div>

</template>

<script>

// Local imports

import SectionTitle from "@/components/section-title";
import HorizontalGallery from "@/components/horizontal-gallery";


export default {
  name: "home-page",
  components: {HorizontalGallery, SectionTitle},
  data: function () {
    return {
      galleryImages: [
        {
          fileName: "15discount.jpeg",
          title: "For the first time user",

          id: 1
        },
        {
          fileName: "20discount.jpeg",
          title: "Invite your friend!!",

          id: 2
        },
        {fileName: "20discount.jpeg", title: "Take a one year plan with us!!", id: 3},

      ],
      shopImages: [
        {
          fileName: "carwash.jpeg",
          title: "     Car Wash",

          id: 1
        },
        {
          fileName: "maintenace.png",
          title: "    Maintenance",

          id: 2
        },
        {fileName: "tire.jpeg", title: "Change tire", id: 3},
        {fileName: "latin_language.jpg", title: "Oxford Latin Course", description: "Repair", id: 4},

      ],
      shopImagessub: [
        {
          fileName: "repair.jpeg",
          title: "Repair",

          id: 1
        },
        {
          fileName: "modified.jpeg",
          title: "Modification",

          id: 2
        },
        {fileName: "tel.jpg", title: "Consultation", id: 3},


      ],
      activities: [
        {
          imageFileName: "LeetCode500.png",
          titleFlag: "Per Aspera Ad Astra",
          title: "Ace Interview!",
          description:"Complete 500 questions on LeetCode.",
          id: 1
        },
        {
          imageFileName: "django_development.png",
          titleFlag: "I LIKE THE WAY YOU DIE, BOY",
          title: "Software development",
          description:"Turn ideas into numbers",
          id: 2
        },
        {
          imageFileName: "ml_goal.jpg",
          titleFlag: "The real 'Homo clusus?'",
          title: "Embrace the Age of AI",
          description:"Computer Vision, NLP, et cetera",
          id: 3
        },
      ]
    };
  },
  methods:{
    getImagePath: function (imageFileName){
      return require(`.././assets/img/${imageFileName}`)
    }
  }
}
</script>

<style scoped>

.main-info-section {
  height: 90vh;
  width: 100%;
  background-image: url("../../src/assets/img/carshop.jpg");

  background-attachment: fixed;
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;

  display: flex;
  align-items: center;
  flex-direction: row;
  justify-content: center;
}

.blur {
  background: rgba(0, 0, 0, 0.05);
  border-radius: 15px;
  backdrop-filter: blur(1px);
  margin-top: 300px;
  height: max-content;
  width: max-content;
}
.Shop-Information{
  align-items: center;
}
.motto {
  margin-top: 0;
  font-size: 35px;
  font-family: "Times New Roman", sans-serif;

  color: black;
}

.projects{
  width: 85%;
  margin-left: auto;
  margin-right: auto;
  border-radius: 8px;
  overflow: hidden;
}

.project{
  width: 100%;
  height: 500px;
  position: relative;

  background-size: cover;
  background-position: center;
  filter: sepia(50%);
  transition: 0.4s ease;
}

.project:hover{
  filter: sepia(0);
  transition: 0.8s ease;
}

.activity-promotion{
  width: 30%;
  height: 100%;
  margin-left: 80px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
}

.activity-dash{
  background: #b10000;
  height: 3px;
  margin-bottom: 10px;
  width: 30px
}

.activity-flag, .activity-title, .activity-description{
  color: white;
}

.activity-flag, .activity-description{
  font-family: Roboto, sans-serif;
}

.activity-flag{
  font-size: 35px;
}

.activity-description{
  font-size: 40px;
}

.activity-title{
  font-size: 50px;
  font-family: "Playfair Display SC", serif;
  font-weight: 600;
}

</style>