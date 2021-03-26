import Vue from 'vue';
import VueRouter from 'vue-router';

import About from "@/pages/about"
import HomePage from "@/pages/home-page";
import LogIn from "@/pages/log-in";
import UserPage from "@/pages/user-page"

/*
Related to user operations.
 */
import UserWelcomePage from "@/pages/user-welcome-page"
import UserAccountPage from "@/pages/user-account-page"
import UserMakeAppointment from "@/pages/user-make-appointment"

const routes = [
    {path: '/', name: '/', component: HomePage},
    {path: '/about', component: About},
    {path: "/log-in", component: LogIn},
    {
        path: "/user",
        component: UserPage,
        children: [
            {
                path: "welcome",
                component: UserWelcomePage
            },
            {
                path: "account",
                component: UserAccountPage
            },
            {
                path: "make-appointment",
                component: UserMakeAppointment
            }
        ]
    },
];

Vue.use(VueRouter);

export default new VueRouter({routes});