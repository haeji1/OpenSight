import { createRouter, createWebHistory } from 'vue-router';
import IntroView from '@/views/intro/Intro.vue';
import LoginView from '@/views/auth/Login.vue';
import MainView from '@/views/dashboard/Main.vue';
import RegisterView from '@/views/auth/Register.vue'
import RegisterCompleteView from '@/views/auth/RegisterComplete.vue'
import FindEmail from '@/views/auth/FindEmail.vue'
import FindEmailComplete from '@/views/auth/FindEmailComplete.vue'
import FindEmailFail from '@/views/auth/FindEmailFail.vue'
import EditProfileView from '@/views/profile/EditProfile.vue'
import EditProfileCompleteView from '@/views/profile/EditProfileComplete.vue'
import PasswordFindView from '@/views/auth/PasswordFind.vue'
import PasswordFindFailView from '@/views/auth/PasswordFindFail.vue'
import PasswordChangeView from '@/views/auth/PasswordChange.vue'
import PasswordChangeCompleteView from '@/views/auth/PasswordChangeComplete.vue'
import OpenAccountView from '@/views/account/OpenAccount.vue';
import OpenAccountTermsView from '@/views/account/OpenAccountTerms.vue';
import OpenAccountCompleteView from '@/views/account/OpenAccountComplete.vue';
import SavingsView from '@/views/savings/Savings.vue';
import TransactionsView from '@/views/transactions/Transactions.vue';
import TransferView from '@/views/transfer/Transfer.vue'
import TransferConfirmView from '@/views/transfer/TransferConfirm.vue'
import TransferCompleteView from '@/views/transfer/TransferComplete.vue'
import TransferVerifyView from '@/views/transfer/TransferVerify.vue';
import ChatbotView from '@/views/Chatbot.vue';
import SimplePasswordSettingView from '@/views/account/SimplePasswordSetting.vue';
import SocialLoginView from '@/views/dashboard/SocialLogin.vue';
import ChatbotView2 from '@/views/ChatbotSpring.vue';
import AccountSettingView from '@/views/account/AccountSetting.vue';
import SettingView from '@/views/account/Setting.vue';

const requireAuth = () => (from, to, next) => {
  if (!sessionStorage.getItem("accessToken")) {
    window.alert("로그인이 필요한 서비스입니다. 로그인을 해주세요");
    next("/login");
  }
  next();
};


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: LoginView
    },
    {
      path: '/main',
      name: 'Main',
      component: MainView,
      beforeEnter: requireAuth(),
    },
    {
      path: '/',
      name: 'Intro',
      component: IntroView
    },
    {
      path: '/register',
      name: 'Reigster',
      component: RegisterView
    },
    {
      path: '/register/complete',
      name: 'RegisterComplete',
      component: RegisterCompleteView
    },
    {
      path: '/find-email',
      name: 'FindEmail',
      component: FindEmail
    },
    {
      path: '/find-email/complete',
      name: 'FindEmailComplete',
      component: FindEmailComplete
    },
    {
      path: '/find-email/fail',
      name: 'FindEamilFail',
      component: FindEmailFail
    },
    {
      path: '/profile/edit',
      name: 'EditProfile',
      component: EditProfileView,
      beforeEnter: requireAuth(),
    },
    {
      path: '/profile/edit-complete',
      name: 'EditProfileComplete',
      component: EditProfileCompleteView,
      beforeEnter: requireAuth(),
    },
    {
      path: '/find-password',
      name: 'PasswordFind',
      component: PasswordFindView
    },
    {
      path: '/find-password/fail',
      name: 'PasswordFindFail',
      component: PasswordFindFailView
    },
    {
      path: '/password/change',
      name: 'PasswordChange',
      component: PasswordChangeView
    },
    {
      path: '/password/change-complete',
      name: 'PasswordChangeComplete',
      component: PasswordChangeCompleteView
    },
    {
      path: '/account/open/select-bank',
      name: 'OpenAccount',
      component: OpenAccountView,
      beforeEnter: requireAuth(),
    },
    {
      path: '/account/open/terms',
      name: 'OpenAccountTerms',
      component: OpenAccountTermsView,
      beforeEnter: requireAuth(),
    },
    {
      path: '/account/open/complete',
      name: 'OpenAccountComplete',
      component: OpenAccountCompleteView,
      beforeEnter: requireAuth(),
    },
    {
      path: '/savings',
      name: 'Savings',
      component: SavingsView,
      beforeEnter: requireAuth(),
    },
    {
      path: '/transactions',
      name: 'Transactinos',
      component: TransactionsView,
      beforeEnter: requireAuth(),
    },
    {
      path: '/transfer',
      name: 'Transfer',
      component: TransferView,
      beforeEnter: requireAuth(),
    },
    {
      path: '/transfer/confirm',
      name: 'TransferConfirm',
      component: TransferConfirmView,
      beforeEnter: requireAuth(),
    },
    {
      path: '/transfer/complete',
      name: 'TransferComplete',
      component: TransferCompleteView,
      beforeEnter: requireAuth(),
    },
    {
      path: '/transfer/verify',
      name: 'TransferVerify',
      component: TransferVerifyView,
      beforeEnter: requireAuth(),
    },
    {
      path: '/chatbot',
      name: 'Chatbot',
      component: ChatbotView,
      beforeEnter: requireAuth(),
    },
    {
      path: '/chatbot2',
      name: 'Chatbot2',
      component: ChatbotView2,
      beforEnter: requireAuth()
    },
    {
      path: '/password/setting',
      name: 'SimplePasswordSetting',
      component: SimplePasswordSettingView,
      beforeEnter: requireAuth(),
    },
    {
      path: '/login/social',
      name: 'SocialLogin',
      component: SocialLoginView
    },
    {
      path: '/setting',
      name: 'Setting',
      component: SettingView,
      beforeEnter: requireAuth(),
    },
    {
      path: '/account/setting',
      name: 'AccountSetting',
      component: AccountSettingView,
      beforeEnter: requireAuth(),
    }
  ]
})

export default router