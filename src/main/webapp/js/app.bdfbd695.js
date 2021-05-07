(function(e){function t(t){for(var r,u,a=t[0],c=t[1],s=t[2],f=0,p=[];f<a.length;f++)u=a[f],Object.prototype.hasOwnProperty.call(o,u)&&o[u]&&p.push(o[u][0]),o[u]=0;for(r in c)Object.prototype.hasOwnProperty.call(c,r)&&(e[r]=c[r]);l&&l(t);while(p.length)p.shift()();return i.push.apply(i,s||[]),n()}function n(){for(var e,t=0;t<i.length;t++){for(var n=i[t],r=!0,u=1;u<n.length;u++){var c=n[u];0!==o[c]&&(r=!1)}r&&(i.splice(t--,1),e=a(a.s=n[0]))}return e}var r={},o={1:0},i=[];function u(e){return a.p+"js/"+({}[e]||e)+"."+{2:"f41fa81f",3:"c412c529",4:"603dce52",5:"7e1a0562",6:"8bb02609",7:"c645064c"}[e]+".js"}function a(t){if(r[t])return r[t].exports;var n=r[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,a),n.l=!0,n.exports}a.e=function(e){var t=[],n=o[e];if(0!==n)if(n)t.push(n[2]);else{var r=new Promise((function(t,r){n=o[e]=[t,r]}));t.push(n[2]=r);var i,c=document.createElement("script");c.charset="utf-8",c.timeout=120,a.nc&&c.setAttribute("nonce",a.nc),c.src=u(e);var s=new Error;i=function(t){c.onerror=c.onload=null,clearTimeout(f);var n=o[e];if(0!==n){if(n){var r=t&&("load"===t.type?"missing":t.type),i=t&&t.target&&t.target.src;s.message="Loading chunk "+e+" failed.\n("+r+": "+i+")",s.name="ChunkLoadError",s.type=r,s.request=i,n[1](s)}o[e]=void 0}};var f=setTimeout((function(){i({type:"timeout",target:c})}),12e4);c.onerror=c.onload=i,document.head.appendChild(c)}return Promise.all(t)},a.m=e,a.c=r,a.d=function(e,t,n){a.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},a.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},a.t=function(e,t){if(1&t&&(e=a(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(a.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var r in e)a.d(n,r,function(t){return e[t]}.bind(null,r));return n},a.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return a.d(t,"a",t),t},a.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},a.p="/",a.oe=function(e){throw console.error(e),e};var c=window["webpackJsonp"]=window["webpackJsonp"]||[],s=c.push.bind(c);c.push=t,c=c.slice();for(var f=0;f<c.length;f++)t(c[f]);var l=s;i.push([0,0]),n()})({0:function(e,t,n){e.exports=n("2f39")},"0613":function(e,t,n){"use strict";(function(e){var r=n("4bde"),o=n("2f62"),i=n("e52e"),u=n("6968"),a=n("232b");t["a"]=Object(r["store"])((function(t){var n=t.Vue;n.use(o["a"]);var r=new o["a"].Store({modules:{RecommendationModule:i["a"],SocketModule:u["a"],LoginModule:a["a"]},strict:!!e.env.DEBUGGING});return r}))}).call(this,n("4362"))},"232b":function(e,t,n){"use strict";var r={namespaced:!0,state:function(){return{visible:!1,loginned:!1,user:{},auth:{},clientConfig:{}}},mutations:{setVisible:function(e,t){e.visible=t},setLoginned:function(e,t){e.loginned=t},setResponseUser:function(e,t){e.user=t},setAuth:function(e,t){e.auth=t},setClientConfig:function(e,t){e.clientConfig=t}},actions:{},modules:{},getters:{getVisible:function(e){return e.visible},getLoginned:function(e){return e.loginned},getResponseUser:function(e){return e.user},getAuth:function(e){return e.auth},getClientConfig:function(e){return e.clientConfig}}};t["a"]=r},"25d1":function(e,t,n){"use strict";var r=n("4572"),o=n("cc7d"),i=n.n(o),u=n("60a3"),a=n("ddaf"),c=function(){var e=function(t,n){return e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var n in t)t.hasOwnProperty(n)&&(e[n]=t[n])},e(t,n)};return function(t,n){function r(){this.constructor=t}e(t,n),t.prototype=null===n?Object.create(n):(r.prototype=n.prototype,new r)}}(),s=function(e,t,n,r){var o,i=arguments.length,u=i<3?t:null===r?r=Object.getOwnPropertyDescriptor(t,n):r;if("object"===typeof Reflect&&"function"===typeof Reflect.decorate)u=Reflect.decorate(e,t,n,r);else for(var a=e.length-1;a>=0;a--)(o=e[a])&&(u=(i<3?o(u):i>3?o(t,n,u):o(t,n))||u);return i>3&&u&&Object.defineProperty(t,n,u),u},f=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return c(t,e),t.prototype.connect=function(){var e=this;this.stompClient=r["Stomp"].over((function(){return new i.a("/steps")})),this.stompClient.connect({},(function(t){console.log("Connected: "+t),e.setConnectedState(!0),e.stompClient.subscribe("/user/topic/analyseSteps",(function(t){e.setMessage(t.body)}))}))},t=s([u["a"]],t),t}(Object(u["b"])(a["a"]));t["a"]=f},"2f39":function(e,t,n){"use strict";n.r(t);n("ac1f"),n("5319"),n("96cf");var r=n("c973"),o=n.n(r),i=(n("5c7d"),n("7d6e"),n("e54f"),n("0ca9"),n("5b0d"),n("2b0e")),u=n("1f91"),a=n("42d2"),c=n("b05d"),s=n("1b3f"),f=n("2a19"),l=n("18d6");i["a"].use(c["a"],{config:{loadingBar:{color:"orange",size:"3px"}},lang:u["a"],iconSet:a["a"],plugins:{LoadingBar:s["a"],Notify:f["a"],LocalStorage:l["a"]}});var p=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"q-app"}},[n("router-view")],1)},d=[],b=n("60a3"),h=n("25d1"),y=n("ddaf"),v=function(){var e=function(t,n){return e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var n in t)t.hasOwnProperty(n)&&(e[n]=t[n])},e(t,n)};return function(t,n){function r(){this.constructor=t}e(t,n),t.prototype=null===n?Object.create(n):(r.prototype=n.prototype,new r)}}(),g=function(e,t,n,r){var o,i=arguments.length,u=i<3?t:null===r?r=Object.getOwnPropertyDescriptor(t,n):r;if("object"===typeof Reflect&&"function"===typeof Reflect.decorate)u=Reflect.decorate(e,t,n,r);else for(var a=e.length-1;a>=0;a--)(o=e[a])&&(u=(i<3?o(u):i>3?o(t,n,u):o(t,n))||u);return i>3&&u&&Object.defineProperty(t,n,u),u},m=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return v(t,e),t=g([b["a"]],t),t}(Object(b["b"])(h["a"],y["a"])),w=m,O=w,_=n("2877"),x=Object(_["a"])(O,p,d,!1,null,null,null),j=x.exports,P=n("0613"),k=n("4bde"),C=n("8c4f"),R=function(){var e=function(t,n){return e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var n in t)t.hasOwnProperty(n)&&(e[n]=t[n])},e(t,n)};return function(t,n){function r(){this.constructor=t}e(t,n),t.prototype=null===n?Object.create(n):(r.prototype=n.prototype,new r)}}(),S=function(e,t,n,r){function o(e){return e instanceof n?e:new n((function(t){t(e)}))}return new(n||(n=Promise))((function(n,i){function u(e){try{c(r.next(e))}catch(t){i(t)}}function a(e){try{c(r["throw"](e))}catch(t){i(t)}}function c(e){e.done?n(e.value):o(e.value).then(u,a)}c((r=r.apply(e,t||[])).next())}))},M=function(e,t){var n,r,o,i,u={label:0,sent:function(){if(1&o[0])throw o[1];return o[1]},trys:[],ops:[]};return i={next:a(0),throw:a(1),return:a(2)},"function"===typeof Symbol&&(i[Symbol.iterator]=function(){return this}),i;function a(e){return function(t){return c([e,t])}}function c(i){if(n)throw new TypeError("Generator is already executing.");while(u)try{if(n=1,r&&(o=2&i[0]?r["return"]:i[0]?r["throw"]||((o=r["return"])&&o.call(r),0):r.next)&&!(o=o.call(r,i[1])).done)return o;switch(r=0,o&&(i=[2&i[0],o.value]),i[0]){case 0:case 1:o=i;break;case 4:return u.label++,{value:i[1],done:!1};case 5:u.label++,r=i[1],i=[0];continue;case 7:i=u.ops.pop(),u.trys.pop();continue;default:if(o=u.trys,!(o=o.length>0&&o[o.length-1])&&(6===i[0]||2===i[0])){u=0;continue}if(3===i[0]&&(!o||i[1]>o[0]&&i[1]<o[3])){u.label=i[1];break}if(6===i[0]&&u.label<o[1]){u.label=o[1],o=i;break}if(o&&u.label<o[2]){u.label=o[2],u.ops.push(i);break}o[2]&&u.ops.pop(),u.trys.pop();continue}i=t.call(e,u)}catch(a){i=[6,a],r=0}finally{n=o=0}if(5&i[0])throw i[1];return{value:i[0]?i[1]:void 0,done:!0}}},A=function(e){function t(){var t=null!==e&&e.apply(this,arguments)||this;return t.isAuth=function(e,n,r){return S(t,void 0,Promise,(function(){var e;return M(this,(function(t){switch(t.label){case 0:return[4,this.$axios.post("/heartbeat")];case 1:return e=t.sent(),403===e.status||401===e.status?(f["a"].create({color:"negative",progress:!0,caption:"Нет прав для доступа к ресурсу.",message:"Пожалуйста выполните авторизацию.",icon:"report_problem",position:"bottom"}),l["a"].remove("isLogged"),l["a"].remove("user"),r("/"),[2]):(r(),[2])}}))}))},t}return R(t,e),t}(b["d"]),L=A,E=new L,V=[{path:"/",component:function(){return Promise.all([n.e(0),n.e(2)]).then(n.bind(null,"713b"))},children:[{path:"",name:"main",component:function(){return Promise.all([n.e(0),n.e(3)]).then(n.bind(null,"8b24"))}},{path:"/about",component:function(){return Promise.all([n.e(0),n.e(4)]).then(n.bind(null,"a1d1"))}},{path:"/fileHistory",beforeEnter:E.isAuth,name:"fileHistory",component:function(){return Promise.all([n.e(0),n.e(6)]).then(n.bind(null,"067f"))}},{path:"/monitoring",beforeEnter:E.isAuth,name:"monitoring",component:function(){return Promise.all([n.e(0),n.e(7)]).then(n.bind(null,"b716"))}}]},{path:"*",component:function(){return Promise.all([n.e(0),n.e(5)]).then(n.bind(null,"e51e"))}}],G=V,T=Object(k["route"])((function(e){var t=e.Vue;t.use(C["a"]);var n=new C["a"]({scrollBehavior:function(){return{x:0,y:0}},routes:G,mode:"history",base:"/"});return n})),$=function(){return B.apply(this,arguments)};function B(){return B=o()(regeneratorRuntime.mark((function e(){var t,n,r;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:if("function"!==typeof P["a"]){e.next=6;break}return e.next=3,Object(P["a"])({Vue:i["a"]});case 3:e.t0=e.sent,e.next=7;break;case 6:e.t0=P["a"];case 7:if(t=e.t0,"function"!==typeof T){e.next=14;break}return e.next=11,T({Vue:i["a"],store:t});case 11:e.t1=e.sent,e.next=15;break;case 14:e.t1=T;case 15:return n=e.t1,t.$router=n,r={router:n,store:t,render:function(e){return e(j)}},r.el="#q-app",e.abrupt("return",{app:r,store:t,router:n});case 20:case"end":return e.stop()}}),e)}))),B.apply(this,arguments)}var D=n("bc3a"),U=n.n(D),q=function(e,t,n,r){function o(e){return e instanceof n?e:new n((function(t){t(e)}))}return new(n||(n=Promise))((function(n,i){function u(e){try{c(r.next(e))}catch(t){i(t)}}function a(e){try{c(r["throw"](e))}catch(t){i(t)}}function c(e){e.done?n(e.value):o(e.value).then(u,a)}c((r=r.apply(e,t||[])).next())}))},H=function(e,t){var n,r,o,i,u={label:0,sent:function(){if(1&o[0])throw o[1];return o[1]},trys:[],ops:[]};return i={next:a(0),throw:a(1),return:a(2)},"function"===typeof Symbol&&(i[Symbol.iterator]=function(){return this}),i;function a(e){return function(t){return c([e,t])}}function c(i){if(n)throw new TypeError("Generator is already executing.");while(u)try{if(n=1,r&&(o=2&i[0]?r["return"]:i[0]?r["throw"]||((o=r["return"])&&o.call(r),0):r.next)&&!(o=o.call(r,i[1])).done)return o;switch(r=0,o&&(i=[2&i[0],o.value]),i[0]){case 0:case 1:o=i;break;case 4:return u.label++,{value:i[1],done:!1};case 5:u.label++,r=i[1],i=[0];continue;case 7:i=u.ops.pop(),u.trys.pop();continue;default:if(o=u.trys,!(o=o.length>0&&o[o.length-1])&&(6===i[0]||2===i[0])){u=0;continue}if(3===i[0]&&(!o||i[1]>o[0]&&i[1]<o[3])){u.label=i[1];break}if(6===i[0]&&u.label<o[1]){u.label=o[1],o=i;break}if(o&&u.label<o[2]){u.label=o[2],u.ops.push(i);break}o[2]&&u.ops.pop(),u.trys.pop();continue}i=t.call(e,u)}catch(a){i=[6,a],r=0}finally{n=o=0}if(5&i[0])throw i[1];return{value:i[0]?i[1]:void 0,done:!0}}},J={baseURL:"api/"},N=U.a.create(J),Y=Object(k["boot"])((function(e){var t=e.Vue;e.router;t.prototype.$axios=U.a,N.interceptors.response.use((function(e){return e}),(function(e){return q(void 0,void 0,void 0,(function(){var t;return H(this,(function(n){return t=e.response,t?(401!==t.status&&403!==t.status||(l["a"].remove("isLogged"),l["a"].remove("user")),[2,t]):[2,Promise.reject(e)]}))}))}));var n=t;n.prototype.$axios=N})),z="/";function I(){return Q.apply(this,arguments)}function Q(){return Q=o()(regeneratorRuntime.mark((function e(){var t,n,r,o,u,a,c,s,f;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,$();case 2:t=e.sent,n=t.app,r=t.store,o=t.router,u=!1,a=function(e){u=!0;var t=Object(e)===e?o.resolve(e).route.fullPath:e;window.location.href=t},c=window.location.href.replace(window.location.origin,""),s=[Y],f=0;case 11:if(!(!1===u&&f<s.length)){e.next=29;break}if("function"===typeof s[f]){e.next=14;break}return e.abrupt("continue",26);case 14:return e.prev=14,e.next=17,s[f]({app:n,router:o,store:r,Vue:i["a"],ssrContext:null,redirect:a,urlPath:c,publicPath:z});case 17:e.next=26;break;case 19:if(e.prev=19,e.t0=e["catch"](14),!e.t0||!e.t0.url){e.next=24;break}return window.location.href=e.t0.url,e.abrupt("return");case 24:return console.error("[Quasar] boot error:",e.t0),e.abrupt("return");case 26:f++,e.next=11;break;case 29:if(!0!==u){e.next=31;break}return e.abrupt("return");case 31:new i["a"](n);case 32:case"end":return e.stop()}}),e,null,[[14,19]])}))),Q.apply(this,arguments)}I()},"5b0d":function(e,t,n){},6968:function(e,t,n){"use strict";var r={namespaced:!0,state:function(){return{message:{},isConnected:{}}},mutations:{setMessage:function(e,t){e.message=t},setConnectedState:function(e,t){e.isConnected=t}},actions:{},modules:{},getters:{getMessage:function(e){return e.message},isConnected:function(e){return e.isConnected}}};t["a"]=r},ddaf:function(e,t,n){"use strict";var r=n("2b0e"),o=n("2fe1"),i=n("4bb5"),u=function(){var e=function(t,n){return e=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var n in t)t.hasOwnProperty(n)&&(e[n]=t[n])},e(t,n)};return function(t,n){function r(){this.constructor=t}e(t,n),t.prototype=null===n?Object.create(n):(r.prototype=n.prototype,new r)}}(),a=function(e,t,n,r){var o,i=arguments.length,u=i<3?t:null===r?r=Object.getOwnPropertyDescriptor(t,n):r;if("object"===typeof Reflect&&"function"===typeof Reflect.decorate)u=Reflect.decorate(e,t,n,r);else for(var a=e.length-1;a>=0;a--)(o=e[a])&&(u=(i<3?o(u):i>3?o(t,n,u):o(t,n))||u);return i>3&&u&&Object.defineProperty(t,n,u),u},c=Object(i["a"])("SocketModule"),s=function(e){function t(){return null!==e&&e.apply(this,arguments)||this}return u(t,e),a([c.Mutation("setMessage")],t.prototype,"setMessage",void 0),a([c.Mutation("setConnectedState")],t.prototype,"setConnectedState",void 0),a([c.Getter("getMessage")],t.prototype,"getMessage",void 0),a([c.Getter("isConnected")],t.prototype,"isConnected",void 0),t=a([o["b"]],t),t}(r["a"]);t["a"]=s},e52e:function(e,t,n){"use strict";var r={namespaced:!0,state:function(){return{recommendations:{},yakeResponse:{}}},mutations:{setRecommendation:function(e,t){e.recommendations=t},setYakeResponse:function(e,t){e.yakeResponse=t}},actions:{},modules:{},getters:{getRecommendation:function(e){return e.recommendations},getYakeResponse:function(e){return e.yakeResponse}}};t["a"]=r}});