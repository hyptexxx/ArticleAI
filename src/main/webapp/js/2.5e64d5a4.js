(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[2],{"713b":function(t,e,a){"use strict";a.r(e);var r=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("q-layout",{attrs:{view:"hHh lpR lFf"}},[a("q-header",{attrs:{elevated:""}},[a("q-toolbar",[a("q-btn",{attrs:{flat:"",dense:"",round:"",icon:"menu","aria-label":"Menu"},on:{click:function(e){t.leftDrawerOpen=!t.leftDrawerOpen}}}),a("q-toolbar-title",[t._v("Актуальность научных публикаций")]),a("div",{staticClass:"row q-ml-auto"},[a("q-item-section",{attrs:{avatar:""}},[a("q-avatar",{attrs:{size:"48px"}},[a("img",{attrs:{src:"https://ies.unitech-mo.ru/files/upload/tpl/logo.svg?1609842718"}})])],1)],1)],1),a("q-tabs",{attrs:{align:"left","inline-label":""}},[a("q-route-tab",{attrs:{to:"/",label:"Главная",icon:"star"}}),a("q-route-tab",{attrs:{to:"/about",label:"О системе",icon:"search"}})],1)],1),a("q-drawer",{attrs:{"content-class":"q-layout__section--marginal"},model:{value:t.leftDrawerOpen,callback:function(e){t.leftDrawerOpen=e},expression:"leftDrawerOpen"}},[a("account"),a("span",{staticStyle:{position:"absolute",bottom:"0","font-size":"11px"}},[t._v(t._s(t.productVersion))])],1),a("q-page-container",[a("router-view")],1)],1)},n=[],o=a("60a3"),i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("q-list",{staticClass:"rounded-borders text-white",attrs:{bordered:""}},[!0===t.isLoginned?a("q-expansion-item",{staticClass:"bg-grey-10 text-white",scopedSlots:t._u([{key:"header",fn:function(){return[a("q-item-section",{attrs:{avatar:""}},[a("q-avatar",{attrs:{rounded:"",size:"48px"}},[a("img",{attrs:{src:"https://cdn.quasar.dev/img/avatar.png"}})])],1),a("q-item-section",{staticClass:"text-white"},[a("q-item-label",{staticClass:"text-white"},[t._v(t._s(t.user.login))]),a("q-item-label",{staticClass:"text-white",attrs:{caption:""}},[t._v(t._s(t.user.role))])],1),a("q-item-section",{staticClass:"text-white",attrs:{side:""}},[t._v("Аккаунт")])]},proxy:!0}],null,!1,3952703914)},[a("q-card",{staticClass:"bg-white text-amber-1"},[a("q-btn",{staticClass:"bg-white",staticStyle:{width:"100%"},attrs:{align:"left",icon:"view_compact",flat:"",label:"Личный кабинет"}}),a("q-btn",{staticClass:"bg-white",staticStyle:{width:"100%"},attrs:{align:"left",icon:"directions_run",flat:"",label:"Выход"}})],1)],1):a("q-btn",{staticClass:"bg-white text-black",staticStyle:{width:"100%"},attrs:{outline:""}},[a("q-item-section",{attrs:{avatar:""}},[a("q-avatar",{attrs:{size:"48px"}},[a("img",{attrs:{src:"https://www.shareicon.net/data/512x512/2017/01/06/868320_people_512x512.png"}})])],1),a("q-item-section",[a("q-item-label",[t._v("Вход")])],1)],1)],1)},s=[],c=function(){var t=function(e,a){return t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var a in e)e.hasOwnProperty(a)&&(t[a]=e[a])},t(e,a)};return function(e,a){function r(){this.constructor=e}t(e,a),e.prototype=null===a?Object.create(a):(r.prototype=a.prototype,new r)}}(),l=function(t,e,a,r){var n,o=arguments.length,i=o<3?e:null===r?r=Object.getOwnPropertyDescriptor(e,a):r;if("object"===typeof Reflect&&"function"===typeof Reflect.decorate)i=Reflect.decorate(t,e,a,r);else for(var s=t.length-1;s>=0;s--)(n=t[s])&&(i=(o<3?n(i):o>3?n(e,a,i):n(e,a))||i);return o>3&&i&&Object.defineProperty(e,a,i),i},u=function(t){function e(){var e=null!==t&&t.apply(this,arguments)||this;return e.isLoginned=!1,e.user={login:"",post:"",idUser:0,role:"hr"},e}return c(e,t),e=l([o["a"]],e),e}(o["d"]),p=u,f=p,b=a("2877"),d=a("1c1c"),h=a("3b73"),w=a("4074"),v=a("cb32"),_=a("0170"),y=a("f09f"),m=a("9c40"),q=a("eebe"),g=a.n(q),O=Object(b["a"])(f,i,s,!1,null,null,null),x=O.exports;g()(O,"components",{QList:d["a"],QExpansionItem:h["a"],QItemSection:w["a"],QAvatar:v["a"],QItemLabel:_["a"],QCard:y["a"],QBtn:m["a"]});var Q=function(){var t=function(e,a){return t=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(t,e){t.__proto__=e}||function(t,e){for(var a in e)e.hasOwnProperty(a)&&(t[a]=e[a])},t(e,a)};return function(e,a){function r(){this.constructor=e}t(e,a),e.prototype=null===a?Object.create(a):(r.prototype=a.prototype,new r)}}(),j=function(t,e,a,r){var n,o=arguments.length,i=o<3?e:null===r?r=Object.getOwnPropertyDescriptor(e,a):r;if("object"===typeof Reflect&&"function"===typeof Reflect.decorate)i=Reflect.decorate(t,e,a,r);else for(var s=t.length-1;s>=0;s--)(n=t[s])&&(i=(o<3?n(i):o>3?n(e,a,i):n(e,a))||i);return o>3&&i&&Object.defineProperty(e,a,i),i},C=function(t){function e(){var e=null!==t&&t.apply(this,arguments)||this;return e.leftDrawerOpen=!0,e.productVersion="v3.0.1 alpha.",e}return Q(e,t),e=j([Object(o["a"])({components:{Account:x}})],e),e}(o["d"]),D=C,P=D,R=a("4d5a"),S=a("e359"),k=a("65c6"),A=a("6ac5"),L=a("429b"),T=a("7867"),z=a("9404"),I=a("09e3"),E=Object(b["a"])(P,r,n,!1,null,null,null);e["default"]=E.exports;g()(E,"components",{QLayout:R["a"],QHeader:S["a"],QToolbar:k["a"],QBtn:m["a"],QToolbarTitle:A["a"],QItemSection:w["a"],QAvatar:v["a"],QTabs:L["a"],QRouteTab:T["a"],QDrawer:z["a"],QPageContainer:I["a"]})}}]);