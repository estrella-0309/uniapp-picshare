import App from './App'

// #ifndef VUE3
import Vue from 'vue'
import store from './store'
import uView from 'uview-ui'
// import{ElementUI } from 'element-ui'
import { Tabs,TabPane, Upload, Dialog,Input,FormItem} from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(uView);
// Vue.use(ElementUI);
Vue.use(Tabs);
Vue.use(TabPane);
Vue.use(Upload);
Vue.use(Dialog);
Vue.use(Input);
Vue.use(FormItem);
Vue.config.productionTip = false
Vue.prototype.$store=store

App.mpType = 'app'
const app = new Vue({
	store,
    ...App
	
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif