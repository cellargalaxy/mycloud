// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import http from './utils/http'
import format from './utils/formatDate'
import formatFileSize from './utils/formatFileSize'

Vue.use(ElementUI);
Vue.config.productionTip = false;
Vue.prototype.$http = http;
Vue.prototype.$formatDate = format;
Vue.prototype.$formatFileSize = formatFileSize;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
});
